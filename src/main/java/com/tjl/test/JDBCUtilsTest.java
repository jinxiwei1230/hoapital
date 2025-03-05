package com.tjl.test;

public class JDBCUtilsTest {
/*
	@Test
	public void jdbcConnectionTest() throws Exception
	{
		Connection connection = JDBCUtils.getConnection();
		System.out.println("数据库连接成功！");
		//创建执行环境
		Statement statement = connection.createStatement();
		//查询得到结果集
		ResultSet result = statement.executeQuery("SELECT * FROM `administrator`");
		while(result.next())
		{
			System.out.print(result.getString("idNumber") +"  " );
			System.out.print(result.getString("name") +"  " );
			System.out.println(result.getString("password"));
		}
	}
	
	
	
	@Test
	public void loginTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		User admin = new Administrator("admin1", "admin1", 30, 'M', "Admin ContactInfo", "Admin Address", "admin11");
		int type = userdao_Imp.login(admin);
		System.out.println(type);
	}

	
	@Test
	public void insertTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		User admin = new Doctor("octor", "admin1", 30, "M", "Admin ContactInfo", "Admin Address", "admin11","qerw","wfrfw","ergetge","regts");
		boolean line = userdao_Imp.insert(admin);
		System.out.println(line);
	}
	
	@Test
	public void updateTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		User admin = new Doctor("doctor", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin222","qerw","wfrfw","ergetge","regts");
		boolean line = userdao_Imp.updata(admin);
		System.out.println(line);
	}
	
	@Test
	public void deleteTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		//User admin = new Doctor("doctor", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin222","qerw","wfrfw","ergetge","regts");
		boolean line = userdao_Imp.delete("doctor");
		System.out.println(line);
	}
	
	@Test
	public void selectTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		//User admin = new Doctor("doctor", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin222","qerw","wfrfw","ergetge","regts");
		User user = userdao_Imp.select("admin1");
		System.out.println(user);
	}
	
	
	@Test
	public void insertTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		User user = new Administrator("admin", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin");
		boolean line = userdao_Imp.insert(user);
		System.out.println(line);
	}
	

	
	
	@Test
	public void scheinsertTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		schedule c = new schedule(3, "iuy98765t", "gafd", "2022-2-1 11:34:55","rdsh", "uytr",0,50);
		boolean line = userdao_Imp.scheduleInsert(c);
		System.out.println(line);
	}
	
	@Test
	public void selectTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		//User admin = new Doctor("doctor", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin222","qerw","wfrfw","ergetge","regts");
		ArrayList<schedule> list= userdao_Imp.scheduleSelectApprove();
		list.forEach(user -> System.out.println(user.toString()));
	}

	@Test
	public void upinsertTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		caseHistory c = new caseHistory(3, "iu87654yt", "gafd", "2022-2-1 11:34:55","rdsh");
		boolean line = userdao_Imp.caseHistoryInsert(c);
		System.out.println(line);
	}	
	
	
	@Test
	public void caseinsertTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		Appointment c = new Appointment(3, "5301271234", "mike", "2022-2-1 11:34:55","tooth", "toothpartment");
		boolean line = userdao_Imp.appointInsert(c);
		System.out.println(line);
	}
	
	
	

	
	@Test
	public void selectTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		//User admin = new Doctor("doctor", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin222","qerw","wfrfw","ergetge","regts");
		Appointment a= userdao_Imp.appointSelectTime("2022-02-01 11:34:55","530127");
		System.out.println(a.toString());
	}
	@Test
	public void deleteTest() throws Exception
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		boolean line = userdao_Imp.appointDelete(1,"76543");
		System.out.println(line);
	}*/
	
	
}
