package openpro.web.beans.managed;

import java.util.Calendar;
import java.util.Date;

import openpro.datamodel.entities.Wbstbl;

public class WbsStatusWrapper
{
	private Wbstbl wbsTbl;
	private float estimatedWorkHours;
	private float actualWorkHours;
	
	private float percentComplete;
	private float percentWorkComplete;
	/**
	 * @return the wbsTbl
	 */
	public synchronized Wbstbl getWbsTbl() {
		return wbsTbl;
	}


	/**
	 * @param wbsTbl the wbsTbl to set
	 */
	public synchronized void setWbsTbl(Wbstbl wbsTbl) {
		this.wbsTbl = wbsTbl;
	}


	/**
	 * @return the estimatedWorkHours
	 */
	public synchronized float getEstimatedWorkHours() {
		return estimatedWorkHours;
	}


	/**
	 * @param estimatedWorkHours the estimatedWorkHours to set
	 */
	public synchronized void setEstimatedWorkHours(float estimatedWorkHours) {
		this.estimatedWorkHours = estimatedWorkHours;
	}


	/**
	 * @return the actualWorkHours
	 */
	public synchronized float getActualWorkHours() {
		return actualWorkHours;
	}


	/**
	 * @param actualWorkHours the actualWorkHours to set
	 */
	public synchronized void setActualWorkHours(float actualWorkHours) {
		this.actualWorkHours = actualWorkHours;
	}


	/**
	 * @return the percentComplete
	 */
	public synchronized float getPercentComplete() {
		return percentComplete;
	}


	/**
	 * @param percentComplete the percentComplete to set
	 */
	public synchronized void setPercentComplete(float percentComplete) {
		this.percentComplete = percentComplete;
	}


	/**
	 * @return the percentWorkComplete
	 */
	public synchronized float getPercentWorkComplete() {
		return percentWorkComplete;
	}


	/**
	 * @param percentWorkComplete the percentWorkComplete to set
	 */
	public synchronized void setPercentWorkComplete(float percentWorkComplete) {
		this.percentWorkComplete = percentWorkComplete;
	}


	public WbsStatusWrapper(Wbstbl item)
	{
		wbsTbl=item;
		if(wbsTbl!=null)
		{
			estimatedWorkHours=getEstimatedHours(wbsTbl);
			actualWorkHours=getActualHoursWorked(wbsTbl);

			if(estimatedWorkHours==0 || actualWorkHours==0)
			{
				percentWorkComplete=0;		
			}
			else if(actualWorkHours>estimatedWorkHours)
				percentWorkComplete=100;
			else
				percentWorkComplete=(((actualWorkHours*100)/estimatedWorkHours));
			
			calcPercentComplete();
		}
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
	
	private void calcPercentComplete()
	{		
		System.out.println("Entered:WbsStatusWrapper:calcPercentComplete");
		//take wbs items and calculate time lists.
		
		float planneddays=0;
		percentComplete=0;
		planneddays=UtilBean.calculateDifference(wbsTbl.getStartEstimate(),wbsTbl.getEndEstimate());
		if(planneddays<=0)
			return;
		Calendar cal = Calendar.getInstance();
		Date today=cal.getTime();
		if (today.compareTo(wbsTbl.getStartEstimate()) < 0)
		{
			return;
		}
		
		if (today.compareTo(wbsTbl.getEndEstimate()) > 0)
		{
			percentComplete=100;
			return;
		}
		
		float spentdays=UtilBean.calculateDifference(wbsTbl.getStartEstimate(),today);
		
		if(spentdays==0 || planneddays==0)
		{
			return;
		}
		
		if(spentdays>planneddays)
		{
			percentComplete=100;
			return;
		}
		percentComplete=((spentdays*100)/planneddays);
		System.out.println("Exited:WbsStatusWrapper:calcPercentComplete"+percentComplete);
	}	
}

