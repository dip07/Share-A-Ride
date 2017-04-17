package com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.PsStatus;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemStore implements Serializable {

	public static class Blob implements Serializable {
		public static final int kSerialDefault = 100;

		protected static int nextSerial = kSerialDefault;
		private String data;
		private int serial;

		public Blob (String data){
			this.data = data;
			this.serial = nextSerial++;
		}

		public String getData(){
			return this.data;
		}

		public int getSerial() {
			return this.serial;
		}

		public String marshall(List<Blob> records)
		{
			String rval = "";
			for (Blob record : records){
				rval += record.toString()+"\n";
			}
			return rval;
		}

		@Override
		public String toString(){
			return serial+": "+data;
		}
	}

	/**
	 * A multimap with two level (topic->key) index. Entries are
	 * assigned a unique integer index as they are created.
	 */
	private Map<String,Map<String,List<Blob>>> topics
			= new HashMap<String, Map<String, List<Blob>>>();


	/**
	 * @param topic
	 * @param key
	 * @param payload
	 * @return -1 if bin topic does not exist.
	 *         -2 if key/record already exist.
	 *         Otherwise record index.
	 */
	public int create(String topic, String key, String payload){
		Map<String, List<Blob>> bin = topics.get(topic);
		List<Blob> cluster = null;
		if (bin == null)
			return PsStatus.NOTOPIC.getValue();
		else if ( (cluster = bin.get(key)) != null && isDuplicate(cluster, payload)){
			return PsStatus.DUPE.getValue();
		} else {
			// no entry for this key, create a key cluster
			if (cluster == null){
				cluster = new ArrayList();
				bin.put(key, cluster);
			}
			// now put payload into key cluster
			Blob entry = new Blob(payload);
			cluster.add(entry);
			return entry.getSerial();
		}
	}

	private boolean isDuplicate(Collection<Blob> cluster, String payload) {
		for (Blob item: cluster){
			if (payload.contentEquals(item.getData())){
				return true;
			}
		}
		return false;
	}

	public int read(String topic, String key, List<Record> result ) {
		List<Blob> cluster= null;
		Map<String,List<Blob>> bin = topics.get(topic);
		if (bin == null)
			return PsStatus.NOTOPIC.getValue();
		else if ( (cluster = bin.get(key)) == null) {
			return PsStatus.NOKEY.getValue();
		} else {
			int offset = 0;
			for (Blob item: cluster){
				result.add(new Record(topic, key, item.getSerial(), item.getData()));
				offset ++;
			}
			return offset;
		}
	}

	public List<String> addTopic(String[] topics){
		List<String> rval = new ArrayList<String>();
		for (int i = 0; i < topics.length; i++){
			if ( ! addTopic(topics[i])){
				rval.add(topics[i]);
			}
		}
		return rval;
	}

	public boolean addTopic(String topic) {
		if ( topics.keySet().contains(topic)) {
			return false;
		} else {
			topics.put(topic, new HashMap<String,List<Blob>>());
			return true;
		}
	}

	public void clear() {
		topics = new HashMap<String, Map<String, List<Blob>>>();
		Blob.nextSerial = Blob.kSerialDefault;
	}

	public int delete(String topic, String key, String payload) {
		Map<String, List<Blob>> topicMap = topics.get(topic);
		if (topicMap == null || topicMap.isEmpty() )
			return PsStatus.NOTOPIC.getValue();
		List<Blob> keyRecords = topicMap.get(key);
		if (keyRecords == null || keyRecords.isEmpty() ){
			return PsStatus.NOKEY.getValue();
		}
		for (Blob record: keyRecords){
			if (record.getData().contentEquals(payload)){
				keyRecords.remove(record);
				return record.getSerial();
			}
		}
		return PsStatus.NORECORD.getValue();
	}

	/**
	 * unconditionally deletes the record with unique id dsIndex.
	 *
	 * @param dsIndex the unique id associated with the record to delete.
	 * @return the deleted index on success. PsStatus.NORECORD (a negative int)
	 * if the requested index is no longer in the store.
	 */
	public int delete(int dsIndex){
		for (Map<String, List<Blob>> topic: topics.values()){
			for (List<Blob> key : topic.values()){
				System.out.println("before\n"+topic.values().toString());
				for (Blob record: key){
					if (record.getSerial()==dsIndex){
						key.remove(record);
						System.out.println("after\n"+topic.values().toString());
						return dsIndex;
					}
				}
			}
		}
		return PsStatus.NORECORD.getValue();
	}

	public int update(int dsIndex, String newPayload) {
		for (Map<String, List<Blob>> topic: topics.values()){
			for (List<Blob> key : topic.values()){
				System.out.println("before\n"+topic.values().toString());
				for (Blob record: key){
					if (record.getSerial()==dsIndex){
						record.data = newPayload;
						System.out.println("after\n"+topic.values().toString());
						return dsIndex;
					}
				}
			}
		}
		return PsStatus.NORECORD.getValue();
	}
}
