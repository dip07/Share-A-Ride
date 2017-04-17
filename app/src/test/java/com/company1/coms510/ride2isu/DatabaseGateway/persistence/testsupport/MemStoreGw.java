package com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.BaseCbResponse;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.BaseResponse;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.Message;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway.Status;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;

/**
 * 
 * This class actually acts more like an endpoint than a gateway. It unpacks the inbound 
 * message, delegates to an instance of memstore for the data, and 
 * composes appropriate response objects. 
 * @author Robertw
 *
 */
public class MemStoreGw implements PsGateway, GwMemStoreTestAccessor {

	protected MemStore store = new MemStore();

	@Override
	public Response send(Message msg) {
		List<Record> recordSet = new ArrayList<Record>();
		switch ( msg.getCommand().getOperation()){
			case read :
				try {
					int offset = 0;
					recordSet.clear();
					String topic = msg.getTopic();
					String key = msg.getParam("key");
					String index = msg.getParam("index");
					String pageSize = msg.getParam("pageSize");
					if (index == null && pageSize == null){
						offset = store.read(topic, key, recordSet); //on read, return value is offset, indices is in recordSet
					}
					if (msg.getCallback()== null){
						return syncPloadResponse(msg.server, topic, key, recordSet, offset);
					}

				} catch (Exception e){

				}
				break;
			case create:
				try {
					int rIndex = 0;
					recordSet.clear();
					String topic = msg.getTopic();
					String key = msg.getParam("key");
					String index = msg.getParam("index");
					int pageSize = msg.getIntParam("pageSize");
					String payload = msg.payload.get(key);
					if (index == null && (pageSize < 2 )){
						rIndex = store.create(topic, key, payload);
					}
					if (msg.getCallback()== null){
						return syncResponse(msg.server, topic, key, rIndex);
					}
				} catch (Exception e){

				}
				break;

			case addTopic:{
				String topic = msg.getTopic();
				boolean state = store.addTopic(topic);
				return syncResponse(msg.getServer(), topic, (state)? 0 : -2);
			}
			case update:
				try {
					int rIndex = 0;
					recordSet.clear();
					String topic = (msg.getTopic()==null)? "update-default": msg.getTopic();
					String key = (msg.getParam("key")==null)? "update-default": msg.getParam("key");
					int index = msg.getIntParam("index");
					int pageSize = msg.getIntParam("pageSize");
					String payload = msg.payload.get(key);
					if (index > 0 && (pageSize < 2 )){
						rIndex = store.update(index, payload);
					}
					if (msg.getCallback()== null){
						return syncResponse(msg.server, topic, key, rIndex);
					}
				} catch (Exception e){
					//TODO: handle properly
				}
			case delete:
				try {
					int rIndex = 0;
					recordSet.clear();
					String topic = msg.getTopic();
					String key = msg.getParam("key");
					int index = msg.getIntParam("index");
					int pageSize = msg.getIntParam("pageSize");
					String payload = msg.payload.get(key);
					if (index > 0 && (pageSize < 2 )){
						rIndex = store.delete(index);
					}
					if (msg.getCallback()== null){
						return syncResponse(msg.server, topic, key, rIndex);
					}
				} catch (Exception e){
					//TODO: handle properly
				}
				break;
			case deleteTopic:

			default:

		}
		return null;
	}

	private Response syncPloadResponse(String server, String topic,
									   String key, List<Record> recordSet, int offset) {
		BaseCbResponse rval = new BaseCbResponse(server, topic, recordSet, offset);
		rval.setMessage("New Topic created at "+topic);
		return rval;
	}

	private Response syncResponse(String server, String topic, int i) {
		BaseResponse rval = new BaseResponse(server, topic, "",  i);
		rval.setMessage("New Topic created at "+topic);
		return rval;
	}

	/**
	 *
	 * constructs a simple, no payload response to be returned synchronously
	 *
	 * @param server
	 * @param topic
	 * @param key
	 * @param rIndex
	 * @return
	 */
	private Response syncResponse(String server, String topic, String key, int rIndex) {
		BaseResponse rval =  new BaseResponse(server, topic, key, rIndex) ;

		return rval;
	}

	@Override
	public MemStore getMemStore() {
		return store;
	}

}