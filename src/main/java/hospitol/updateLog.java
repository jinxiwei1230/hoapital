package hospitol;

public class updateLog{
//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//	LocalDateTime now = LocalDateTime.now();
//	String s = formatter.format(now);
	private int ID;
	private String idNumber;
	private String name;
	private String time;
	private String updateLog;
	
	public updateLog(int ID,String idNumber,String name,String time,String updateLog)
	{
		this.updateLog = updateLog;
		this.ID = ID;
		this.idNumber = idNumber;
		this.name = name;
		this.time = time;
	}
	 @Override
    public String toString() {
        return "{ID='" + ID + ",idNumber=" + idNumber + ",name=" + name + ", time=" + time + ", updateLog=" + updateLog + "}";
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

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
