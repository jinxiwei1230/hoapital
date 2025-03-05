package hospitol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.tjl.dao.Userdao_Imp;

public class Doctor extends User {
	private String hospital; // 所在医院
    private String department; // 科室
    private String title; // 职称
    private String specialty; // 专长
    private ArrayList<String> schedules; // 医生的出诊信息列表
    
    public Doctor(String idNumber, String name, int age, String gender, String contactInfo, String address,String password, String hospital, String department, String title, String specialty) {
	  super(idNumber, name, age, gender,contactInfo, address, password, 3);
	  this.hospital = hospital;
	  this.department = department;
	  this.title = title;
	  this.specialty = specialty;
	  this.schedules = new ArrayList<>();
	}
    

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public ArrayList<String> getSchedules() {
		return schedules;
	}

	public void setSchedules(ArrayList<String> schedules) {
		this.schedules = schedules;
	}
    
	public static void addSchedules(Doctor doctor)//修改出诊信息，自动提交
	{
		Scanner sc= new Scanner(System.in);
		System.out.println("请输入出诊安排编号：");
		int ID = sc.nextInt();
		sc.nextLine();
		System.out.println("请输入出诊安排内容：");
		String schedule = sc.nextLine();
		System.out.println("请输入挂号数量：");
		int restNumber=sc.nextInt();//挂号数量
		sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String time = formatter.format(now);
		schedule sche = new schedule(ID, doctor.getIdNumber(), doctor.getName(), time, schedule, doctor.getDepartment(),0, restNumber);
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		boolean line = userdao_Imp.scheduleInsert(sche);
		if(line != false)
		{
			System.out.println("出诊安排添加成功,等待审批！");
		}else {
			System.out.println("出诊安排添加失败！");
		}
	}
	
	public static void seeSchedules(Doctor doctor)//查看已发布出诊信息
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		ArrayList<schedule> list = userdao_Imp.scheduleSelectApprove(doctor.getIdNumber());
		if(list.size()!=0)
		{
			list.forEach(user -> System.out.println(user.toString()));
		}else {
			System.out.println("您暂未发布出诊信息或出诊信息还未审批通过！");
		}
		
	}
	
	public static schedule searchSchedules(String time)//按时间查询出诊信息（某一条）
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		schedule sche = userdao_Imp.scheduleSelectTime(time);
		System.out.println(sche.toString());
		return sche;
	}
	
	public static void deleteSchedules(int ID)//删除出诊信息
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		boolean line = userdao_Imp.scheduleDelete(ID);
		if(line != false)
		{
			System.out.println("出诊安排删除成功！");
		}else {
			System.out.println("出诊安排删除失败！");
		}
	}
	/*
	public static void periodsche()
	{
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String s = formatter.format(now);
		System.out.println("当前时间: " + s);
        
        System.out.println("请输入周期性出诊的日期和时间，例：2022-02-03 12:00:00");
        String time = sc.nextLine();
        
        try {
            LocalDateTime parsedTime = LocalDateTime.parse(time, formatter);
            LocalDateTime plusDaysResult = parsedTime.plusDays(7L);
            System.out.println("加7天后的日期和时间: " + plusDaysResult.format(formatter));
            LocalDateTime plusSecondsResult = parsedTime.plusSeconds(10L);
            System.out.println("加10s后的日期和时间: " + plusSecondsResult.format(formatter));
        } catch (Exception e) {
           
        }
       
	
	} */
	
	public static void addSchedulesPeriodly(Doctor doctor) {
	    Scanner sc = new Scanner(System.in);

	    System.out.println("请输入出诊安排编号：");
	    int ID = sc.nextInt();
	    sc.nextLine();
	    
	    System.out.println("请输入出诊安排内容：");
	    String schedule = sc.nextLine();
	    
	    System.out.println("请输入挂号数量：");
	    int restNumber = sc.nextInt();
	    sc.nextLine();

	    System.out.println("是否为周期性安排？(y/n)：");
	    String isRecurring = sc.nextLine();

	    int recurrenceDays = 0;
	    int recurrenceWeeks = 0;
	    int durationDays = 0;

	    if (isRecurring.equalsIgnoreCase("y")) {
	        System.out.println("请输入周期类型（1-每日，2-每周）：");
	        int recurrenceType = sc.nextInt();
	        sc.nextLine();

	        if (recurrenceType == 1) {
	            System.out.println("请输入每日的周期（天数）：");
	            recurrenceDays = sc.nextInt();
	            sc.nextLine();
	        } else if (recurrenceType == 2) {
	            System.out.println("请输入每周的周期（周数）：");
	            recurrenceWeeks = sc.nextInt();
	            sc.nextLine();
	        }

	        System.out.println("请输入持续时间（天数）：");
	        durationDays = sc.nextInt();
	        sc.nextLine();
	    }

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime now = LocalDateTime.now();

	    Userdao_Imp userdao_Imp = new Userdao_Imp();
	    boolean allSuccess = true;

	    for (int i = 0; i <= durationDays; i++) {
	        LocalDateTime scheduleDate = now;

	        if (recurrenceDays > 0) {
	            scheduleDate = now.plusDays(i * recurrenceDays);
	        } else if (recurrenceWeeks > 0) {
	            scheduleDate = now.plusWeeks(i * recurrenceWeeks);
	        }

	        String time = formatter.format(scheduleDate);
	        schedule sche = new schedule(ID, doctor.getIdNumber(), doctor.getName(), time, schedule, doctor.getDepartment(), 0, restNumber);
	        boolean line = userdao_Imp.scheduleInsert(sche);

	        if (!line) {
	            allSuccess = false;
	            break;
	        }
	    }

	    if (allSuccess) {
	        System.out.println("出诊安排添加成功,等待审批！");
	    } else {
	        System.out.println("出诊安排添加失败！");
	    }
	}
}

