package com.tjl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tjl.jdbc.JDBCUtils;

import hospitol.Administrator;
import hospitol.Appointment;
import hospitol.Doctor;
import hospitol.Patient;
import hospitol.User;
import hospitol.caseHistory;
import hospitol.schedule;
import hospitol.updateLog;

public class Userdao_Imp implements Userdao {
	
	//用户数据表user
	private static final String SQL_USER_LOGIN = "SELECT * FROM user WHERE idNumber=? AND `password`=?";
	private static final String SQL_USER_INSERT = "INSERT INTO `user` VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_USER_UPDATE = "UPDATE `user` SET password=? WHERE idNumber=?";
	private static final String SQL_USER_DELETE = "DELETE FROM `user` WHERE idNumber=?";
	private static final String SQL_USER_SELECT = "SELECT * FROM user WHERE idNumber=?";
	private static final String SQL_USER_SHOEWALL = "SELECT * FROM `user`";
	
	//病历表caseHistory
	private static final String SQL_CASEHISTORY_INSERT= "INSERT INTO casehistory VALUES(?,?,?,?,?)";
	private static final String SQL_CASEHISTORY_DELETE= "DELETE FROM `casehistory` WHERE ID=?";
	private static final String SQL_CASEHISTORY_SELECT= "SELECT * FROM casehistory WHERE idNumber=?";
	
	//更新日志updataLog
	private static final String SQL_UPDATELOG_INSERT= "INSERT INTO updatalog VALUES(?,?,?,?,?)";
	private static final String SQL_UPDATELOG_DELETE= "DELETE FROM updatalog WHERE ID=?";
	private static final String SQL_UPDATELOG_SELECT= "SELECT * FROM updatalog WHERE idNumber=?";
	
	//出诊安排记录schedule
	private static final String SQL_SCHEDULE_INSERT= "INSERT INTO `schedule` VALUES(?,?,?,?,?,?,?,?)";
	private static final String SQL_SCHEDULE_DELETE= "DELETE FROM `schedule` WHERE ID=?";
	private static final String SQL_SCHEDULE_SELECT_TIME= "SELECT * FROM schedule WHERE time=?";
	private static final String SQL_SCHEDULE_SELECT_APPROVE= "SELECT * FROM schedule WHERE approved=? AND idNumber=?";
	private static final String SQL_SCHEDULE_APPROVE="UPDATE `schedule` SET approved=? WHERE ID=?";
	private static final String SQL_SCHEDULE_SELECT_DISAPPROVE = "SELECT * FROM schedule WHERE approved=?";
	private static final String SQL_SCHEDULE_UPDATE = "UPDATE `schedule` SET restNumber=? WHERE ID=?";
	private static final String SQL_SCHEDULE_ALL = "SELECT * FROM schedule WHERE approved=?";
	private static final String SQL_SCHEDULE_BYDEPARTMENT = "SELECT * FROM schedule WHERE department=?";
	private static final String SQL_SCHEDULE_BYID = "SELECT * FROM schedule WHERE ID=?";
	
	//预约记录表appointment
	private static final String SQL_APPOINTMENT_INSERT= "INSERT INTO `appointment` VALUES(?,?,?,?,?,?,?)";
	private static final String SQL_APPOINTMENT_DELETE= "DELETE FROM `appointment` WHERE id=? AND idNumber=?";
	private static final String SQL_APPOINTMENT_SELECT_TIME= "SELECT * FROM appointment WHERE time=? AND idNumber=?";
	private static final String SQL_APPOINTMENT_SELECT = "SELECT * FROM appointment WHERE idNumber=?";
	private static final String SQL_APPOINTMENT_SELECTBYID = "SELECT * FROM appointment WHERE id=?";
	
	
	
	@Override
	public int login(String idNumber,String password) {      //用户登录
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		//创建与编译环境
		PreparedStatement prepareStatement = null;//创建预编译环境
		ResultSet result = null;
		try {
			prepareStatement = conn.prepareStatement(SQL_USER_LOGIN);
			//设置sql语句中的参数
			prepareStatement.setString(1,idNumber);
			prepareStatement.setString(2,password);
			//执行语句
			result = prepareStatement.executeQuery();
			while(result.next())
			{
				int type = result.getInt("userType");
				return type;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, result);
		}
		return -1;
	}

