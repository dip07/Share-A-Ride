package com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStore.Blob;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.PsStatus;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;

public class TestMemStore {

	MemStore store = new MemStore();
	
	@Test
	public void testTopics() {
		store.clear();
		
		List<Record> result = new ArrayList<Record>();
		int status = store.read("My Topic", "Blues", result );
		System.out.println("status after 1st read"+status);
		assertEquals( PsStatus.NOTOPIC.getValue(), status);
		assertEquals( 0, result.size());

		boolean success = store.addTopic("My Topic");
		assertTrue(success);
		
		status = store.read("My Topic", "Blues", result );
		//System.out.println("status after adding topic"+status);
		assertEquals( PsStatus.NOKEY.getValue(), status);
		assertEquals( 0, result.size());
		
		// write a record
		status = store.create("My Topic", "Blues", "song = my favorite");
		//System.out.println("create returns "+status);
		assertTrue(status >= PsStatus.OK.getValue());
	    assertTrue(status == Blob.kSerialDefault);
		
		// retrieve that record
		status = store.read("My Topic", "Blues", result );
	//	System.out.println("Status of 1st create is:"+status);
		assertEquals( 1, status);
		assertEquals( 1, result.size());
		assertEquals(result.get(0).pLoad,"song = my favorite");

		// write a record
		status = store.create("My Topic", "Blues", "song = different stuff");
		//System.out.println("create returns "+status);
		assertTrue(status >= PsStatus.OK.getValue());
	    assertTrue(status == Blob.kSerialDefault+1);

		// retrieve the set
		result.clear();
	    status = store.read("My Topic", "Blues", result );
       // System.out.println("status after 2 records creation"+status);
		assertEquals( 2, status);
		assertEquals( 2, result.size());
		assertListContains("song = different stuff",result);
		assertListContains("song = my favorite",result);	
	}

	private boolean assertListContains(String expected, List<Record> result) {
		for (Record item : result){
			if (item.pLoad.contentEquals(expected)){
				return true;
			}
		}
		fail("Did not find "+expected+ "in result set");
		return false;
	}
    @Test
    public void testDb()
    {
        store.clear();

        List<Record> result = new ArrayList<Record>();
        boolean success = store.addTopic("Ride Details");

        int status = store.create("Ride Details", "source-destination-25th october 2016", "capacity=3");
       status = store.read("Ride Details", "source-destination-25th october 2016", result );
        status = store.create("Ride Details", "source-destination-25th october 2016", "capacity=1");
        status = store.read("Ride Details", "source-destination-25th october 2016", result );
       status = store.create("Ride Details", "source-destination-24th october 2016", "capacity=5");
        status = store.read("Ride Details", "source-destination-24th october 2016", result );
        status = store.create("Ride Details", "source-destination-24th october 2016", "capacity=4");
        status = store.read("Ride Details", "source-destination-24th october 2016", result );
        status = store.create("Ride Details", "source-destination-24th october 2016", "capacity=2");
        status = store.read("Ride Details", "source-destination-24th october 2016", result );


        List<String> ridesList=new ArrayList<String>();
        for(Record r:result){
            String key=r.key;
            String[] output=key.split("-");
            if(output[2].equals("24th october 2016"))
            ridesList.add(r.pLoad);
      }
        for(String s:ridesList){
            System.out.println(s);
        }

    }

	@Test
	public void testUpdate(){
		store.clear();

		List<Record> result = new ArrayList<Record>();
		boolean success = store.addTopic("Ride Details");

		int status = store.create("Ride Details", "source-dest-3Dec", "capacity=3");
		status = store.read("Ride Details", "source-dest-3Dec", result);

		List<String> ridesList=new ArrayList<String>();

		for(Record r:result){
			String key=r.key;
			ridesList.add(r.pLoad);
		}
		for(String s:ridesList){
			System.out.println("Before Update - " + s);
		}

		status = store.update(100, "capacity=5");
		status = store.update(100, "capacity=6");
		status = store.update(100, "capacity=7");
		List<Record> result_ = new ArrayList<Record>();
		status = store.read("Ride Details", "source-dest-3Dec", result_);

		List<String> n_ridesList=new ArrayList<String>();
		for(Record r:result_){
			String key=r.key;
			n_ridesList.add(r.pLoad);
		}
		for(String s_:n_ridesList){
			System.out.println("After Update -- " + s_);
		}
	}
}
