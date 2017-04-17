package  com.company1.coms510.ride2isu.DatabaseGateway.persistence;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;

public class VBD_Unmarshaller {

	/**
	 * primarily for testing and automation, this constructor creates 
	 * a string (typically read from a file) in form 
	 * addr|topic|key|index|\t payload
	 * @param tabDelimitedData
	 * @param deferred reference to a callback object. 
	 */
	//TODO: error checking/handling.
	public static Record record(String data){
		return record(data,true);
	}
	
	public static Record record(String data, boolean payload){
		String[] fields = data.split("\\|");
		String[] payloadBreak = data.split("\\|\\t[ ]+");
		return new Record(
			fields[1].trim(),	/* topic */
			fields[2].trim(),	/* key */
			(fields[3].isEmpty())? -1 : Integer.parseInt(fields[3].trim()),	/* index */
			(payload) ? payloadBreak[1] : ""	/* payload: preserve spaces after first. */			
			);		
	}

	public static String extractAddr(String data) {		
			String[] fields = data.split("\\|");
			return fields[0].trim();
			}
}
