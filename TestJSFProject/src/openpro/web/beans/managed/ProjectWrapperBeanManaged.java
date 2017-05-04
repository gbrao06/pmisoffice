package openpro.web.beans.managed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Timesheettbl;
import openpro.datamodel.entities.Wbstbl;
import openpro.ejb.session.WbsEJB;

@ManagedBean (name = "ProjectWrapperWebBean")
@SessionScoped

public class ProjectWrapperBeanManaged
{
	
	@EJB
	WbsEJB wbsEJB;
	private Projecttbl projectTbl;
	
	private Date projectStartDate;
	private Date projectEndDate;
	private float totalEstimatedWorkHours;
	private float totalActualWorkHoursSpent;
	private float percentageWorkComplete;
	private float percentageComplete;
	private String projectStatus=null;
	
	private List<WbsStatusWrapper> wbsWrapperList=new ArrayList<WbsStatusWrapper>();
	
	public List<WbsStatusWrapper> getWbsStatusWrappers()
	{
		System.out.println("Entered:ProjectWrapperBean::getWbsStatusWrappers");
		if(projectTbl==null)
		{
			System.out.println("ProjectWrapperBean::getWbsStatusWrappers:projectTbl=null");
			return null;
		}
		wbsWrapperList.clear();	
		List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
		wbsList.clear();
		
		wbsList=wbsEJB.findAllByProjectID(projectTbl.getId());
		
		for(int i=0;i<wbsList.size();i++)
			wbsWrapperList.add(new WbsStatusWrapper(wbsList.get(i)));
		
		System.out.println("Exited:ProjectWrapperBean::getWbsStatusWrappers");
		return wbsWrapperList;
	}
	
			
	/**
	 * @return the projectStatus
	 */
	public synchronized String getProjectStatus() {
		return projectStatus;
	}

	/**
	 * @param projectStatus the projectStatus to set
	 */
	public synchronized void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public ProjectWrapperBeanManaged(Projecttbl pTable)
	{
		projectTbl=pTable;
		
		if(pTable!=null)
		{
			calcProjectStatus();	
		}
		
	}
	
	/**
	 * @return the percentageWorkComplete
	 */
	public synchronized float getPercentageWorkComplete() {
		return percentageWorkComplete;
	}

	/**
	 * @return the percentageComplete
	 */
	public synchronized float getPercentageComplete() {
		return percentageComplete;
	}

	/**
	 * @param percentageComplete the percentageComplete to set
	 */
	public synchronized void setPercentageComplete(float percentageComplete) {
		this.percentageComplete = percentageComplete;
	}

	/**
	 * @param percentageWorkComplete the percentageWorkComplete to set
	 */
	public synchronized void setPercentageWorkComplete(float percentageWorkComplete) {
		this.percentageWorkComplete = percentageWorkComplete;
	}

	/**
	 * @return the totalEstimatedWorkHours
	 */
	public synchronized float getTotalEstimatedWorkHours() {
		return totalEstimatedWorkHours;
	}

	/**
	 * @param totalEstimatedWorkHours the totalEstimatedWorkHours to set
	 */
	public synchronized void setTotalEstimatedWorkHours(
			float totalEstimatedWorkHours) {
		this.totalEstimatedWorkHours = totalEstimatedWorkHours;
	}

	/**
	 * @return the totalActualWorkHoursSpent
	 */
	public synchronized float getTotalActualWorkHoursSpent() {
		return totalActualWorkHoursSpent;
	}

	/**
	 * @param totalActualWorkHoursSpent the totalActualWorkHoursSpent to set
	 */
	public synchronized void setTotalActualWorkHoursSpent(
			float totalActualWorkHoursSpent) {
		this.totalActualWorkHoursSpent = totalActualWorkHoursSpent;
	}

	/**
	 * @return the projectStartDate
	 */
	public synchronized Date getProjectStartDate() {
		return projectStartDate;
	}

	/**
	 * @param projectStartDate the projectStartDate to set
	 */
	public synchronized void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	/**
	 * @return the projectEndDate
	 */
	public synchronized Date getProjectEndDate() {
		return projectEndDate;
	}

	/**
	 * @param projectEndDate the projectEndDate to set
	 */
	public synchronized void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	/**
	 * @return the projectTbl
	 */
	public synchronized Projecttbl getProjectTbl() {
		return projectTbl;
	}

	/**
	 * @param projectTbl the projectTbl to set
	 */
	public synchronized void setProjectTbl(Projecttbl projectTbl) {
		this.projectTbl = projectTbl;
	}

	
	
	private void calcProjectStatusStr()
	{
		if(percentageComplete<=0)
			projectStatus="Project Not Yet Started";
		if(percentageComplete>=100)
			projectStatus="Project Finished";
		
		if(percentageComplete>0 && percentageComplete<100)//in between
		{
			if(percentageWorkComplete<=0)
				projectStatus="Project Started But Work Yet To Start";
			else if (percentageWorkComplete>=99.9)
				projectStatus="Project Work Finished Earlier than Deadline";
			else
				projectStatus="Yellow:Active";
		}
				
	}
	
