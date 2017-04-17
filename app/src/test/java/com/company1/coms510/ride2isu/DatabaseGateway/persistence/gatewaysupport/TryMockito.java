package com.company1.coms510.ride2isu.DatabaseGateway.persistence.gatewaysupport;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MockPsGatewayFacade;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.BaseCbResponse;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.BaseResponse;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.Message;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.CbResponse;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Response;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Status;
import com.company1.coms510.ride2isu.DatabaseGateway.infrastructure.ServiceRegistry;

public class TryMockito {

	public ServiceRegistry locator = ServiceRegistry.getInstance();
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		locator.register(PsGateway.class, new MockPsGatewayFacade<>());
	}
	
	@Test
	public void testHappyWhenThen() {
		PsGateway.Response rsp1 = new BaseResponse();
		Message readMsg = new Message();
		
		PsGateway gwMock = Mockito.mock(PsGateway.class);
		when(gwMock.send(readMsg)).thenReturn(rsp1);
		
		PsGateway.Response result = gwMock.send(readMsg);
		assertEquals(result.getStatus(),Status.success);
	}

	@Test
	public void testHappyWhenThenInFacadeSync() {
		PsGateway.Response rsp1 = new BaseResponse();
		Message readMsg = new Message();
		PsGateway gwMock = Mockito.mock(PsGateway.class);
		when(gwMock.send(readMsg)).thenReturn(rsp1);
		
		//this time, install mock in facade retrieved from registry
		PsGateway gw = locator.getService(PsGateway.class);
		MockTestControl<PsGateway> tc = (MockTestControl) gw;
		tc.setMockInstance(gwMock);
		
		PsGateway.Response result = gw.send(readMsg);
		
		
		assertEquals(result.getStatus(),Status.success);
		if (result instanceof CbResponse ){
			CbResponse fullResult = (CbResponse) result;
			assertEquals(result.getDetails().get("nextOffset"), "23");
		}
	}

	public static PsGateway.CbResponse asyncAnswer = null; 

	@Test
	public void testHappyWhenThenInFacadeASync() {
		final PsGateway.Response rsp1 = new BaseResponse();
		Message readMsg = new Message();
		readMsg.setCallBack( 
				new PsGateway.Callback() {					
					@Override
					public void deliver(CbResponse deferredResponse) {
						asyncAnswer = deferredResponse;					
					}
				});
		final BaseCbResponse resp2 = new BaseCbResponse();
			resp2.addRecord("Key1", "OpaqueRecordBlob");
			resp2.addDetail("nextOffset", ""+1);
		PsGateway gwMock = Mockito.mock(PsGateway.class);
//		when(gwMock.send(readMsg)).thenReturn(rsp1);
		when(gwMock.send(any(Message.class))).thenAnswer(
			new Answer<Object>() {	
				@Override
				public Object answer(InvocationOnMock req) throws Throwable {
					((Message) req.getArguments()[0]).getCallback().deliver(resp2);
					return rsp1;
				}
			});
		
		//this time, install mock in facade retrieved from registry
		MockTestControl<PsGateway> tc = 
				(MockTestControl) locator.getService(PsGateway.class);
		tc.setMockInstance(gwMock);
		// everything before this is test setup. 
	
		// Here we actually attempt a call to the gateway. 
		// the same way production code would. 
		
		PsGateway gw = locator.getService(PsGateway.class);
		PsGateway.Response result = gw.send(readMsg);
		
		// at this point, 
		// result should hold the synchronous result,
		// asyncAnswer holds the async result. 
		assertEquals(result.getStatus(),Status.success);
		assertNotNull(asyncAnswer);
		assertEquals("OpaqueRecordBlob",
				asyncAnswer.getPayload().get(0).pLoad);
		assertEquals("1",asyncAnswer.getDetails().get("nextOffset") );
	}

}
