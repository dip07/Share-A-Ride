package  com.company1.coms510.ride2isu.DatabaseGateway.persistence;

public enum PsStatus {
	OK(0),
	NOTOPIC(-1),
	NOKEY(-2), 
	DUPE(-3), NORECORD(-4);;
	
	
	private int stat;
	private PsStatus(int value){
		stat = value;
	}
	
	public int getValue(){
		return stat;
	}
}
