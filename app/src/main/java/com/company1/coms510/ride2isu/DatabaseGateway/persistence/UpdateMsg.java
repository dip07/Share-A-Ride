package com.company1.coms510.ride2isu.DatabaseGateway.persistence;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.Message;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Cmd;

public class UpdateMsg extends Message {
	
	/**
	 * primarily for testing and automation, this constructor creates 
	 * a string (typically read from a file) in form 
	 * addr|topic|key|index|\t payload
	 * @param vbarDelimitedData
	 * @param deferred reference to a callback object. 
	 */
	public UpdateMsg (String vbarDelimitedData){
		this( VBD_Unmarshaller.extractAddr(vbarDelimitedData),	
				VBD_Unmarshaller.record(vbarDelimitedData)
			);
		
	}

	public UpdateMsg(String addr, int index, String payload){
		this(addr, Record.createUpdateRecord(index, payload));
	}
	
	public UpdateMsg (String addr, Record record){
		super();
		super.server = addr; 
		super.topic = record.topic;
		super.cmd = new PsGateway.Command(Cmd.update);
		cmd.setParam("pageSize", "1");
		cmd.setParam("index", ""+record.index);
		super.payload.put(record.key, record.pLoad);
		super.callback = null; //update never needs deferred? 
	}
	
	


	public PsGateway.Response send(PsGateway gw){
		return gw.send(this);
	}

}
