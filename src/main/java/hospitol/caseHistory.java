package hospitol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class caseHistory{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	String s = formatter.format(now);
	private int ID;
	private String idNumber;
	private String name;
	private String time;
	private String caseHistory;
	
	public caseHistory(int ID,String idNumber,String name,String time,String caseHistory)
	{
		this.caseHistory = caseHistory;
		this.ID = ID;
		this.idNumber = idNumber;
		this.name = name;
		this.time = time;
	}
	 @Override
	    public String toString() {
	        return "{ID='" + ID + ",name=" + name + ", time=" + time + ", caseHistory=" + caseHistory + "}";
	    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCaseHistory() {
		return caseHistory;
	}

	public void setCaseHistory(String caseHistory) {
		this.caseHistory = caseHistory;
	}
	
}