	@Override
	public boolean insert(User user) {     //向数据库添加新的用户
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_USER_INSERT);
			prepareStatement.setString(1,user.getIdNumber());
			prepareStatement.setString(2,user.getName());
			prepareStatement.setString(3,user.getPassword());
			prepareStatement.setInt(4,user.getUserType());
			prepareStatement.setInt(5,user.getAge());
			prepareStatement.setString(6,user.getGender());
			prepareStatement.setString(7,user.getContactInfo());
			prepareStatement.setString(8,user.getAddress());
			if(user.getUserType()==3)
			{
				prepareStatement.setString(9,((Doctor) user).getHospital());
				prepareStatement.setString(10,((Doctor) user).getDepartment());
				prepareStatement.setString(11,((Doctor) user).getTitle());
				prepareStatement.setString(12,((Doctor) user).getSpecialty());
			}
			else {
				prepareStatement.setString(9,null);
				prepareStatement.setString(10,null);
				prepareStatement.setString(11,null);
				prepareStatement.setString(12,null);
			}
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean update(User user, String password) {      //更新数据库中用户信息
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_USER_UPDATE);
			//设置sql语句参数
			prepareStatement.setString(1,password);
			prepareStatement.setString(2,user.getIdNumber());
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}
	
	@Override
	public boolean delete(String idNumber) {   //删除数据库用户
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_USER_DELETE);
			//设置sql语句参数
			prepareStatement.setString(1,idNumber);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public User select(String idNumber) {   //在数据库中查找用户
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_USER_SELECT);
			//设置sql语句参数
			prepareStatement.setString(1,idNumber);
			ResultSet result = prepareStatement.executeQuery();
			
			String id,name,password,gender,contactInfo,address,hospital,department,specialty,title;
			int age,userType = 0;
			while(result.next())
			{
				id = result.getString("idNumber");
				name = result.getString("name");
				password = result.getString("password");
				userType = result.getInt("userType");
				age = result.getInt("age");
				gender = result.getString("gender");
				contactInfo = result.getString("contactInfo");
				address = result.getString("address");
				if(userType == 3)
				{
					hospital = result.getString("hospital");
					department = result.getString("department");
					specialty = result.getString("specialty");
					title = result.getString("title");
					return new Doctor(idNumber, name, age, gender, contactInfo, address, password, hospital, department, title, specialty);
				}
				else if(userType == 2)
				{
					return new Patient(idNumber, name, age, gender, contactInfo, address, password);
				}
				else if(userType == 1)
				{
					return new Administrator(idNumber, name, age, gender, contactInfo, address, password);
				}
 			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public ArrayList<User> showall() {
		ArrayList<User> userList = new ArrayList<User>();
		ArrayList<User> patientList = new ArrayList<User>();
		ArrayList<User> doctorList = new ArrayList<User>();
		ArrayList<User> administratorList = new ArrayList<User>();
		User user;
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_USER_SHOEWALL);
			//设置sql语句参数
			ResultSet result = prepareStatement.executeQuery();
			String id,name,password,gender,contactInfo,address,hospital,department,specialty,title;
			int age,userType = 0;
			while(result.next())
			{
				id = result.getString("idNumber");
				name = result.getString("name");
				password = result.getString("password");
				userType = result.getInt("userType");
				age = result.getInt("age");
				gender = result.getString("gender");
				contactInfo = result.getString("contactInfo");
				address = result.getString("address");
				if(userType == 3)
				{
					hospital = result.getString("hospital");
					department = result.getString("department");
					specialty = result.getString("specialty");
					title = result.getString("title");
					user = new Doctor(id, name, age, gender, contactInfo, address, password, hospital, department, title, specialty);
					doctorList.add(user);
				}
				else if(userType == 2)
				{
					user = new Patient(id, name, age, gender, contactInfo, address, password);
					patientList.add(user);
				}
				else if(userType == 1)
				{
					user = new Administrator(id, name, age, gender, contactInfo, address, password);
					administratorList.add(user);
				}
				
 			}
			userList.addAll(administratorList);
			userList.addAll(patientList);
			userList.addAll(doctorList);
			return userList;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}	
		return userList;
	}

	@Override
	public boolean scheduleInsert(schedule sche) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_INSERT);
			prepareStatement.setInt(1,sche.getID());
			prepareStatement.setString(2,sche.getIdNumber());
			prepareStatement.setString(3,sche.getName());
			prepareStatement.setString(4,sche.getTime());
			prepareStatement.setString(5,sche.getDepartment());
			prepareStatement.setString(6,sche.getSchedule());
			prepareStatement.setInt(7,sche.getApproved());
			prepareStatement.setInt(8,sche.getRestNumber());
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}
	
	

	@Override
	public boolean updateschedule(int ID,int rest) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_UPDATE);
			//设置sql语句参数
			prepareStatement.setInt(1,rest);
			prepareStatement.setInt(2,ID);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean updateLogInsert(updateLog log) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_UPDATELOG_INSERT);
			prepareStatement.setInt(1,log.getID());
			prepareStatement.setString(2,log.getIdNumber());
			prepareStatement.setString(3,log.getName());
			prepareStatement.setString(4,log.getTime());
			prepareStatement.setString(5,log.getUpdateLog());
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean caseHistoryInsert(caseHistory caseHistory) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_CASEHISTORY_INSERT);
			prepareStatement.setInt(1,caseHistory.getID());
			prepareStatement.setString(2,caseHistory.getIdNumber());
			prepareStatement.setString(3,caseHistory.getName());
			prepareStatement.setString(4,caseHistory.getTime());
			prepareStatement.setString(5,caseHistory.getCaseHistory());
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean updateLogDelete(int ID) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_UPDATELOG_DELETE);
			//设置sql语句参数
			prepareStatement.setInt(1,ID);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean caseHistoryDelete(int ID) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_CASEHISTORY_DELETE);
			//设置sql语句参数
			prepareStatement.setInt(1,ID);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean scheduleDelete(int ID) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_DELETE);
			//设置sql语句参数
			prepareStatement.setInt(1,ID);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}


	@Override
	public schedule scheduleSelectTime(String time) {
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_SELECT_TIME);
			//设置sql语句参数
			prepareStatement.setString(1,time);
			ResultSet result = prepareStatement.executeQuery();
			
			String idNumber,name,department,schedule;
			int ID,approve,restNumber = 0;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				approve = result.getInt("approved");
				restNumber = result.getInt("restNumber");
				time = result.getString("time");
				department = result.getString("department");
				schedule = result.getString("schedule");
				return new schedule(ID, idNumber, name, time, schedule, department, approve, restNumber);
 			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
				
		return null;
	}

	@Override
	public ArrayList<schedule> scheduleSelectApprove(String id) {
		ArrayList<schedule> scheduleList = new ArrayList<schedule>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_SELECT_APPROVE);
			//设置sql语句参数
			prepareStatement.setInt(1,1);
			prepareStatement.setString(2,id);
			ResultSet result = prepareStatement.executeQuery();
			
			String idNumber,name,department,schedule,time;
			int ID,approve,restNumber = 0;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				approve = result.getInt("approved");
				restNumber = result.getInt("restNumber");
				time = result.getString("time");
				department = result.getString("department");
				schedule = result.getString("schedule");
				schedule sche = new schedule(ID, idNumber, name, time, schedule, department, approve, restNumber);
				scheduleList.add(sche);
 			}
			return scheduleList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
				
		return null;
	}
	
	@Override
	public ArrayList<schedule> scheduleSelectDisApprove() {
		ArrayList<schedule> scheduleList = new ArrayList<schedule>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_SELECT_DISAPPROVE);
			//设置sql语句参数
			prepareStatement.setInt(1,0);
			ResultSet result = prepareStatement.executeQuery();
			
			String idNumber,name,department,schedule,time;
			int ID,approve,restNumber = 0;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				approve = result.getInt("approved");
				restNumber = result.getInt("restNumber");
				time = result.getString("time");
				department = result.getString("department");
				schedule = result.getString("schedule");
				schedule sche = new schedule(ID, idNumber, name, time, schedule, department, approve, restNumber);
				scheduleList.add(sche);
 			}
			return scheduleList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
				
		return null;
	}

	@Override
	public ArrayList<caseHistory> caseHistorySelect(String idNumber) {
		ArrayList<caseHistory> caseList = new ArrayList<caseHistory>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_CASEHISTORY_SELECT);
			//设置sql语句参数
			prepareStatement.setString(1,idNumber);
			ResultSet result = prepareStatement.executeQuery();
			
			String name,casehistory,time;
			int ID;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				time = result.getString("time");
				casehistory = result.getString("casehistory");
				caseHistory caseHistory = new caseHistory(ID, idNumber, name, time, casehistory);
				caseList.add(caseHistory);
 			}
			return caseList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public ArrayList<updateLog> logSelect(String idNumber) {
		ArrayList<updateLog> logList = new ArrayList<updateLog>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_UPDATELOG_SELECT);
			//设置sql语句参数
			prepareStatement.setString(1,idNumber);
			ResultSet result = prepareStatement.executeQuery();
			
			String name,updatelog,time;
			int ID;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				time = result.getString("time");
				updatelog = result.getString("updateLog");
				updateLog log = new updateLog(ID, idNumber, name, time, updatelog);
				logList.add(log);
 			}
			return logList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public boolean approved(schedule sche,int ID) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_APPROVE);
			//设置sql语句参数
			prepareStatement.setInt(1,1);
			prepareStatement.setInt(2,ID);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean appointInsert(Appointment appointment) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_APPOINTMENT_INSERT);
			prepareStatement.setString(1,appointment.getIdNumber());
			prepareStatement.setString(2,appointment.getName());
			prepareStatement.setInt(3,appointment.getID());
			prepareStatement.setString(4,appointment.getTime());
			prepareStatement.setString(5,appointment.getDoctorName());
			prepareStatement.setString(6,appointment.getDepartment());
			prepareStatement.setInt(7,appointment.getScheduleID());
			int line = prepareStatement.executeUpdate();
			
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return false;
	}

	@Override
	public boolean appointDelete(int ID, String idNumber) {
		//连接数据库，创建连接对象conn
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_APPOINTMENT_DELETE);
			//设置sql语句参数
			prepareStatement.setInt(1,ID);
			prepareStatement.setString(2,idNumber);
			int line = prepareStatement.executeUpdate();
			return line>0?true:false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}		
		return false;
	}

	@Override
	public Appointment appointSelectTime(String time, String idNumber) {
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_APPOINTMENT_SELECT_TIME);
			//设置sql语句参数
			prepareStatement.setString(1,time);
			prepareStatement.setString(2,idNumber);
			ResultSet result = prepareStatement.executeQuery();
			
			String name,doctoeName,department;
			int id,scheduleID;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				id = result.getInt("id");
				scheduleID = result.getInt("scheduleID");
				time = result.getString("time");
				doctoeName= result.getString("doctoeName");
				department= result.getString("department");
				return new Appointment(id, scheduleID,idNumber, name, time, doctoeName,department);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public ArrayList<Appointment> appointSelect( String idNumber) {
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_APPOINTMENT_SELECT);
			//设置sql语句参数
			prepareStatement.setString(1,idNumber);
			ResultSet result = prepareStatement.executeQuery();
			
			String name,time,doctoeName,department;
			int id,scheduleID;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				id = result.getInt("id");
				scheduleID = result.getInt("scheduleID");
				time = result.getString("time");
				doctoeName= result.getString("doctoeName");
				department= result.getString("department");
				Appointment appoint = new Appointment(id, scheduleID,idNumber, name, time, doctoeName,department);
				list.add(appoint);
			}
			return list;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public ArrayList<schedule> allschedule() {
		ArrayList<schedule> scheduleList = new ArrayList<schedule>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_ALL);
			//设置sql语句参数
			prepareStatement.setInt(1,1);
			ResultSet result = prepareStatement.executeQuery();
			
			String idNumber,name,department,schedule,time;
			int ID,approve,restNumber = 0;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				approve = result.getInt("approved");
				restNumber = result.getInt("restNumber");
				time = result.getString("time");
				department = result.getString("department");
				schedule = result.getString("schedule");
				schedule sche = new schedule(ID, idNumber, name, time, schedule, department, approve, restNumber);
				scheduleList.add(sche);
 			}
			return scheduleList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
				
		return null;
	}

	@Override
	public ArrayList<schedule> searchSchedulesByDepartment(String department) {
		ArrayList<schedule> scheduleList = new ArrayList<schedule>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_BYDEPARTMENT);
			//设置sql语句参数
			prepareStatement.setString(1,department);
			ResultSet result = prepareStatement.executeQuery();
			
			String idNumber,name,schedule,time;
			int ID,approve,restNumber = 0;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				approve = result.getInt("approved");
				restNumber = result.getInt("restNumber");
				time = result.getString("time");
				department = result.getString("department");
				schedule = result.getString("schedule");
				schedule sche = new schedule(ID, idNumber, name, time, schedule, department, approve, restNumber);
				scheduleList.add(sche);
 			}
			return scheduleList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public schedule searchSchedulesByID(int ID) {
		ArrayList<schedule> scheduleList = new ArrayList<schedule>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_SCHEDULE_BYID);
			//设置sql语句参数
			prepareStatement.setInt(1,1);
			ResultSet result = prepareStatement.executeQuery();
			
			String idNumber,name,schedule,time,department;
			int approve,restNumber = 0;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				ID = result.getInt("ID");
				approve = result.getInt("approved");
				restNumber = result.getInt("restNumber");
				time = result.getString("time");
				department = result.getString("department");
				schedule = result.getString("schedule");
				return new schedule(ID, idNumber, name, time, schedule, department, approve, restNumber);
 			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}

	@Override
	public Appointment appointSelectbyID(int ID) {
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Connection conn= JDBCUtils.getConnection();
		PreparedStatement prepareStatement = null;//创建预编译环境
		try {
			prepareStatement = conn.prepareStatement(SQL_APPOINTMENT_SELECTBYID);
			//设置sql语句参数
			prepareStatement.setInt(1,ID);
			ResultSet result = prepareStatement.executeQuery();
			
			String name,time,doctoeName,department,idNumber;
			int id,scheduleID;
			while(result.next())
			{
				idNumber = result.getString("idNumber");
				name = result.getString("name");
				id = result.getInt("id");
				scheduleID = result.getInt("scheduleID");
				time = result.getString("time");
				doctoeName= result.getString("doctoeName");
				department= result.getString("department");
				return new Appointment(id, scheduleID,idNumber, name, time, doctoeName,department);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn, prepareStatement, null);
		}
		return null;
	}
	
	

}
