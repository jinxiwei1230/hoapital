package hospitol;

public abstract class User {
	//身份证号、姓名、住址、联系方式
	private String idNumber; // 身份证号码
    private String name; // 姓名
    private int age; //年龄
    private String gender; //性别
    private String address; // 地址
    private String contactInfo; // 联系方式
    private String password; // 密码
    private int userType; // 用户类型：患者(PATIENT)、医生(DOCTOR)、管理员(ADMIN)
    private boolean approved; // 是否已审批
    
    public User(String idNumber, String name, int age, String gender,String contactInfo, String address, String password, int userType) {
	    this.idNumber = idNumber;
	    this.name = name;
	    this.age = age;
	    this.address = address;
	    this.contactInfo = contactInfo;
	    this.password = password;
	    this.userType = userType;
	}
    
    @Override
    public String toString() {
        return "{name='" + name + "', idNumber=" + idNumber + ", userType=" + userType + ", age=" + age +", address=" + address + ", contactInfo=" + contactInfo + "}";
    }
    

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	
}
