package hospitol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.tjl.dao.Userdao_Imp;

public class Administrator extends User {
	
	public static ArrayList<String> updateLog;
	public static Map<String, User> disapproveUsers; 
	//public static Map<String, User> users; 
    public static Map<String, Patient> patients;// 用户存储，键为身份证号码
    public static Map<String, Doctor> doctors;
    public static Map<String, Administrator> administrators;
	
	public Administrator(String idNumber, String name, int age,String gender, String contactInfo, String address, String password){
		super(idNumber, name, age, gender, contactInfo, address, password, 1);
		this.updateLog = new ArrayList<String>();
		this.disapproveUsers = new HashMap<>();
		this.patients = new HashMap<>();
        this.doctors = new HashMap<>();
        this.administrators = new HashMap<>();
	}
	
	

	// 审批新注册用户
    public static void approveUser(Administrator admin) {
    	// 获取键集合，遍历键集合，通过get()方法获取对应的值
    	Set<String> keySet = UserOperate.disapproveUsers.keySet();
    	if(keySet.size()!=0)
    	{
    		for (String key : keySet) {
	    	   User user = UserOperate.disapproveUsers.get(key);
	    	   System.out.println("用户信息："+user.toString());
	    	   System.out.println("请审批该用户,输入-1退出审批，通过: 1，不通过: 0：");
	    	   
	    	   Scanner sc = new Scanner(System.in);
	    	   int flag = sc.nextInt();
	    	   sc.nextLine();  // 消费换行符
	    	   
	    	   if(flag == 1)
	    	   {
	    		   user.setApproved(true);
	        	   
	        	   Userdao_Imp userdao_Imp = new Userdao_Imp();
	        	   boolean line;
	        	   
	        	   switch (user.getUserType()) {
	    				case 1:
	    					line = userdao_Imp.insert(user);
	    					if(line != false)
	    					{
	    						System.out.println("用户添加成功！");
	    					}else {
	    						System.out.println("用户添加失败！");
	    					}
	    					break;
	    				case 2:
	    					line = userdao_Imp.insert(user);
	    					if(line != false)
	    					{
	    						System.out.println("用户添加成功！");
	    					}else {
	    						System.out.println("用户添加失败！");
	    					}
	    					break;
	    				case 3:
	    					line = userdao_Imp.insert(user);
	    					if(line != false)
	    					{
	    						System.out.println("用户添加成功！");
	    					}else {
	    						System.out.println("用户添加失败！");
	    					}
	    					break;
	    				default:
	    					break;
	        	   }
	        	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        	   LocalDateTime now = LocalDateTime.now();
	        	   String s = formatter.format(now);
	        	   String text = "审批了新注册用户："+user.getIdNumber();
	        	   updateLog log = new updateLog(1,admin.getIdNumber() , admin.getName(), s, text);
	        	   userdao_Imp.updateLogInsert(log);
	        	  // UserOperate.disapproveUsers.remove(key);//审批并添加到对应map中后删除该元素
	    	   }
	    	   else if(flag == -1)
	    	   {
	    		   break;
	    	   }
	    	   
	    	}
    	}
    	else {
			System.out.println("无待审批的新注册用户！");
		}
    	
	}
	
    //审批新的出诊安排
    public static void scheduleApprove(Administrator admin) {
    	boolean line;
    	Userdao_Imp userdao_Imp = new Userdao_Imp();
    	ArrayList<schedule> list = userdao_Imp.scheduleSelectDisApprove();
    	if(list.size() != 0)
    	{
    		for(schedule sche: list)
        	{
    			System.out.println("出诊信息："+sche.toString());
    		    System.out.println("请审批该信息，输入-1退出审批，通过: 1，不通过: 0：");
    	 	   Scanner sc = new Scanner(System.in);
    		   int flag = sc.nextInt();
    		   sc.nextLine();  // 消费换行符
        	   if(flag == 1)
        	   {
        		   line = userdao_Imp.approved(sche,sche.getID());
        	   }
        	   else if(flag == -1)
        	   {
        		   break;
        	   }
        	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        	   LocalDateTime now = LocalDateTime.now();
        	   String s = formatter.format(now);
        	   String text = "审批了新的出诊安排："+sche.getID();
        	   updateLog log = new updateLog(2,admin.getIdNumber() , admin.getName(), s, text);
        	   userdao_Imp.updateLogInsert(log);
        	   
        	}
    	}
    	else {
			System.out.println("暂无待审批的出诊安排！");
		}
		
	}
    
    public static void updateUser(Scanner sc,Administrator admin) {
    	Userdao_Imp userdao_Imp = new Userdao_Imp();
    	boolean line,flag=false;
    	System.out.println("请输入要修改的用户的身份证号：");
    	String idNumber = sc.nextLine();
    	
    	User user = userdao_Imp.select(idNumber);
    	if(user == null)
    	{
    		System.out.println("该用户不存在！");
    	}else
    	{
    		System.out.println("该用户信息如下：");
        	System.out.println(user.toString());
        	userdao_Imp.delete(idNumber);//删除该用户
        	
        	System.out.println("输入以下信息：");
            System.out.print("身份证号码: ");
            idNumber = sc.nextLine();
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
        	
        	
            switch (user.getUserType()) {
    	        case 1:
    		       	 Administrator adminUser = new Administrator(idNumber, name, age, gender, address, contactInfo, password);
    		       	 line = userdao_Imp.insert(adminUser);
    		       	 if(line == true)
    		       	 {
    		       		 flag = true;
    		       		 System.out.println("修改成功!");
    		       	 }else {
    					userdao_Imp.insert(user);
    				 }
    		       	 break;
            case 2:
    	       	 Patient patient = new Patient(idNumber, name, age, gender, address, contactInfo, password);
    	       	 line = userdao_Imp.insert(patient);
    	       	 if(line == true)
    	       	 {
    	       		flag = true;
    	       		 System.out.println("修改成功!");
    	       	 }else {
    				userdao_Imp.insert(user);
    			 }
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
                line = userdao_Imp.insert(doctor);
    	       	 if(line == true)
    	       	 {
    	       		 flag = true;
    	       		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	 	        	   LocalDateTime now = LocalDateTime.now();
	 	        	   String s = formatter.format(now);
	 	        	   String text = "修改了该用户信息："+user.getIdNumber();
	 	        	   updateLog log = new updateLog(1,admin.getIdNumber() , admin.getName(), s, text);
	 	        	  System.out.println("修改成功!");
	 	        	   userdao_Imp.updateLogInsert(log);
    	       	 }else {
    				userdao_Imp.insert(user);
    			 }
    	       	 break;
                
            default:
                System.out.println("无效的选择，请重试。");
                break;
            }
            if(flag == false)
            {
            	userdao_Imp.insert(user);
            }
            
    	}
    	
        
    }
    
}

