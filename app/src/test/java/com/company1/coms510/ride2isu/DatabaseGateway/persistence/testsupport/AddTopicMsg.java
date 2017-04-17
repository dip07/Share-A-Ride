package com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.Message;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Cmd;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Response;

public class AddTopicMsg extends Message {
	
	public AddTopicMsg(String addr, String topic ){
	super();
	super.server = addr; 
	super.topic = topic;
	super.cmd = new PsGateway.Command(Cmd.addTopic);
	super.callback = null; //create never needs deferred? 
	}
	
	public Response send(PsGateway gw){
		return gw.send(this);
	};
}

