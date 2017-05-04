package openpro.web.beans.managed;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.TypedQuery;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Tasktbl;

import openpro.ejb.session.TaskEJB;

@ManagedBean (name = "TaskWebBean")
@SessionScoped
/*
 * use setHasMultipleTableConstraintDependecy DescriptorCustomizer  for sql error
 * EclipseLink, version: Eclipse Persistence Services - 2.3.0.v20110604-r9504
 * use 2.3.0.v20110604-r9504
 * 
 */
public class TaskBeanManaged {


	@EJB
	TaskEJB taskEJB; 
	//@ManagedProperty("#{genreLabelListJSF}")	
	@ManagedProperty("#{ProjectWebBean}")
	private ProjectBeanManaged projectBean;
	
	private String task_name;
	private String parent_task_name;
	//private List<String>taskListNames=new ArrayList<String>();
	
	/**
	 * @return the parent_task_name
	 */
	public synchronized String getParent_task_name() {
		return parent_task_name;
	}

	/**
	 * @param parent_task_name the parent_task_name to set
	 */
	public synchronized void setParent_task_name(String parent_task_name) {
		this.parent_task_name = parent_task_name;
	}

	//private Projecttbl projectEJB=null;
	private BigInteger project_id=null;
	/**
	 * @return the project_id
	 */
	public synchronized BigInteger getProject_id() {
		return project_id;
	}
	
	/**
	 * @return the taskEJB
	 */
	public synchronized TaskEJB getTaskEJB() {
		return taskEJB;
	}

	/**
	 * @param taskEJB the taskEJB to set
	 */
	public synchronized void setTaskEJB(TaskEJB taskEJB) {
		this.taskEJB = taskEJB;
	}
	/**
	 * @param project_id the project_id to set
	 */
	public synchronized void setProject_id(BigInteger project_id) {
		this.project_id = project_id;
	
	}
	
	private String group_type;
	private long parent_task_id;
	
	

	 public List<Tasktbl> findAllByProjectId(BigInteger pid) {
		 System.out.println("Enetered:TaskBeanManaged::fndAllByprojectId");
		 List<Tasktbl>taskListProject=new ArrayList<Tasktbl>();
		 taskListProject.clear();
		 project_id=pid;
		 taskListProject=taskEJB.findAllByProjectId(pid);
		 System.out.println("Exited:TaskBeanManaged::fndAllByprojectId");
		 return taskListProject;	
	    }    

	/**
	 * @return the taskListNames
	 */
//	public synchronized List<String> getTaskListNames() {
	//	return taskListNames;
	//}

	/**
	 * @param taskListNames the taskListNames to set
	 */
//	public synchronized void setTaskListNames(List<String> taskListNames) {
	//	this.taskListNames = taskListNames;
//	}

	public List<String> getTaskNamesByProjectId(BigInteger pid)
	{
		List<String>taskListNames=new ArrayList<String>();
		taskListNames.clear();
		project_id=pid;
		taskListNames=taskEJB.findAllTaskNamesByProjectId(pid);
		return taskListNames;
	}
	
	public synchronized void updateTasks()
	{
		System.out.println("Entered::TaskBeanManaged::updateTasks::selected project_id=");
		if(project_id!=null)
			System.out.println(project_id.longValue());
		getTaskNamesByProjectId(project_id);
	}
	
	public List<Tasktbl> findAllByProjectIdAndTaskName(BigInteger projectId,String task){
		System.out.println("Entered::TaskBeanManaged::findAllByProjectIdAndTaskName:");
		project_id=projectId;
		task_name=task;
		if(project_id==null || task_name==null || task_name.equalsIgnoreCase(""))
		{	
			System.out.println("project_id==null || task_name==null || task_name.equalsIgnoreCase");	
				return null;
		}
		else
		{
			System.out.println("Selected project_id="+projectId.longValue());
			System.out.println("Selected taskName is="+task);
		}
		
		
		System.out.println("Exited::WbsBeanManaged::findAllByProjectIdAndTaskName:");
		
		return taskEJB.findAllByProjectIdAndTaskName(projectId,task);
	}
	
	public Tasktbl findByPrimaryKeyID(BigInteger projectId){
		
		List<Tasktbl> tblList=new ArrayList<Tasktbl>();
		if(projectId!=null)
			tblList=taskEJB.findAllByPrimaryKeyID(projectId.toString());
		else
		{
			System.out.println("Exited::TaskBeanManaged::findByprimaryKey::project_id=null");
			return null;
		}
		if(tblList!=null && tblList.size()>0)
			return tblList.get(0);
		
		return null;
	}
	
	/**
	 * @return the task_name
	 */
	public synchronized String getTask_name() {
		return task_name;
	}
	/**
	 * @param task_name the task_name to set
	 */
	public synchronized void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	/**
	 * @return the project_id
	 */
/*	public synchronized ProjectEJB getProjectTable() {
		return projectEJB;
	}
*/
	
