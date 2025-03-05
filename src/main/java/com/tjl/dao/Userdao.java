package com.tjl.dao;

import java.util.ArrayList;

import hospitol.Appointment;
import hospitol.User;
import hospitol.caseHistory;
import hospitol.schedule;
import hospitol.updateLog;

public interface Userdao {
	
	int login(String idNumber,String password);//用户登录接口
	
	boolean insert(User user);//添加新的用户
	
	boolean update(User user,String password);//更新用户信息
	
	boolean delete(String idNumber);//删除用户
	
	User select(String idNumber);//根据身份证号查询用户
	
	ArrayList<User> showall();//展示所有用户信息
	
	boolean updateLogInsert(updateLog log);//添加新的修改日志
	boolean caseHistoryInsert(caseHistory caseHistory);//添加新的病历
	boolean scheduleInsert(schedule sche);//添加新的出诊安排
	boolean updateLogDelete(int ID);//删除新的修改日志
	boolean caseHistoryDelete(int ID);//删除新的病历
	boolean scheduleDelete(int ID);//删除新的出诊安排
	
	boolean updateschedule(int ID,int rest);//更新信息出诊

	schedule scheduleSelectTime(String time);//按时间查询出诊信息
	ArrayList<schedule> scheduleSelectApprove(String id);//展示本人所有已发布的出诊信息
	ArrayList<schedule> allschedule();//展示所有已发布的出诊信息
	ArrayList<schedule> scheduleSelectDisApprove();//得到所有未审批出诊信息
	ArrayList<schedule> searchSchedulesByDepartment(String department);//根据科室查询出诊信息
	schedule searchSchedulesByID(int ID);//根据ID查询出诊信息
	
	ArrayList<caseHistory> caseHistorySelect(String idNumber);//按idNumber查询病历信息
	ArrayList<updateLog> logSelect(String idNumber);//按idNumber查询日志信息
	
	boolean approved(schedule sche,int ID);//审批出诊信息
	
	
	boolean appointInsert(Appointment appointment);//添加新的预约安排
	boolean appointDelete(int ID,String inNumber);//删除新的预约安排
	Appointment appointSelectTime(String time,String idNumber);//按时间查询预约信息
	ArrayList<Appointment> appointSelect( String idNumber);//查询所有预约信息
	Appointment appointSelectbyID( int ID);//查询所有预约信息
}
