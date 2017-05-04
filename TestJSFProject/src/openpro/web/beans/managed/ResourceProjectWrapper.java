package openpro.web.beans.managed;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Resourcecosttbl;
import openpro.datamodel.entities.Wbstbl;
import openpro.ejb.session.ResourceCostEJB;

@ManagedBean (name = "ResourceProjectWebBean")
@SessionScoped
public class ResourceProjectWrapper {

	//for each resource in given selected project
	@EJB
	ResourceCostEJB resourceCostEJB;
	
	@ManagedProperty("#{WbsWebBean}")
	private WbsBeanManaged wbsBean;
	
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

	/**
	 * @return the resList
	 */
	public synchronized List<ResourceStatus> getResList() {
		return resList;
	}

	/**
	 * @param resList the resList to set
	 */
	public synchronized void setResList(List<ResourceStatus> resList) {
		this.resList = resList;
	}

	
	List<ResourceStatus> resList=new ArrayList<ResourceStatus>();
	public List<ResourceStatus> getResourceStatusOfTheProject(Projecttbl projectTbl)
	{
		resList.clear();
		
		if(projectTbl==null)
			return resList;
		
		for(int i=0;i<projectTbl.getResourcecosttbls().size();i++)
		{
			resList.add(new ResourceStatus(projectTbl.getResourcecosttbls().get(i)));
		}
		
		return resList;
	}
	
	public class ResourceStatus
	{
		private Resourcecosttbl resourceCostTbl;
		List<WbsStatusWrapper> wbsWrapperList=new ArrayList<WbsStatusWrapper>();
		private float estimatedWorkHours=0;
		private float actualHoursWorked=0;
		private float estimatedCost=0;
		private float actualCost=0;
		public ResourceStatus(Resourcecosttbl costTbl)
		{
			resourceCostTbl=costTbl;
			
			createWbsWrappers();
			updateResourceCosts();
		}
		
		public void createWbsWrappers()
		{
			if(resourceCostTbl==null)
				return;
			List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
			wbsList.clear();
			wbsWrapperList.clear();
			wbsList=wbsBean.get_user_wbs_by_project(resourceCostTbl.getProjecttbl().getProjectId(),resourceCostTbl.getUsertbl().getUserId());
			for(int i=0;i<wbsList.size();i++)
			{
				wbsWrapperList.add(new WbsStatusWrapper(wbsList.get(i)));
			}
		}
		
		public void updateResourceCosts()
		{
			if(resourceCostTbl==null)
				return;
			estimatedWorkHours=0;
			actualHoursWorked=0;
			estimatedCost=0;
			actualCost=0;
			for(int i=0;i<wbsWrapperList.size();i++)
			{
				estimatedWorkHours+=wbsWrapperList.get(i).getEstimatedWorkHours();
				actualHoursWorked+=wbsWrapperList.get(i).getActualWorkHours();
			}
			
			//if(resourceCostTbl.getBillable()!=0)//only if billable
			//{
				resourceCostTbl.setEstimatedWorkHrs(estimatedWorkHours);
				resourceCostTbl.setActualHrsWorked(actualHoursWorked);
				estimatedCost=resourceCostTbl.getCostperhour()*estimatedWorkHours;
				actualCost=resourceCostTbl.getCostperhour()*actualHoursWorked;
				
				resourceCostEJB.addResourceCostTbl(resourceCostTbl.getProjecttbl().getProjectId(),resourceCostTbl.getUsertbl().getUserId(),resourceCostTbl.getBillable(),resourceCostTbl.getCostperhour(), estimatedWorkHours, actualHoursWorked);
			//}
		}
		
	}
}

