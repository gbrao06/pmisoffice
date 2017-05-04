package openpro.web.beans.managed;

import java.math.BigInteger;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import openpro.datamodel.entities.Timesheettbl;
import openpro.datamodel.entities.Wbstbl;
import openpro.ejb.session.TaskEJB;
import openpro.ejb.session.TimeSheetEJB;
import openpro.ejb.session.UserTimeEntryEJB;

import org.primefaces.event.CellEditEvent;

@ManagedBean (name = "TimeSheetWebBean")
@SessionScoped
public class TimeSheetBeanManaged {

	@EJB
	TimeSheetEJB timeEJB; 
	
	
	//@ManagedProperty("#{genreLabelListJSF}")	
	@ManagedProperty("#{WbsWebBean}")
	private WbsBeanManaged wbsBean;
	
	@ManagedProperty("#{UserWebBean}")
	private UserBeanManaged userBean;
	
	@ManagedProperty("#{UserTimeEntryWebBean}")
	private UserTimeEntryBeanManaged userTimeEntryBean;
	
	private TimeSheetWbsWrapper selectedWbsWrapper;
	
	private boolean foundEntry=false;
	/**
	 * @return the userTimeEntryBean
	 */
	public synchronized UserTimeEntryBeanManaged getUserTimeEntryBean() {
		return userTimeEntryBean;
	}
	/**
	 * @param userTimeEntryBean the userTimeEntryBean to set
	 */
	public synchronized void setUserTimeEntryBean(
			UserTimeEntryBeanManaged userTimeEntryBean) {
		this.userTimeEntryBean = userTimeEntryBean;
	}
	public TimeSheetBeanManaged()
	{
		System.out.println("TimeSheetBeanManaged::Constructor");
		foundEntry=false;
		timeList.clear();	
	}
	/**
	 * @return the selectedWbsWrapper
	 */
	public synchronized TimeSheetWbsWrapper getSelectedWbsWrapper() {
		return selectedWbsWrapper;
	}

	/**
	 * @param selectedWbsWrapper the selectedWbsWrapper to set
	 */
	public synchronized void setSelectedWbsWrapper(
			TimeSheetWbsWrapper selectedWbsWrapper) {
		this.selectedWbsWrapper = selectedWbsWrapper;
	}

	/**
	 * @return the userBean
	 */
	public synchronized UserBeanManaged getUserBean() {
		return userBean;
	}

	/**
	 * @param userBean the userBean to set
	 */
	public synchronized void setUserBean(UserBeanManaged userBean) {
		this.userBean = userBean;
	}

	List<TimeSheetWbsWrapper> timeList=new ArrayList<TimeSheetWbsWrapper>();
	
	private float totalWeekEffort;
	
	/**
	 * @return the totalWeekEffort
	 */
	public synchronized float getTotalWeekEffort() {
		return totalWeekEffort;
	}
	/**
	 * @param totalWeekEffort the totalWeekEffort to set
	 */
	public synchronized void setTotalWeekEffort(float totalWeekEffort) {
		this.totalWeekEffort = totalWeekEffort;
	}
	/**
	 * @return the timeList
	 */
	public synchronized List<TimeSheetWbsWrapper> getTimeList() {
		System.out.println("getTimeList:called");
		return timeList;
	}
	/**
	 * @param timeList the timeList to set
	 */
	public synchronized void setTimeList(List<TimeSheetWbsWrapper> timeList) {
		this.timeList = timeList;
	}
	/**
	 * @return the wbsBean
	 */
	public synchronized WbsBeanManaged getWbsBean() {
		return wbsBean;
	}

	/**
	 * @param wbsBean the wbsBean to set
	 */
	public synchronized void setWbsBean(WbsBeanManaged wbsBean) {
		this.wbsBean = wbsBean;
	}
	
	public class TimeSheetWbsWrapper
	{
		private Wbstbl wbsTable;
		
		public TimeSheetWbsWrapper(Wbstbl tbl)
		{
			wbsTable=tbl;
			
		}
		
		private float sundayTime;
	    private float mondayTime;
	    private float tuesdayTime;
	    private float wednsdayTime;
	    private float thursdayTime;
	    private float fridayTime;
	    private float saturdayTime;
	    
