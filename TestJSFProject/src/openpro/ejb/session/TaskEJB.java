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
import openpro.datamodel.entities.Wbstbl;


/**
 * Session Bean implementation class TaskEJB
 */
@Stateless
@LocalBean
public class TaskEJB {


    @PersistenceContext(unitName = "TestPU")
    EntityManager em;
    
    
    //public List<Tasktbl> allTaskList= new ArrayList<Tasktbl>();
   
public List<Tasktbl> findAll() {
    	
	List<Tasktbl> allTaskList= new ArrayList<Tasktbl>();
	allTaskList.clear();
    	TypedQuery<Tasktbl> query = em.createQuery("select o from Tasktbl o", Tasktbl.class);
    	allTaskList = query.getResultList();
    	
    	return allTaskList;
    }    
    
public List<String> findAllTaskNamesByProjectId(BigInteger project_id) {
    	
		System.out.println("Entered:TaskEJB::findAllTaskNamesByProjectId:Entered with projID=");
		if(project_id!=null)
			System.out.println(project_id.longValue());
    	List<String> pList= new ArrayList<String>();
    	pList.clear();
    	List<Tasktbl> allTaskList= new ArrayList<Tasktbl>();
    	allTaskList.clear();
    	allTaskList=findAll();
    	
    	if(allTaskList!=null && allTaskList.size()>0)
    	{
    		
    		for(int i=0;i<allTaskList.size();i++)
    		{
    			Tasktbl tbl=allTaskList.get(i);
    			if(tbl.getProjectId().equals(project_id))
    			{
    				//System.out.println("project_id=::"+tbl.getProjectId().longValue()+"task_name=::"+tbl.getTaskName());
    				pList.add(tbl.getTaskName());
    			}
    		}
    	}
    	System.out.println("Exited:TaskEJB::findAllTaskNamesByProjectId:found"+pList.size()+"Tasks for given project_id");
    	return pList;	
    }    
    
 public List<Tasktbl> findAllByProjectId(BigInteger pid) {
    	
	 System.out.println("Entered:TaskEJB::findAllByProjectId");
	 List<Tasktbl> allTaskList= new ArrayList<Tasktbl>();
	 	allTaskList.clear();
	 allTaskList=findAll();
	 	
	 List<Tasktbl> pList= new ArrayList<Tasktbl>();
	 
	 if(allTaskList!=null && allTaskList.size()>0)
 	 {
 		for(int i=0;i<allTaskList.size();i++)
 		{
 			Tasktbl tbl=allTaskList.get(i);
 			if(tbl.getProjectId().equals(pid))
 			{
 				pList.add(tbl);
 				//System.out.println("project_id=::"+tbl.getProjectId().longValue()+"task_name=::"+tbl.getTaskName());
 			}
 		}
 	}  
	 System.out.println("Exited:TaskEJB::findAllByProjectId");
	 return pList;
}    

 
 public List<Tasktbl> findAllByPrimaryKeyID(String pid) {
    
	 System.out.println("Entered:TaskEJB::findAllByPrimaryKey=:"+pid);
	 List<Tasktbl> pList= new ArrayList<Tasktbl>();	
	 pList.clear();
	      	
	 Tasktbl tbl=em.find(Tasktbl.class,pid);
	 if(tbl!=null)
		 pList.add(tbl);
	 
	 /*	
	  * List<Tasktbl> allTaskList= new ArrayList<Tasktbl>();
	 	allTaskList.clear();
	
    	if(allTaskList!=null && allTaskList.size()>0)
    	 {
    		for(int i=0;i<allTaskList.size();i++)
    		{
    			Tasktbl tbl=allTaskList.get(i);
    			
    			if(tbl.getId().equals(pid))
    			{
    				pList.add(tbl);
    				//System.out.println("found PK:source pk="+pid+"Match table id="+tbl.getId()+"match table project_id=::"+tbl.getId()+"Match table task_name=::"+tbl.getTaskName());
    			}
    			//else
    			//	System.out.println("Failed Table Key:is="+tbl.getId()+"source prime key is="+pid);
    		}
    	}
	 */
    	System.out.println("Exited:TaskEJB::findAllByPrimaryKey::pList.size()="+pList.size()+"prime key ="+pid);
    	return pList;	
    }

