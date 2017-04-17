package com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport;

import java.util.HashMap;
import java.util.Map;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Callback;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Command;

public class Message {
	public String server;
	public String topic;
	public Command cmd;
	public Map<String, String> payload = new HashMap<String, String>();
	public Callback callback;

	public Message() {
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallBack(Callback callback) {
		this.callback = callback;
	}

	public PsGateway.Command getCommand() {
		return cmd;
	}

	public String getTopic() {
		return topic;
	}

	public String getParam(String key) {
		return cmd.getParam(key);
	}

	public int getIntParam(String key) {
		String value = cmd.getParam(key);
		return ((value == null)|| value.isEmpty())? 0 : Integer.parseInt(value);
	}

	public String getServer() {
		return server;
	}
	
	
	
	
}