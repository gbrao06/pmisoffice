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


import openpro.datamodel.entities.Projecttbl;
import openpro.ejb.session.ProjectEJB;

/*
 jsf should use javax.faces.bean.SessionScoped; instead of  
 */

@ManagedBean (name = "ProjectWebBean")
@SessionScoped
public class ProjectBeanManaged {


	@ManagedProperty("#{UserWebBean}")
	private UserBeanManaged userBean;
	
	/**
	 * @return the project_description
	 */
	public synchronized String getProject_description() {
		return project_description;
	}
	/**
	 * @param project_description the project_description to set
	 */
	public synchronized void setProject_description(String project_description) {
		this.project_description = project_description;
	}

	@EJB
	ProjectEJB projectEJB; 

	
	private long project_id;
	
	private String project_description;
	
	private String project_name;
	
	
	
	private List<Projecttbl> projectList = new ArrayList<Projecttbl>();
	
	public List<Projecttbl> getProjectList() {
		projectList.clear();
		projectList=projectEJB.findAll();
		return projectList;
	}
	
	public List<BigInteger> getProjectListBigInts() {
		System.out.println("Entered:ProjectWebManaged::getProjectListBigInts");
		getProjectList();
		List<BigInteger> bigList=new ArrayList<BigInteger>();
		bigList.clear();
		for(int i=0;i<projectList.size();i++)
		{
			bigList.add(projectList.get(i).getProjectId());
		}
		
		return bigList;
	}
	
	
	public Projecttbl getProjectByProjectID(long id) {
		System.out.println("entered:ProjectBeanManaged:getProjectById");
		
		try
		{
			getProjectList();
			if(projectList.size()<=0)
				System.out.println("projectList.size:0");
			
			for(int i=0;i<getProjectList().size();i++)
			{
				Projecttbl tbl=projectList.get(i);
				if(tbl==null)
				{
					System.out.println("Exited:ProjectWebBeanManaged->getProjectById ptable in projectList=null");
					return null;
				}
				else
					System.out.println("Exited:ProjectWebBeanManaged->getProjectById ptable in projectList not null");
				
				if(tbl!=null && tbl.getProjectId().longValue()==id)
				{
					System.out.println("exited:ProjectBeanManaged:getProjectById:returned valid tbl");
					return tbl;
				}
				
			}
		}
		catch(Exception e)
		{
			System.out.println("exited:ProjectBeanManaged:getProjectById:Exception");
			System.out.println(e.getStackTrace());
		}
		System.out.println("exited:ProjectBeanManaged:getProjectById:returned NULL");
		return null;
		
	}
	
	public void setProjectList(List<Projecttbl> projects)
	{
		this.projectList=projects;
	}
	
	public void addProject() 
	{
		try
		{
			System.out.println("entered:ProjectBeanManaged:addProject:project_name:"+project_name+";escription:"+project_description);
			projectEJB.addProject(BigInteger.valueOf(project_id), project_name,project_description,userBean.getCurrentUserID());
		}
		catch(CreateException ce)
		{
			
		}
		catch(SQLException se)
		{
			
		}
		finally
		{
			project_name=null;
		}
		
	
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
	/**
	 * @return the project_id
	 */
	public synchronized long getProject_id() {
		return project_id;
	}

	/**
	 * @param project_id the project_id to set
	 */
	public synchronized void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	/**
	 * @return the project_name
	 */
	public synchronized String getProject_name() {
		return project_name;
	}

	/**
	 * @param project_name the project_name to set
	 */
	public synchronized void setProject_name(String project_name) {
		this.project_name = project_name;
	}


}