	    String wbsId;
	    private float totalTime;
	    
		/**
		 * @return the wbsTable
		 */
		public synchronized Wbstbl getWbsTable() {
			return wbsTable;
		}

		/**
		 * @param wbsTable the wbsTable to set
		 */
		public synchronized void setWbsTable(Wbstbl wbsTable) {
			this.wbsTable = wbsTable;
		}

		/**
		 * @return the wbsId
		 */
		public synchronized String getWbsId() {
			return wbsId;
		}

		/**
		 * @param wbsId the wbsId to set
		 */
		public synchronized void setWbsId(String wbsId) {
			this.wbsId = wbsId;
		}

		/**
		 * @return the mondayTime
		 */
		public synchronized float getMondayTime() {
			return mondayTime;
		}

		/**
		 * @param mondayTime the mondayTime to set
		 */
		public synchronized void setMondayTime(float mondayTime) {
			
			this.mondayTime = mondayTime;
			System.out.println("wbsId:"+wbsTable.getWbsId()+"mondayTime=:"+this.mondayTime);
		}
		
		/**
		 * @return the tuesdayTime
		 */
		public synchronized float getTuesdayTime() {
			
			return tuesdayTime;
		}

		/**
		 * @param tuesdayTime the tuesdayTime to set
		 */
		public synchronized void setTuesdayTime(float tuesdayTime) {
			
			
			this.tuesdayTime = tuesdayTime;
			System.out.println("wbsId:"+wbsTable.getWbsId()+"tuesdayTime=:"+this.tuesdayTime);
		}

		/**
		 * @return the wednsdayTime
		 */
		public synchronized float getWednsdayTime() {
			
			return wednsdayTime;
			
		}

		/**
		 * @param wednsdayTime the wednsdayTime to set
		 */
		public synchronized void setWednsdayTime(float wednsdayTime) {
			
			this.wednsdayTime = wednsdayTime;
			System.out.println("wbsId:"+wbsTable.getWbsId()+"wednsdayTime=:"+this.wednsdayTime);
			
		}

		/**
		 * @return the thursdayTime
		 */
		public synchronized float getThursdayTime() {
			return thursdayTime;
		}

		/**
		 * @param thursdayTime the thursdayTime to set
		 */
		public synchronized void setThursdayTime(float thursdayTime) {
			this.thursdayTime = thursdayTime;
			System.out.println("wbsId:"+wbsTable.getWbsId()+"thursdayTime=:"+this.thursdayTime);
		}

		/**
		 * @return the fridayTime
		 */
		public synchronized float getFridayTime() {
			return fridayTime;
			
		}

		/**
		 * @param fridayTime the fridayTime to set
		 */
		public synchronized void setFridayTime(float fridayTime) {
			this.fridayTime = fridayTime;
			System.out.println("wbsId:"+wbsTable.getWbsId()+"fridayTime=:"+this.fridayTime);
		}

		/**
		 * @return the saturdayTime
		 */
		public synchronized float getSaturdayTime() {
			return saturdayTime;
		}

		/**
		 * @param saturdayTime the saturdayTime to set
		 */
		public synchronized void setSaturdayTime(float saturdayTime) {
			this.saturdayTime = saturdayTime;
		}

		/**
		 * @return the sundayTime
		 */
		public synchronized float getSundayTime() {
			return sundayTime;
		}

		
		/**
		 * @param sundayTime the sundayTime to set
		 */
		public synchronized void setSundayTime(float sundayTime) {
			this.sundayTime = sundayTime;
		}
	    
		/**
		 * @return the totalTime
		 */
		public synchronized float getTotalTime() {
			return totalTime;
		}

		/**
		 * @param totalTime the totalTime to set
		 */
		public synchronized void setTotalTime(float totalTime) {
			this.totalTime = totalTime;
		}

		public float calculateTotalWeekTime()
		{
			return (this.totalTime=this.sundayTime+this.mondayTime+this.tuesdayTime+this.wednsdayTime+this.thursdayTime+this.fridayTime+this.saturdayTime);			
		}
		
	}
	
	//}}end of wrapper
	
	private Date weekDate;
    private String user_name="gbrao06";
    
