package com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport;

public class Record {
	public String topic;
	public String key;
	public String pLoad;
	public int index;
	
	public Record(String topic, String key, int index, String pLoad) {
	
		this.topic = topic;
		this.key = key;
		this.index = index;
		this.pLoad = pLoad;
	}

}
