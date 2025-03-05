package hospitol;

import java.util.ArrayList;
import java.util.Scanner;

import com.tjl.dao.Userdao_Imp;

public class Main {
	
	private static UserOperate userOperate = new UserOperate();
	 public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		 //initialize();//初始化一个管理员，用户名和密码均为admin
		 while(true)
		 {
			 try {
				 System.out.println("1.注册");
				 System.out.println("2.登录");
				 System.out.println("3.退出");
				 
				 int choice = sc.nextInt();
		         sc.nextLine();  // 消费换行符
		         
		         switch (choice) {
		             case 1:
		                 register(sc);
		                 break;
		             case 2:
		                 Login(sc);
		                 break;
		             case 3:
		                 System.out.println("已退出...");
		                 sc.close();
		                 return;
		             default:
		                 System.out.println("选择无效，请重试。");
		         }
			} catch (Exception e) {
				System.out.println("输入不合法11！");
				sc.nextLine();
			}
			 
		 }
	 }
	 
	 //用户初始化
	 private static void initialize()
	 {
		 Administrator admin = new Administrator("admin", "admin", 30, "M", "Admin ContactInfo", "Admin Address", "admin");
	 }
	 
	 //用户注册
	 public static void register(Scanner sc)
	 {
		 System.out.println("选择注册类型：");
		 System.out.println("1. 管理员");
         System.out.println("2. 患者");
         System.out.println("3. 医生");
         try {
        	 int userTypeChoice = sc.nextInt();
             sc.nextLine();  // 消费换行符
             
             System.out.println("输入以下信息：");
             System.out.print("身份证号码: ");
             String idNumber = sc.nextLine();
             System.out.print("姓名: ");
             String name = sc.nextLine();
             System.out.print("年龄: ");
             int age = sc.nextInt();
             sc.nextLine();  // 消费换行符
             System.out.print("性别（M/F）: ");
             String gender = sc.nextLine();
             System.out.print("联系方式: ");
             String contactInfo = sc.nextLine();
             System.out.print("地址: ");
             String address = sc.nextLine();
             System.out.print("密码: ");
             String password = sc.nextLine();

             switch (userTypeChoice) {
    	         case 1:
    	        	 Administrator adminUser = new Administrator(idNumber, name, age, gender, address, contactInfo, password);
    	        	 UserOperate.addDisapprove(adminUser);//先加入未审批的队列中等待审批
    	        	 System.out.println("管理员注册申请成功，请等待审批!");
    	             
    	             break;
    	         case 2:
    	        	 Patient patient = new Patient(idNumber, name, age, gender, address, contactInfo, password);
    	        	 UserOperate.addDisapprove(patient);//先加入未审批的队列中等待审批
    	        	 System.out.println("患者注册申请成功，请等待审批!");
    	             break;
    	             
    	         case 3:
    	        	 System.out.print("所在医院: ");
    	             String hospital = sc.nextLine();
    	             System.out.print("科室: ");
    	             String department = sc.nextLine();
    	             System.out.print("职称: ");
    	             String title = sc.nextLine();
    	             System.out.print("专长: ");
    	             String specialty = sc.nextLine();
    	          
    	             Doctor doctor = new Doctor(idNumber, name, age, gender, contactInfo, address, password, hospital, department, title, specialty);
    	             UserOperate.addDisapprove(doctor);//先加入未审批的队列中等待审批
    	             System.out.println("医生注册申请成功，请等待审批!");
    	             break;
    	             
    	         default:
    	             System.out.println("无效的选择，请重试。");
    	             break;
    	     }
		} catch (Exception e) {
			System.out.println("输入不合法！");
		}
         
	 }
	 
	 //用户登录
	 public static void Login(Scanner sc)
	 {
		 System.out.println("输入身份证号码:");
         String idNumber = sc.nextLine();
         System.out.println("输入密码:");
         String password = sc.nextLine();
         Userdao_Imp userdao_Imp = new Userdao_Imp();
         int type = userdao_Imp.login(idNumber,password);
         if(type != -1)
         {
        	 User user = userdao_Imp.select(idNumber);
        	 switch (type) {
             case 1:
            	 if(user.getIdNumber().equals(idNumber) && user.getPassword().equals("admin"))
            	 {
            		 System.out.println("修改初始密码，请输入新的密码：");
            		 String newPassword = sc.nextLine();
            		 userdao_Imp.update(user,newPassword);
            		 System.out.println("修改密码成功！");
            	 }
                 adminMenu(sc,(Administrator)user);
            	 break;
             case 2:
            	 patientMenu(sc, (Patient)user);
            	 break;
             case 3:
            	 doctorMenu(sc, (Doctor) user);
            	 break;
             default:
            	 break;
             }
         }else {
        	 System.out.println("登陆失败，用户名或密码错误！");
		}
	 }
	 
	 //管理员菜单1
	 public static void adminMenu(Scanner sc, Administrator admin){
		 while (true) {
			Userdao_Imp userdao_Imp = new Userdao_Imp();
            System.out.println("1. 查看所有用户");
            System.out.println("2. 审批新注册用户/修改用户信息");
            System.out.println("3. 审批出诊信息");
            System.out.println("4. 退出登录");
            int choice = sc.nextInt();
            sc.nextLine();  // 消费换行符

            switch (choice) {
                case 1:
                	ArrayList<User> userList;
                	userList = userdao_Imp.showall();
                    userList.forEach(user -> System.out.println(user.toString()));
                    break;
                case 2:
                    updateUser(sc,admin);
                    break;
                case 3:
                	Administrator.scheduleApprove(admin);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("选择无效，请重试。");
            }
        }
	 }
	
	 //医生菜单
	private static void doctorMenu(Scanner sc, Doctor doctor) {
        while (true) {
            System.out.println("1. 发布出诊信息");
            System.out.println("2. 删除出诊信息");
            System.out.println("3. 查看出诊信息");
            System.out.println("4. 退出登录");
            int choice = sc.nextInt();
            sc.nextLine();  // 消费换行符

            switch (choice) {
                case 1:
                	Doctor.addSchedules((Doctor)doctor);
                    break;
                case 2:
                	System.out.println("请输入所要删除信息的ID");
                	int ID = sc.nextInt();
                	sc.nextLine();
                	Doctor.deleteSchedules(ID);
                    break;
                case 3:
                	System.out.println("1.按时间查询: ");
                	System.out.println("2.查询所有出诊安排: ");
                	int op = sc.nextInt();
                	sc.nextLine();
                	if(op ==1)
                	{
                		System.out.println("请输入出诊安排时间：");
                		String time = sc.nextLine();
                		Doctor.searchSchedules(time);
                	}
                	else {
						Doctor.seeSchedules((Doctor)doctor);
					}
                	
                    break;
                case 4:
                    return;
                default:
                    System.out.println("选择无效，请重试。");
            }
        }
    }
	
	//患者菜单
	public static void patientMenu(Scanner sc, Patient patient){
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		while (true) {
			
            System.out.println("1. 预约挂号");
            System.out.println("2. 查看预约");
            System.out.println("3. 查询出诊安排");
            System.out.println("4. 取消预约");
            System.out.println("5. 退出登录");
            int choice = sc.nextInt();
            sc.nextLine();  // 消费换行符
            switch (choice) {
                case 1:
                    Patient.makeAppointment(patient);
                    break;
                case 2:
                	try {
                		System.out.println("1.按时间查询预约: ");
                    	System.out.println("2.查询所有历史预约: ");
                    	int op = sc.nextInt();
                    	sc.nextLine();
                    	if(op ==1)
                    	{
                    		System.out.println("请输入预约时间：");
                    		String time = sc.nextLine();
                    		Patient.searchAppointment(patient, time);
                    	}
                    	else if(op == 2 ){
                    		Patient.seeAppointments(patient);
    					}else {
    						System.out.println("输入不合法！");
    					}
					} catch (Exception e) {
						System.out.println("输入不合法！");
						sc.nextLine();
					}
                	
                    break;
                case 3:
                	System.out.println("1.查看所有出诊安排: ");
                	System.out.println("2.根据科室查看出诊安排: ");
                	int op2 =sc.nextInt();
                	sc.nextLine();
                	if(op2 == 1)
                	{
                		ArrayList<schedule> list = userdao_Imp.allschedule();
                		if(list.size()!=0)
                		{
                			list.forEach(sche -> System.out.println(sche.toString()));
                		}
                		else {
							System.out.println("暂无出诊安排！");
						}
                		
                	}
                	else if(op2 == 2) 
                	{
                		System.out.println("请输入要查询的科室：");
                		String department = sc.nextLine();
                		ArrayList<schedule> list = userdao_Imp.searchSchedulesByDepartment(department);
                		if(list.size()!=0)
                		{
                			list.forEach(sche -> System.out.println(sche.toString()));
                		}
                		else {
							System.out.println("该科室下暂无出诊安排！");
						}
                		
					}else
					{
						System.out.println("输入错误！");
					}
                    break;
                case 4:
                	
                	Patient.cancelAppointment(patient.getIdNumber());
                    break;
                case 5:
                	return;
                default:
                    System.out.println("选择无效，请重试。");
            }
        }
	}
	
	//管理员菜单2
	public static void updateUser(Scanner sc,Administrator admin){
		System.out.println("1. 审批新注册用户");
		System.out.println("2. 修改用户信息");
		int choice = sc.nextInt();
        sc.nextLine();  // 消费换行符
		switch (choice) {
        case 1:
           Administrator.approveUser(admin);
            break;
        case 2:
           Administrator.updateUser(sc,admin);
            break;

        default:
            System.out.println("选择无效，请重试。");
		}
		
	}
}
