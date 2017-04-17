package com.company1.coms510.ride2isu.DatabaseGateway.persistence.gatewaysupport;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;

public interface MockTestControl <T extends PsGateway> {
	public void setMockInstance(T mock); // should be mockito object. 
	public T getMockInstance();
}
