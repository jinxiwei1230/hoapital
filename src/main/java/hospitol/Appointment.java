package hospitol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.tjl.dao.Userdao_Imp;

public class Appointment {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	String s = formatter.format(now);
	private int ID;
	private int scheduleID;
	private String idNumber;
	private String name;
	private String time;
	private String department;
	private String doctorName;
	
	
	public Appointment(int ID,int scheduleID,String idNumber,String name,String time,String doctorName,String department)
	{
		this.department = department;
		this.ID = ID;
		this.scheduleID = scheduleID;
		this.idNumber = idNumber;
		this.name = name;
		this.time = time;
		this.doctorName = doctorName;
	}
	 @Override
    public String toString() {
        return "{ID='" + ID + ",scheduleID=" + scheduleID + ",idNumber=" + idNumber + ",name=" + name + ", time=" + time + ", doctorName=" + doctorName + ", department=" + department + "}";
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public int getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}


	public static void maketodayAppointment(Patient patient) {
	    Scanner sc = new Scanner(System.in);
	    Userdao_Imp userdao_Imp = new Userdao_Imp();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime now = LocalDateTime.now();

	    System.out.println("请输入要预约的时间 (yyyy-MM-dd HH:mm:ss)：");
	    String time = sc.nextLine();
	    LocalDateTime appointmentTime = LocalDateTime.parse(time, formatter);

	    // 检查是否在允许的预约时间范围内
	    if (!isWithinAppointmentTimeRange(now, appointmentTime)) {
	        System.out.println("不在允许的预约时间范围内！");
	        return;
	    }

	    schedule sche = Doctor.searchSchedules(time);
	    if (sche != null) {
	        if (sche.getRestNumber() > 0) {
	            int rest = sche.getRestNumber();
	            System.out.println("剩余名额为：" + rest);
	            System.out.println("是否确认预约并支付？");
	            System.out.println("1:确认预约并支付  0：取消预约");
	            int op = sc.nextInt();
	            if (op == 1) {
	                Appointment ap = userdao_Imp.appointSelectTime(time, patient.getIdNumber());//查找该时间是否存在预约
	                if (ap == null) {
	                    boolean line = userdao_Imp.updateschedule(sche.getID(), rest - 1);
	                    try {
	                        if (line == true) {
	                            String s = formatter.format(now);
	                            Appointment appoint = new Appointment(60 - rest, sche.getID(), patient.getIdNumber(), patient.getName(), sche.getTime(), sche.getName(), sche.getDepartment());
	                            boolean line2 = userdao_Imp.appointInsert(appoint);
	                            System.out.println("预约成功！");
	                        } else {
	                            System.out.println("预约失败！");
	                        }
	                    } catch (Exception e) {
	                        System.out.println("预约失败！");
	                    }
	                } else {
	                    System.out.println("该时间您已有其他预约，无法预约！");
	                }
	            }
	        } else {
	            System.out.println("该预约已无剩余名额！");
	        }
	    } else {
	        System.out.println("该时间无出诊安排，无法预约！");
	    }
	}

	public static boolean isWithinAppointmentTimeRange(LocalDateTime now, LocalDateTime appointmentTime) {
	    LocalDateTime morningStart = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 7, 0);
	    LocalDateTime morningEnd = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 9, 0);
	    LocalDateTime afternoonEnd = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 14, 0);

	    // 检查当前时间是否在早上7点到9点之间
	    if (now.isAfter(morningStart) && now.isBefore(morningEnd)) {
	        // 允许预约当天上午号
	        return appointmentTime.isBefore(LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 12, 0));
	    }

	    // 检查当前时间是否在早上7点到下午2点之间
	    if (now.isBefore(afternoonEnd)) {
	        // 允许预约当天下午号
	        return appointmentTime.isAfter(LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 12, 0));
	    }

	    return false;
	}

}
