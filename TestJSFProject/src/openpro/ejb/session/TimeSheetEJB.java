package openpro.ejb.session;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Tasktbl;
import openpro.datamodel.entities.Timesheettbl;
import openpro.datamodel.entities.Usertbl;
import openpro.datamodel.entities.Wbstbl;
import openpro.web.beans.managed.TimeSheetBeanManaged.TimeSheetWbsWrapper;

/**
 * Session Bean implementation class TimeSheetEJB
 */
@Stateless
@LocalBean
public class TimeSheetEJB {


    @PersistenceContext(unitName = "TestPU")
    EntityManager em;
    /**
     * Default constructor. 
     */
    public TimeSheetEJB() {
        // TODO Auto-generated constructor stub
    }

    public List<Timesheettbl> findAllByWbsPK(String wbs_pk) {
    	
		List<Timesheettbl> alltimeList= new ArrayList<Timesheettbl>();
		
		alltimeList.clear();

		if(wbs_pk==null || wbs_pk.equalsIgnoreCase(""))
			return alltimeList;
		
		TypedQuery<Timesheettbl> query = em.createQuery("select o from Timesheettbl o", Timesheettbl.class);
	    for(int i=0;i<query.getResultList().size();i++)
    	{
    		if(query.getResultList().get(i).getWbstbl().getId().equals(wbs_pk))
    			alltimeList.add(query.getResultList().get(i));	
    			
    	}
	    return alltimeList;
    }


    public List<Timesheettbl> findAllByWeekDate(Date weekDate) {
    	
		List<Timesheettbl> alltimeList= new ArrayList<Timesheettbl>();
		alltimeList.clear();
		if(weekDate==null)
			return alltimeList;
		
		TypedQuery<Timesheettbl> query = em.createQuery("select o from Timesheettbl o", Timesheettbl.class);
	    for(int i=0;i<query.getResultList().size();i++)
    	{
    		if(query.getResultList().get(i).getWeekDate().equals(weekDate))
    			alltimeList.add(query.getResultList().get(i));	
    			
    	}
	    return alltimeList;
    }

private void updateTimeSheet(Timesheettbl timeTbl,float sun,float mon,float tue,float wed,float thu,float fri,float sat)
{
	System.out.println("Entered:TimeSheetEJB:updateTimeSheet");
	Timesheettbl timeTable = (Timesheettbl) em.find(Timesheettbl.class,timeTbl.getId());
	if(timeTable!=null)
	{
		timeTable.setMon(mon);
		timeTable.setTue(tue);
		timeTable.setWed(wed);
		timeTable.setThu(thu);
		timeTable.setFri(fri);
		timeTable.setSat(sat);
		timeTable.setSun(sun);
		float total=sun+mon+tue+wed+thu+fri+sat;
		timeTable.setTotal(total);
		timeTable.setLastUpdated((new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));
		em.flush();
	}
	System.out.println("Exited:TimeSheetEJB:updateTimeSheet");
}

private void addTimeSheet(Wbstbl wbsTbl,Date weekDate,float sun,float mon,
		float tue,float wed,float thu,float fri,float sat) throws CreateException, SQLException
{

	System.out.println("Entered:TimeSheetEJB:addTimeSheet");
	if(wbsTbl==null)
	{
			System.out.println("wbsTbl==null");
			return;
	}
	
	//check for valid weekDate
	if(weekDate==null || !weekDate.getClass().equals(Date.class))
	{
		System.out.println("weekDate invaid");
		return;
	}
	//if total=0 then don't need to enter into system.
	float total=sun+mon+tue+wed+thu+fri+sat;
	if(total<=0)
	{
		System.out.println("0 week Hours for:wbsId:"+wbsTbl.getWbsId());
		return;
	}
	
	//first update if found
	//user_id, project_id, wbsId and weekDate. already wbsTbl exist
	
	//task.setProjecttbl(list.get(0));
	//single wbsID task can be allocated to more than one developer, but wbsTbls itself has project_pk,task_pk_user_pk has unique constraint
	//means each wbstbl itself is an unique record
	//check wheather it has particular date timesheet or not.
	for(int i=0;i<wbsTbl.getTimesheettbls().size();i++)
	{
		Timesheettbl timeTbl=wbsTbl.getTimesheettbls().get(i);
		if(timeTbl.getWeekDate().equals(weekDate))//timesheet present
		{			
			 System.out.println("Record exist:update and return: wbs:"+wbsTbl.getWbsId()+"week:"+weekDate+"user:"+wbsTbl.getUsertbl().getUserId());
			 updateTimeSheet(timeTbl,sun,mon,tue,wed,thu,fri,sat);
			 return;		
		}
	}
	
	Timesheettbl time=new Timesheettbl();
	//set foreign tbls to time.
	if(wbsTbl!=null)
	{
		time.setWbstbl(wbsTbl);
		wbsTbl.getTimesheettbls().add(time);
		System.out.println("wbsTbl.getTimesheettbls.size="+wbsTbl.getTimesheettbls().size());
	}
	else
		System.out.println("wbsTbl==null");

	time.setWeekDate(weekDate);
	time.setMon(mon);
	time.setTue(tue);
	
	time.setWed(wed);
	time.setThu(thu);
	time.setFri(fri);
	time.setSat(sat);
	time.setSun(sun);
	time.setTotal(total);
	time.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
	//em.getTransaction().begin();
	System.out.println("Finished:TimeSheetEJB:addTimeSheet:Before:persist");   	
	em.persist(time);

    //em.getTransaction().commit();
    System.out.println("finished:TimeSheetEJB:addTimeSheet");	
}

public void addTimeSheet(Date weekDate,
		List<TimeSheetWbsWrapper> timeList) {
	// TODO Auto-generated method stub
	
	if(weekDate==null || timeList==null || timeList.size()<=0)
	{
		if(weekDate==null)
			System.out.println("TimeSheetEJB:addTimeSheet:weekDate==null");
		
		System.out.println("TimeSheetEJB:addTimeSheet:weekDate==null || currentUserID==null:timeList.size()="+timeList.size());
		return;
	}
	
	for(int i=0;i<timeList.size();i++)
	{
		try {
			addTimeSheet(timeList.get(i).getWbsTable(),weekDate, timeList.get(i).getSundayTime(),timeList.get(i).getMondayTime(),timeList.get(i).getTuesdayTime(),timeList.get(i).getWednsdayTime(),timeList.get(i).getThursdayTime(),timeList.get(i).getFridayTime(),timeList.get(i).getSaturdayTime());
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


}