	/**
	 * @param project_id the project_id to set
	 */
	public synchronized void setProjectTable(BigInteger projId) {
		System.out.println("entered:TaskWebBean:setProjectTable");
		if(projId==null)
			System.out.println("TaskWebBean:projectId:NULL");
		else
		{
			System.out.println("TaskWebBean:setProjectTable:projectId ="+projId.longValue());
		}
		project_id=projId;
		/*if(project_ejb==null)
			System.out.println("TaskWebBean:setProjectTable:NULL");
		else{
			System.out.println("d"+project_ejb.getProjectId().longValue());
			System.out.println("Project_id from project table is ::");
			if(project_ejb.getProjectId()==null)
				System.out.println("project_ejb.getProjectId()=null");
			else
			{
				
				System.out.println("d"+project_ejb.getProjectId().longValue());
			}
		}
		this.projectEJB = project_ejb;
		*/
		//System.out.println(ptable.getProjectId().longValue());
		System.out.println("exited:TaskWebBean:setProjectTable");
	}
	
	/**
	 * @return the group_type
	 */
	public synchronized String getGroup_type() {
		return group_type;
	}
	/**
	 * @param group_type the group_type to set
	 */
	public synchronized void setGroup_type(String group_type) {
		this.group_type = group_type;
	}
	/**
	 * @return the parent_task_id
	 */
	public synchronized long getParent_task_id() {
		return parent_task_id;
	}
	/**
	 * @param parent_task_id the parent_task_id to set
	 */
	public synchronized void setParent_task_id(long parent_task_id) {
		this.parent_task_id = parent_task_id;
	}
	
	
	public synchronized BigInteger get_project_id()
	{
			return project_id;
	}
	
	 public Projecttbl getProjectByProjectID(BigInteger proId)
	{
		if(projectBean==null)
			System.out.println("TaskBeanManaged::getProjectById::projectWebBean=NULL");
			
		else
			return projectBean.getProjectByProjectID(proId.longValue());
			
		return null;
	}
	
	 public synchronized Tasktbl addDirectWbsTask(String task,Projecttbl ptable,String group,Tasktbl parentTbl) 
	{
			System.out.println("entered:TaskWebBean:addTask:");
			
			if( task==null || task.equalsIgnoreCase(""))
			{
				System.out.println("Exited:taskname :NULL");
				return null;
			}
			
			
			if(ptable==null)
			{
				System.out.println("Exited:TaskWebBean:addTask:ptable=NULL:exit");
				return null;
			}
					try
					{
						System.out.println("taskName::="+task+"::group_type::="+group);
						//get parent_task_id from parent_task_name and project_id
						
						return taskEJB.addDirectWbsTask(task,ptable,group,parentTbl);
						
					}
					 catch (CreateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("exception:TaskWebBean:addTask:InstantiationException");
							System.out.println(e.getStackTrace());
							return null;
						}
					
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("exception:TaskWebBean:addTask:IllegalAccessException");
						System.out.println(e.getStackTrace());
						return null;
					}
				
			}
			
		
	public synchronized void addTask(String task) 
	{
		System.out.println("entered:TaskWebBean:addTask:");
		task_name=task;
		if(task==null || task.equalsIgnoreCase(""))
		{
			System.out.println("Exited:taskname:NULL");
			return;
		}
		
		
		validate();
		Projecttbl ptable=getProjectByProjectID(project_id);
		if(project_id==null)
			System.out.println("entered:TaskWebBean:addTask:project_id=NULL:exit");
		else
			System.out.println(project_id.longValue());
		
		/*if(projectEJB==null || projectEJB.projectList.size()<=0)
			System.out.println("entered:TaskWebBean:addTask:ptable=NULL:exit");
		
		for(int i=0;i<projectEJB.projectList.size();i++)
		{
			ptable=projectEJB.projectList.get(i);
			if(ptable!=null && ptable.getProjectId()!=null)
			{
				if(ptable.getProjectId().longValue()==pid)
				{
					System.out.println("entered:TaskWebBean:addTask");
					System.out.println("entered:TaskWebBean:addTask:project_id!=0");
					//System.out.println(ptable.getProjectId().longValue());
				}
			}
				
		}	
		*/
		if(ptable==null)
		{
			System.out.println("exited:TaskWebBean:addTask:ptable=NULL:exit");
			return;
		}
				try
				{
					System.out.println("entered:TaskWebBean:addTask:==0");
					if(ptable==null)
					{
						System.out.println("entered:TaskWebBean:addTask:ptable==NULL");
						return;
					}
					System.out.println("taskName::="+task_name+"::group_type::="+group_type+"::parent_task_id::="+parent_task_id);
					//get parent_task_id from parent_task_name and project_id
					
					taskEJB.addTask(task_name,ptable,group_type,parent_task_name);
					
				}
				 catch (CreateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("exception:TaskWebBean:addTask:InstantiationException");
						System.out.println(e.getStackTrace());
					}
				
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("exception:TaskWebBean:addTask:IllegalAccessException");
					System.out.println(e.getStackTrace());
				}
			System.out.println("exited:TaskWebBean:addTask");
			
		}
		
	
	public synchronized void  validate()
	{
		System.out.println("entered:TaskWebBean:validate");
		if(project_id!=null)
			System.out.println("project_id="+project_id.longValue());
		else
			System.out.println("project_id=null");
		
	}
	/**
	 * @return the projectBean
	 */
	public synchronized ProjectBeanManaged getProjectBean() {
		return projectBean;
	}
	/**
	 * @param projectBean the projectBean to set
	 */
	public synchronized void setProjectBean(ProjectBeanManaged projectBean) {
		this.projectBean = projectBean;
	}
	
	
}
