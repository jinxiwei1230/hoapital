package hospitol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tjl.dao.Userdao_Imp;

public class Patient extends User {
	private List<String> caseHistory; //病历
	public Patient(String idNumber, String name, int age,String gender, String contactInfo, String address, String password)
	{
		super(idNumber, name, age, gender, contactInfo, address, password, 2);
		this.caseHistory = new ArrayList<>();
	}
	
	
	public List<String> getCaseHistory() {
		return caseHistory;
	}
	public void setCaseHistory(List<String> caseHistory) {
		this.caseHistory = caseHistory;
	}
	
	public static void makeAppointment(Patient patient) {
		Scanner sc = new Scanner(System.in);
		Userdao_Imp userdao_Imp = new Userdao_Imp();
    	System.out.println("请输入要预约的时间");
		String time = sc.nextLine();
		schedule sche =Doctor.searchSchedules(time);
    	if(sche !=null)
    	{
    		if(sche.getRestNumber() > 0)
    		{
    			int rest = sche.getRestNumber();
    			System.out.println("剩余名额为："+rest);
    			System.out.println("是否确认预约并支付？");
    			System.out.println("1:确认预约并支付  0：取消预约");
    			int op = sc.nextInt();
    			if(op == 1)
    			{
    				Appointment ap = userdao_Imp.appointSelectTime(time, patient.getIdNumber());//查找该时间是否存在预约
    				if(ap == null)
    				{
    					boolean line = userdao_Imp.updateschedule(sche.getID(), rest-1);
        				try {
        					if(line == true)
            				{
            					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            					LocalDateTime now = LocalDateTime.now();
            					String s = formatter.format(now);
            					
            					Appointment appoint = new Appointment(60-rest,sche.getID(), patient.getIdNumber(),patient.getName(), sche.getTime(), sche.getName() , sche.getDepartment());
            					boolean line2 = userdao_Imp.appointInsert(appoint);
            					System.out.println("预约成功！");
            				}else {
            					System.out.println("预约失败！");
        					}
    					} catch (Exception e) {
    						System.out.println("预约失败！");
    					}
    				}
    				else {
						System.out.println("该时间您已有其他预约，无法预约！");
					}
    				
    			}
    		}else {
				System.out.println("该预约已无剩余名额！");
			}
    	}else {
			System.out.println("该时间无出诊安排，无法预约！");
		}
    		
	}
	
	public static void seeAppointments(Patient patient)//查看所有预约信息
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		ArrayList<Appointment> list = userdao_Imp.appointSelect(patient.getIdNumber());
		if(list.size()!=0)
		{
			list.forEach(user -> System.out.println(user.toString()));
		}else {
			System.out.println("您暂无历史记录！");
		}
		
	}
	
	public static Appointment searchAppointment(Patient patient,String time)//按时间查询预约信息（某一条）
	{
		Userdao_Imp userdao_Imp = new Userdao_Imp();
		Appointment appoint = userdao_Imp.appointSelectTime(time,patient.getIdNumber());
		if(appoint != null){
			System.out.println(appoint.toString());
		}
		else {
			System.out.println("您该时间暂无预约记录！");
		}
		return appoint;
	}
	
    // 取消预约
    public static boolean cancelAppointment(String idNumber) {
    	Scanner sc = new Scanner(System.in);
        Userdao_Imp userdao_Imp = new Userdao_Imp();
        ArrayList<Appointment> list = userdao_Imp.appointSelect(idNumber);
        if(list.size()>0)
        {
        	System.out.println("您的所有预约：");
        	list.forEach(appoint -> System.out.println(appoint.toString()));
        	System.out.println("请输入要取消的预约的ID：");
        	int appointmentId = sc.nextInt();
        	sc.nextLine();
        	
        	Appointment appoint = userdao_Imp.appointSelectbyID(appointmentId);
            if(appoint == null)
            {
            	System.out.println("无该预约记录！");
            }else {
            	boolean line = userdao_Imp.appointDelete(appointmentId, idNumber);
            	
        		schedule sche = userdao_Imp.searchSchedulesByID(appoint.getScheduleID());
        		if(sche!=null)
        			userdao_Imp.updateschedule(sche.getID(), sche.getRestNumber()+1);
            	if(line == true)
            	{
            		System.out.println("取消预约成功！");
            		return true;
            	}
    		}
        }
        else {
			System.out.println("您暂无预约！");
		}

        return false;
    }
	
}

