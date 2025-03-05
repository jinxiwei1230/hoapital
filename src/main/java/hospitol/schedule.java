package hospitol;

public class schedule{
	
	private int ID;
	private String idNumber;
	private String name;
	private String time;
	private String schedule;
	private String department;
	private int approved;
	private int restNumber;
	
	public schedule(int ID,String idNumber,String name,String time,String schedule,String department,int approved,int restNumber)
	{
		this.schedule = schedule;
		this.ID = ID;
		this.idNumber = idNumber;
		this.name = name;
		this.time = time;
		this.department = department;
		this.approved = approved;
		this.restNumber = restNumber;
	}
	
	 @Override
    public String toString() {
        return "{ID='" + ID + ",idNumber=" + idNumber + ",name=" + name + ",time=" + time +",department=" +department+ ", schedule=" + schedule + ", restNumber=" + restNumber + "}";
    }
	 


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	public int getRestNumber() {
		return restNumber;
	}

	public void setRestNumber(int restNumber) {
		this.restNumber = restNumber;
	}
	
}
