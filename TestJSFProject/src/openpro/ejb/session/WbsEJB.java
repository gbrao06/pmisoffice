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
import openpro.datamodel.entities.Usertbl;
import openpro.datamodel.entities.Wbstbl;

/**
 * Session Bean implementation class WbsEJB
 */
@Stateless
@LocalBean
public class WbsEJB {

	@PersistenceContext(unitName = "TestPU")
    EntityManager em;
    
    /**
     * Default constructor. 
     */
    public WbsEJB() {
        // TODO Auto-generated constructor stub
    }
    
    //List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
	public List<Wbstbl> findAllByUserName(String userName) {
	    	
			List<Wbstbl> allWbsList= new ArrayList<Wbstbl>();
			allWbsList.clear();

			if(userName==null || userName.equalsIgnoreCase(""))
				return allWbsList;
			
			TypedQuery<Wbstbl> query = em.createQuery("select o from Wbstbl o", Wbstbl.class);
		    for(int i=0;i<query.getResultList().size();i++)
	    	{
	    		if(query.getResultList().get(i).getUsertbl().getUserId().equals(userName))
	    			allWbsList.add(query.getResultList().get(i));	
	    			
	    	}
		    return allWbsList;
	    }

	//String means PK
	public List<Wbstbl> findAllByProjectID(String projectId) {
    	
		List<Wbstbl> allWbsList= new ArrayList<Wbstbl>();
		allWbsList.clear();
		
		if(projectId==null || projectId.equalsIgnoreCase(""))
			return allWbsList;
		
		TypedQuery<Wbstbl> query = em.createQuery("select o from Wbstbl o", Wbstbl.class);
	    for(int i=0;i<query.getResultList().size();i++)
    	{
    		if(query.getResultList().get(i).getProjecttbl().getId().equals(projectId))
    			allWbsList.add(query.getResultList().get(i));	
    			
    	}
	    return allWbsList;
    }
  
    public Wbstbl addWbsTask(String wbsId,Tasktbl taskTbl,int priority,Projecttbl projectTbl,Usertbl userTbl,
    		Date startEstimate,Date endEstimate,Date startActual,Date endActual,float effortsperday) throws CreateException, SQLException
    {
 
    	System.out.println("Entered:WbsEJB:addWbsTask");
    	if(projectTbl==null || taskTbl==null || userTbl==null)
    	{
    			System.out.println("projecttbl==null || taskTbl==null || userTbl==null executed");
    			return null;
    	}
    	
    	//check for duplicates
    	//check for tasktbl's wbsList and see wheather its projecttbl and user_name are same
    	Wbstbl wbs=null;
    	System.out.println("Entered:WbsEJB:addWbsTask:Checking For Duplicates:userId:"+userTbl.getUserId()+";task_pk:"+taskTbl.getId()+";projectId"+projectTbl.getProjectId()+"wbsId:"+wbsId);
    	List<Wbstbl> taskList=new ArrayList<Wbstbl>();
    	taskList.clear();
    	taskList=findAllByUserName(userTbl.getUserId());
    	for(int i=0;i<taskList.size();i++)
    	{
    		System.out.println("projectTbl.getWbstbls:i="+i+";projectId:"+projectTbl.getProjectId()+"UserId:"+userTbl.getUserId());
        	
    		if(taskList.get(i).getProjecttbl().getId().equals(projectTbl.getId()))
    		{
    			//check for user_name. each task can have n wbs where n = 0 to number of assigned to
    			//check for task pk
    			if(taskList.get(i).getTasktbl().getId().equals(taskTbl.getId()))//user ias already aasigned with this task
    			{
    				//if same duplicate exist;
    				wbs=taskTbl.getWbstbls().get(i);
    				System.out.println("Duplicate Found:OnlyUpdate:i="+i+";wbsId:"+wbs.getWbsId());
    	        		
    				return updateWbsTask(wbs,projectTbl.getProjectId(),priority,  effortsperday,startEstimate, endEstimate,startActual, endActual,wbsId);
    				
    			}
    			
    		}
    	}
    	
    		System.out.println("Entered:WbsEJB:addWbsTask:Duplicate Not Found");
    	
    		wbs=new Wbstbl();
    	
	    	if(projectTbl!=null)
	    	{
	    		//projecttbl.getWbstbls().add(task);
	    		wbs.setProjecttbl(projectTbl);
	    		projectTbl.getWbstbls().add(wbs);
	    		
	    	}
	    	else
	    		System.out.println("projectTbl==null");
	    	
	    	if(taskTbl!=null)
	    	{
	    		wbs.setTasktbl(taskTbl);
	    		taskTbl.getWbstbls().add(wbs);
	    		System.out.println("tasktbl.getWbstbls.size="+taskTbl.getWbstbls().size());
	    	}
	    	else
	    		System.out.println("taskTbl==null");
	    	
	    	if(userTbl!=null)
	    		wbs.setUsertbl(userTbl);
	    	else
	    		System.out.println("userTbl==null");
	    	
    	wbs.setPriority(priority);
    	wbs.setStartEstimate(startEstimate);
    	wbs.setEndEstimate(endEstimate);
    	
    	wbs.setStartActual(startActual);
    	wbs.setEndActual(endActual);
    	
    	wbs.setEffortsPerDay( effortsperday);
    	System.out.println("WbsEJB:wbsId:"+wbsId);
    	wbs.setWbsId(wbsId);
    	
    	wbs.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
    	System.out.println("finished:WbsEJB:addWbsTask:Before:persist");   	
        
    	//em.getTransaction().begin();
    	em.persist(wbs);
    	   		
        //em.getTransaction().commit();
        System.out.println("finished:WbsEJB:addTask");
        return wbs;
    }

	public Wbstbl updateWbsTask(Wbstbl wbs,BigInteger project_id, int priority, float effortsperday, Date startEstimate1, Date endEstimate1,
			Date startActual1, Date endActual1,String wbsId) {
		// TODO Auto-generated method stub
		//long val=Long.parseLong(wbs.getId());
		Wbstbl table = (Wbstbl) em.find(Wbstbl.class,wbs.getId());
		
		if(table!=null)
		{

	    	//if(currentUserID!=null)
	    		//wbs.setUserId(currentUserID);
	    	
			table.setPriority(priority);
			table.setStartEstimate(startEstimate1);
			table.setEndEstimate(endEstimate1);
	    	
			table.setStartActual(startActual1);
			table.setEndActual(endActual1);
	    	
			table.setEffortsPerDay( effortsperday);
			//table.setHoursActual(hoursActual);
			table.setWbsId(wbsId);
			table.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
	       //	em.persist(wbs);
			System.out.println("WbsEJB::updateWbsTask:Before Flush");
	    	em.flush();
	       	
		}
		else
		{
			System.out.println("WbsEJB::updateWbsTask::table=null");
		}
		
		return table;
	}
    
}
