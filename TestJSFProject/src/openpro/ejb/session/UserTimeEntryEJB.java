package openpro.ejb.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import openpro.datamodel.entities.Timesheettbl;
import openpro.datamodel.entities.Usertbl;
import openpro.datamodel.entities.Usertimeentrytbl;

/**
 * Session Bean implementation class UserTimeEntryEJB
 */
@Stateless
@LocalBean
public class UserTimeEntryEJB {


    @PersistenceContext(unitName = "TestPU")
    EntityManager em;
    
    /**
     * Default constructor. 
     */
    public UserTimeEntryEJB() {
        // TODO Auto-generated constructor stub
    }


public boolean isTimeEntryFound(String userName,Date weekDate)
{

	if(userName==null || userName.equalsIgnoreCase("") || weekDate==null)
		return false;
	TypedQuery<Usertimeentrytbl> query = em.createQuery("select o from Usertimeentrytbl o", Usertimeentrytbl.class);
    for(int i=0;i<query.getResultList().size();i++)
	{
		if(query.getResultList().get(i).getUsertbl().getUserId().equals(userName) && query.getResultList().get(i).getWeekdate().equals(weekDate))
		{
			return true;
		}
	}

    return false;

}

public boolean updateTimeEntry(String userName,Date weekDate, boolean editFlag)
{
	System.out.println("Entered:Usertimeentrtytbl::updateTimeEntryByUserTbl And weekDate");
	if(userName==null || userName.equalsIgnoreCase("") || weekDate==null)
		return false;
	
	Usertimeentrytbl timeEntryTbl =null;
	boolean found=isTimeEntryFound(userName,weekDate);
	if(found==false)
		return false;
	
	Usertimeentrytbl timeTbl =null;
	timeTbl=em.find(Usertimeentrytbl.class,timeEntryTbl.getId());
	
	if(timeTbl!=null)
	{
		byte tr=1;
		byte fl=0;
		if(editFlag)
			timeTbl.setCanedit(tr);
		else
			timeTbl.setCanedit(fl);
		
		em.flush();
		return true;
	}
	System.out.println("Exited:Usertimeentrtytbl::updateTimeEntry");
	return false;    
}

public boolean getUpdateTimeEntryFlag(Usertbl userTbl,Date weekDate)
{

	if(userTbl==null || weekDate==null)
		return false;
	TypedQuery<Usertimeentrytbl> query = em.createQuery("select o from Usertimeentrytbl o", Usertimeentrytbl.class);
    for(int i=0;i<query.getResultList().size();i++)
	{
		if(query.getResultList().get(i).getUsertbl().getId().equals(userTbl.getId()) && query.getResultList().get(i).getWeekdate().equals(weekDate))
		{
			byte bt= query.getResultList().get(i).getCanedit();
			
			if(bt==0)
				return false;
			else
				return true;
		}
	}

    return false;

}

public Usertimeentrytbl updateTimeEntry(Usertimeentrytbl timeEntry, boolean editFlag)
{
System.out.println("Entered:Usertimeentrtytbl::updateTimeEntry");
	if(timeEntry==null)
		return null;
	Usertimeentrytbl timeEntryTbl = em.find(Usertimeentrytbl.class,timeEntry.getId());
	
	if(timeEntryTbl!=null)
	{
		byte tr=1;
		byte fl=0;
		if(editFlag)
			timeEntryTbl.setCanedit(tr);
		else
			timeEntryTbl.setCanedit(fl);
		
		em.flush();
	}
	System.out.println("Exited:Usertimeentrtytbl::updateTimeEntry");
	return timeEntryTbl;    
}

}