	public List<Tasktbl> findAllByProjectIdAndTaskName(BigInteger pid,String task) {
    	
		System.out.println("Entered:TaskEJB::findAllByProjectIdAndTaskName");
		List<Tasktbl> allTaskList= new ArrayList<Tasktbl>();
	 	allTaskList.clear();
	 	allTaskList=findAll();
	 
		
		if(pid!=null && task!=null)
		{
			System.out.println("input pid="+pid.longValue());
			System.out.println("input task_name="+task);
		}
		List<Tasktbl>taskListProjectTaskName=new ArrayList<Tasktbl>();
		 if(allTaskList!=null && allTaskList.size()>0)
	 	 {
	 		for(int i=0;i<allTaskList.size();i++)
	 		{
	 			Tasktbl tbl=allTaskList.get(i);
	 			
	 			if(tbl.getProjectId().equals(pid) && tbl.getTaskName().equals(task))
	 			{
	 				//System.out.println("tbl.project_id="+tbl.getProjectId().longValue());
		 			//System.out.println("tbl.task_name="+tbl.getTaskName());
		 			
	 				taskListProjectTaskName.add(tbl);
	 				System.out.println("project_id=::"+tbl.getProjectId().longValue()+"task_name=::"+tbl.getTaskName());
	 			}
	 		}
	 	}
		 System.out.println("Exited:TaskEJB::findAllByProjectIdAndTaskName");
		 return taskListProjectTaskName;	
    }

	public Tasktbl updateTask(Tasktbl task,String groupType,BigInteger parentTaskId) {
		// TODO Auto-generated method stub
		//long val=Long.parseLong(wbs.getId());
		if(task==null)
			return null;
		
		Tasktbl table = (Tasktbl) em.find(Tasktbl.class,task.getId());
		
		if(table!=null)
		{

			table.setGroupType(groupType);
			table.setParentTaskId(parentTaskId);
			table.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
	    	//em.getTransaction().begin();
	    	System.out.println("finished:TaskEJB:addTask:Before:flush");
	    	em.flush();
	    		
		}
		else
		{
			System.out.println("WbsEJB::updateWbsTask::table=null");
		}
		
		return table;
	}

	
	public Tasktbl addDirectWbsTask(String taskName,Projecttbl projecttbl,String groupType,Tasktbl parentTbl) throws CreateException, SQLException
    {
    	System.out.println("entered:TaskEJB:addTask");
    	BigInteger parentTaskId=null;
    	
    	if(projecttbl==null || taskName==null || taskName.equalsIgnoreCase(""))
    	{
    		System.out.println("Exited::TaskEJB::AddTask:projecttbl=null or taskname empty");
    		return null;	
    	}
    	
    	if(parentTbl!=null)
    	{
    		try
    		{
    			long val=Long.parseLong(parentTbl.getId());
    			parentTaskId=BigInteger.valueOf(val);
    		}
    		catch(Exception e)
    		{
    			System.out.println("TaskEJB::addTask::Exception in Conversion from id to long");
    	    	return null;
    		}
    	}
    	
    	//check if already present i.e., duplicate
    	List<Tasktbl>taskListProjectTaskName=new ArrayList<Tasktbl>();
    	taskListProjectTaskName.clear();
    	
    	taskListProjectTaskName=findAllByProjectIdAndTaskName(projecttbl.getProjectId(),taskName);
    	
    	System.out.println("size of taskListProjectTaskName is:"+taskListProjectTaskName.size());
    	Tasktbl task=null;
    	
    	if(taskListProjectTaskName.size()>0)
    	{
    		//update if exists
    		task=taskListProjectTaskName.get(0);
    		return updateTask(task,groupType,parentTaskId);
    		
    	}
    	 
		task=new Tasktbl();
		task.setTaskName(taskName);	
			
    	task.setProjectId(projecttbl.getProjectId());		        	
	
    	
    	
    	task.setGroupType(groupType);
    	task.setParentTaskId(parentTaskId);
    	task.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
    	//em.getTransaction().begin();
    	System.out.println("finished:TaskEJB:addTask:Before:persist");
    	em.persist(task);
    	
        System.out.println("finished:TaskEJB:addTask:IdCreated is:"+task.getId());	
        return task;
    }
	
