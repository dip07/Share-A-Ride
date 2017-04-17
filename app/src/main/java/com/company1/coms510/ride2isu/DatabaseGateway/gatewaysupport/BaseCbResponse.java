package com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.CbResponse;

public class BaseCbResponse extends BaseResponse implements CbResponse {

	List<Record> content = new ArrayList<Record>();

	public BaseCbResponse (String server, String topic, List<Record> recordSet, int offset){
		super(server, topic, "", 0);
		for (Record item: recordSet){
			content.add(new Record( topic, item.key,  item.index, item.pLoad));
		}
		super.addDetail("nextOffset", ""+offset);
	}

	public BaseCbResponse() {
	}

	@Override
	public List<Record> getPayload() {
		return content;
	}

	public void addRecord(String key, String data){
		content.add(new Record("", key, 0, data));
	}
	
}
