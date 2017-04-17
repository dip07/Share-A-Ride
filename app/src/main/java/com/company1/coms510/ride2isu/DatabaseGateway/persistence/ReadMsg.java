package  com.company1.coms510.ride2isu.DatabaseGateway.persistence;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.Message;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Callback;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Cmd;

public class ReadMsg extends Message {

	public ReadMsg(String vbarDelimitedData){
		this(VBD_Unmarshaller.extractAddr(vbarDelimitedData),	
				VBD_Unmarshaller.record(vbarDelimitedData, false)
			);
	}
	
	public ReadMsg(String addr, Record record){
		this(addr, record.topic, record.key);
	}

	public ReadMsg(String addr, String topic, int index){
		
	}
	
	public ReadMsg(String addr, String topic, String key){
		super.server = addr; 
		super.topic = topic;
		super.cmd = new PsGateway.Command(Cmd.read);
		cmd.setParam("key", key);
		super.callback = null; //default. Gateway will supply callback				
	}
	
	public void setParam(String key, String value){
		cmd.setParam(key, value);
	}
	
	public PsGateway.Response send(PsGateway gw, Callback callback){
		super.callback = callback;
		return gw.send(this);
	}
	
	public PsGateway.CbResponse send(PsGateway gw) {
		 return (PsGateway.CbResponse) gw.send(this);
	}
} 