	private void calcProjectStatus()
	{
		System.out.println("Entered:ProjectStatusBean:calcProjetStatus");
		Calendar first = Calendar.getInstance();
		calculateProjectDates();
		calcPercentageWorkComplete();
		calcPercentComplete();
		calcProjectStatusStr();
		Calendar second = Calendar.getInstance();
		double timeTaken=second.getTimeInMillis()-first.getTimeInMillis();
		calcProjectStatusStr();
		System.out.println("Exited:ProjectStatusBean:calcProjetStatus:timeTaken in  milliSeconds:"+timeTaken);
	}
	
	private void calcPercentComplete()
	{		
		System.out.println("Entered:ProjectStatusBean:calcPercentComplete");
		if(projectTbl==null)
			return;
		//take wbs items and calculate time lists.
		
		float planneddays=0;
		percentageComplete=0;
		planneddays=UtilBean.calculateDifference(projectStartDate,projectEndDate);
		if(planneddays<=0)
			return;
		Calendar cal = Calendar.getInstance();
		Date today=cal.getTime();
		if (today.compareTo(projectStartDate) < 0)
		{
			return;
		}
		
		if (today.compareTo(projectEndDate) > 0)
		{
			percentageComplete=100;
			return;
		}
		
		float spentdays=UtilBean.calculateDifference(projectStartDate,today);
		
		if(spentdays==0 || planneddays==0)
		{
			return;
		}
		
		if(spentdays>planneddays)
		{
			percentageComplete=100;
			return;
		}
		percentageComplete=((spentdays*100)/planneddays);
		System.out.println("Exited:ProjectStatusBean:calcPercentComplete"+percentageComplete);
	}
	
	private void calcPercentageWorkComplete()
	{
		System.out.println("Entered:ProjectStatusBean:calcPercentageWorkComplete");
		if(projectTbl==null)
			return;
		//take wbs items and calculate time lists.
		List<Timesheettbl> timeList=new ArrayList<Timesheettbl>();
		timeList.clear();
		List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
		wbsList.clear();
		wbsList=projectTbl.getWbstbls();
		
		totalActualWorkHoursSpent=0;
		totalEstimatedWorkHours=0;
		
		for(int i=0;i<wbsList.size();i++)
		{
			totalActualWorkHoursSpent+=getActualHoursWorked(wbsList.get(i));
			totalEstimatedWorkHours+=getEstimatedHours(wbsList.get(i));
		}
		
		
		if(totalEstimatedWorkHours==0 || totalActualWorkHoursSpent==0)
		{
			percentageWorkComplete=0;		
		}
		percentageWorkComplete=(((totalActualWorkHoursSpent*100)/totalEstimatedWorkHours));
		System.out.println("Entered:ProjectStatusBean:calcPercentageWorkComplete:"+percentageWorkComplete);
	}
	
	
	private float getEstimatedHours(Wbstbl wbsItem)
	{
		
		System.out.println("Entered:ProjectStatusBean:getEstimatedHours:wbsId"+wbsItem.getWbsId());
		if(wbsItem==null)
			return 0;
		
		float total=0;
		total=UtilBean.calculateDifference(wbsItem.getStartEstimate(),wbsItem.getEndEstimate());
		
		total*=wbsItem.getEffortsPerDay();
		System.out.println("Exited:ProjectStatusBean:getEstimatedHours:"+total);
		return total;
	}
	
	private float getActualHoursWorked(Wbstbl wbsItem)
	{
		System.out.println("Enetered:ProjectStatusBean:getActualHoursWorked");
		if(wbsItem==null)
			return 0;
		float total=0;
		for(int i=0;i<wbsItem.getTimesheettbls().size();i++)
		{	
			System.out.println("Enetered:ProjectStatusBean:WbsId:"+wbsItem.getWbsId()+":weekTime:"+wbsItem.getTimesheettbls().get(i).getTotal());
			
			total+=wbsItem.getTimesheettbls().get(i).getTotal();
		}
		System.out.println("Exited:ProjectStatusBean:getActualHoursWorked:"+total);
		return total;
	}
		
	private void calculateProjectDates()
	{
		System.out.println("Enetered:ProjectStatusBean:calculateProjectDates");
		if(projectTbl==null)
			return;
		
		//take wbs items and calculate time lists.
		List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
		wbsList.clear();
		wbsList=projectTbl.getWbstbls();
		if(wbsList.size()<=0)
			return;
		
		projectStartDate=null;
		projectEndDate=null;
		for(int i=0;i<wbsList.size();i++)
		{
			if(wbsList.get(i).getStartEstimate()!=null)
			{
				if(projectStartDate==null)
				{
					projectStartDate=wbsList.get(i).getStartEstimate();
				}
				if(projectEndDate==null)
				{
					projectEndDate=wbsList.get(i).getEndEstimate();
				}
				if(wbsList.get(i).getStartEstimate().compareTo(projectStartDate)<0)
				{
					projectStartDate=wbsList.get(i).getStartEstimate();
				}
				if(wbsList.get(i).getEndEstimate().compareTo(projectEndDate)>0)
				{
					projectEndDate=wbsList.get(i).getEndEstimate();
				}
				
			}
		}
		
		System.out.println("Exited:ProjectStatusBean:calculateProjectDates:StartDate:"+projectStartDate+":endDate:"+projectEndDate);
	}

}