    /**
	 * @return the weekDate
	 */
	public synchronized Date getWeekDate() {
		return weekDate;
	}

	/**
	 * @param weekDate the weekDate to set
	 */
	public synchronized void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
	}
	/**
	 * @return the user_name
	 */
	public synchronized String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public synchronized void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getThisWeek()
	{
		// get today and clear time of day
		System.out.println("Entered:getThisWeek");
		
		Calendar cal = Calendar.getInstance();
	
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.setTimeZone(TimeZone.getDefault());
		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_WEEK,cal.getFirstDayOfWeek());//Sunday
		System.out.println("Start of this week:       " + cal.getTime());
		System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
		
		// start of the next week
		//cal.add(Calendar.WEEK_OF_YEAR, 1);
		//System.out.println("Start of the next week:   " + cal.getTime());
		//System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
		
		weekDate=cal.getTime();
		System.out.println("Exited:getThisWeek="+cal.getTime().getDate());
	//	weekDate.getda
	   	    
		return weekDate;
	}

	public int getThisWeekDate(int day)
	{
		//1 sunday,2 monday,3 tuesday, 4 wednsday, 5 thursday,6 friday,7 satday
		// get today and clear time of day
		System.out.println("Entered:getThisWeekDate");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()+day);
		System.out.println("Start of this week:       " + cal.getTime());
		System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
		
		// start of the next week
		//cal.add(Calendar.WEEK_OF_YEAR, 1);
		//System.out.println("Start of the next week:   " + cal.getTime());
		//System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
		System.out.println("Exited:getThisWeekDate:Date="+cal.getTime().getDate());
		return cal.getTime().getDate();
	//	weekDate.getda	
	}
	
	public String getTimeSheetTag()
	{
		foundEntry=false;
		foundEntry=userTimeEntryBean.isTimeSheetFound(userBean.getCurrentUserID(),getThisWeek());
		if(foundEntry)
			return "TimeSheet Already Submitted";
		
		return "Fill TimeSheet and Submit";
	}
	
	public List<TimeSheetWbsWrapper> get_user_timesheet_wbs_list()
	{		
		System.out.println("enetered:TimeSheetWebBean::get_user_timesheet_wbs_list");
		//check if already exist
		if(foundEntry)
			return null;
		if(timeList!=null && timeList.size()>0)
		{
			totalWeekEffort=0;
			for(int i=0;i<timeList.size();i++)
			{
				totalWeekEffort+=timeList.get(i).calculateTotalWeekTime();				
			}
			return timeList;
		}
		
		List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
		wbsList.clear();
		wbsList=wbsBean.get_user_wbs_list();	
		//update timeList only if wbstbl not present.
		timeList.clear();
		if(wbsList!=null && wbsList.size()>0)
		{
			for(int i=0;i<wbsList.size();i++)
			{
				timeList.add(new TimeSheetWbsWrapper(wbsList.get(i)));
			}
		}
		
		System.out.println("exited:TimeSheetWebBean::get_user_timesheet_wbs_list");				
		return timeList;
	}
	
	
	public String getMonday()
	{
		System.out.println("Entered:getMonday");
		int date=getThisWeekDate(1);
		
		String dayName="Mon";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}

	public String getTuesday()
	{
		int date=getThisWeekDate(2);
		String dayName="Tue";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}


	public String getWednsday()
	{
		int date=getThisWeekDate(3);
		
		String dayName="Wed";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}

	public String getThursday()
	{
		int date=getThisWeekDate(4);
		
		String dayName="Thu";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}
	
	public String getFriday()
	{
		int date=getThisWeekDate(5);
		
		String dayName="Fri";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}

	public String getSaturday()
	{
		int date=getThisWeekDate(6);
		
		String dayName="Sat";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}

	public String getSunday()
	{
		int date=getThisWeekDate(0);
		
		String dayName="Sun";
		dayName=dayName+":";
		dayName=dayName+String.valueOf(date);
		return dayName;
	}

	public void addTimeSheet()
	{
		//timeEJB.addTimeSheet(wbsID, wbsTbl, userId, date, sun, mon, tue, wed, thu, fri, sat, total);
		timeEJB.addTimeSheet(weekDate,timeList);
		timeList.clear();		
	}
	
}
