package com.company1.coms510.ride2isu.DatabaseGateway.persistence;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.Message;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Cmd;

public class DeleteMsg extends Message {
	/**
	 * primarily for testing and automation, this constructor creates 
	 * a string (typically read from a file) in form 
	 * addr|topic|key|index|\t payload
	 * @param tabDelimitedData
	 * @param deferred reference to a callback object. 
	 */
	public DeleteMsg (String vbarDelimitedData){
		super.server = VBD_Unmarshaller.extractAddr(vbarDelimitedData);	
	}

	public DeleteMsg (String addr, Record record){
		super();
		super.server = addr; 
		super.topic = record.topic;
		super.cmd = new PsGateway.Command(Cmd.delete);
		cmd.setParam("pageSize", "1");
		cmd.setParam("key", record.key);
		super.payload.put(record.key, record.pLoad);
		super.callback = null; //create never needs deferred? 
	}
	
	public DeleteMsg (int index){
		super.cmd = new PsGateway.Command(Cmd.delete);
		cmd.setParam("pageSize", "1");
		cmd.setParam("index", index+"");
		super.callback = null; //create never needs deferred? 
	}
	
	public PsGateway.Response send(PsGateway gw){
		return gw.send(this);
	}

}
