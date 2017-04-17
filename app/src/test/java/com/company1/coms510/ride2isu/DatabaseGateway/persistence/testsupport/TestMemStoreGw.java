package com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.CbResponse;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Status;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.CreateMsg;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.ReadMsg;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;

public class TestMemStoreGw {
	MemStoreGw gw = null;
	MemStore store = null; 
	
	@Before
	public void setup() {
		gw = new MemStoreGw();
		store = ((GwMemStoreTestAccessor) gw).getMemStore();
	}
	// addr|topic|key|index|\t payload
	private static final String write1 = 
			"http://iastate.510.com/test2|Drivers|Joe||\t Joe\'s contact info";

	private static final String write3 = 
			"http://iastate.510.com/test2|Drivers|Joe||\t Joe\'s alternate contact info";

	private static final String write2 = 
			"http://iastate.510.com/test2|Drivers|Tommy||\t Tommy\'s contact info";

	private static final String read1 = 
			"http://iastate.510.com/test2|Drivers|Joe||\t";

	private static final String read2 = 
			"http://iastate.510.com/test2|Drivers|Tommy||\t";
	
	private static final String[] expected = { "Joe\'s contact info",
			"Joe\'s alternate contact info"};
	
	@Test
	public void test() {
		store.clear();
		AddTopicMsg topic = new AddTopicMsg( "http://iastate.510.com", "Drivers" );
		PsGateway.Response reply = topic.send(gw);
		assertEquals(Status.success, reply.getStatus())	;
	
		CreateMsg save = new CreateMsg( write1 );
		reply = save.send(gw);
		assertEquals(Status.success, reply.getStatus());
		assertEquals("Joe", reply.getDetails().get("key"));
		assertEquals("100", reply.getDetails().get("index"));
		
		ReadMsg read = new ReadMsg(read1);
		reply = read.send(gw);
		CbResponse fullReply = null;
		if (reply instanceof CbResponse){
			fullReply = (CbResponse) reply;
		}
		assertEquals(Status.success, reply.getStatus());
		assertEquals(1, fullReply.getPayload().size());
		assertEquals("Joe", fullReply.getPayload().get(0).key);
		assertEquals("Joe\'s contact info", fullReply.getPayload().get(0).pLoad);

		save = new CreateMsg( write2 );
		reply = save.send(gw);
		assertEquals(Status.success, reply.getStatus());
		assertEquals("Tommy", reply.getDetails().get("key"));
		assertEquals("101", reply.getDetails().get("index"));

		save = new CreateMsg( write3 );
		reply = save.send(gw);
		assertEquals(Status.success, reply.getStatus());
		assertEquals("Joe", reply.getDetails().get("key"));
		assertEquals("102", reply.getDetails().get("index"));
		
		read = new ReadMsg(read1);
		reply = read.send(gw);
		fullReply = null;
		if (reply instanceof CbResponse){
			fullReply = (CbResponse) reply;
		}
		assertEquals(Status.success, reply.getStatus());
		assertEquals(2, fullReply.getPayload().size());
		assertEquals("Joe", fullReply.getPayload().get(0).key);
		assertBlobsMatch(fullReply.getPayload(), "Joe", expected);
	}
		//TODO:
		// test read by index
		// test multi-page response
		// check details of error responses at gateway level
		// implement a "remaining" return param (detail) to 
		// indicate how many records follow nextOffset in read query. 
		// test for page-size behavior
		

	private void assertBlobsMatch(List<Record> payload, String key, String[] expected) {
		List<Record> workCopy = new ArrayList<Record>(payload);
		for (String blob : expected){
			for (Record item: workCopy){
				if (item.key.contentEquals(key) && item.pLoad.contentEquals(blob)){
					workCopy.remove(item);
					break;
				}
			}
		} 		
		assertTrue (workCopy.size() == 0);
	}

}