	//Use when already parent tasks exist
    public void addTask(String taskName,Projecttbl projecttbl,String groupType,String parentTaskName) throws CreateException, SQLException
    {
    	System.out.println("entered:TaskEJB:addTask");
    	BigInteger parentTaskId=null;
    	
    	
    	if(projecttbl==null)
    	{
    		System.out.println(taskName+"::"+groupType);
    		if(parentTaskId==null)
    			System.out.println("TaskEJB::addTask::parentTaskId==null");
    		else
    			System.out.println(taskName+"::"+groupType+"::"+parentTaskId.longValue());
    		System.out.println("Exited::TaskEJB::AddTask");
    		return;	
    	}
    	else
    	{
    		System.out.println("project_id="+projecttbl.getProjectId().longValue());
    		System.out.println("parentTaskName="+parentTaskName);
    	}
    	List<Tasktbl>taskListProjectTaskName=new ArrayList<Tasktbl>();
    	taskListProjectTaskName=findAllByProjectIdAndTaskName(projecttbl.getProjectId(),parentTaskName);
    	System.out.println("size of taskListProjectTaskName is"+taskListProjectTaskName.size());
    	if(taskListProjectTaskName.size()<=0){
    		System.out.println("Less than One record exist with project-id and task_name");
    	return;	
    	}
    	
    	String str=taskListProjectTaskName.get(0).getId();
    	System.out.println(str);
    	if(str!=null)
    	{
    		try
    		{
    			long val=Long.parseLong(str);
    			parentTaskId=BigInteger.valueOf(val);
    		}
    		catch(Exception e)
    		{
    			System.out.println("TaskEJB::addTask::Exception in Conversion from id to long");
    	    	
    		}
    	}
    	Tasktbl task=new Tasktbl();
    	//task.setProjecttbl(list.get(0));
    	if(projecttbl!=null)
    	{
    		//projecttbl.getTasktbls().add(task);
    		
    		task.setProjectId(projecttbl.getProjectId());
    		
    	}
    	
    	task.setGroupType(groupType);
    	task.setTaskName(taskName);	
    	task.setParentTaskId(parentTaskId);
    	task.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
    	//em.getTransaction().begin();
    	System.out.println("finished:TaskEJB:addTask:Before:persist");
    	em.persist(task);
    	
        //em.getTransaction().commit();
        System.out.println("finished:TaskEJB:addTask");	
   
    }
    
    /**
     * Default constructor. 
     */
    public TaskEJB() {
        // TODO Auto-generated constructor stub
    	System.out.println("Entered:TaskEJB:constructor");
    }

    /* Query :sort
     query = entityManager.createQuery("select o from MyTestObject o where o.income = :income and o.age = :age order by o.age");
		query.setParameter("income", 50507.0);
		query.setParameter("age", 12);
		List<MyTestObject> obs = query.getResultList();
     */
    /* Query: Like
    query = em.createQuery("select o from MyTestObject3 o where o.someField3 like :x");
    query.setParameter("x", "fred and%"); 
    List<MyTestObject3> obs = query.getResultList();
    */
    /* Equalent to     
     query = entityManager.createQuery("select o from MyTestObject o where o.anotherObject.id = :anotherObjectId");
		query.setParameter("anotherObjectId", anotherObject.getId());
		List<MyTestObject> obs = query.getResultList();
     */
     
    /*
     Query with referenced object filter (@ManyToOne)	
	query = entityManager.createQuery("select o from MyTestObject o where o.anotherObject = :anotherObject");
	query.setParameter("anotherObject", anotherObject);
	List<MyTestObject> obs = query.getResultList();
     */
     /*
      Basic query		
		query = entityManager.createQuery("select o from MyTestObject o where o.income = :income and o.age = :age");
		query.setParameter("income", 50507.0);
		query.setParameter("age", 12);
		List<MyTestObject> obs = query.getResultList();
      */
}
