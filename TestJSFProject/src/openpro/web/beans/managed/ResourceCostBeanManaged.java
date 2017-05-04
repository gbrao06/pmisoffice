package openpro.web.beans.managed;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.TypedQuery;

import openpro.datamodel.entities.Resourcecosttbl;
import openpro.datamodel.entities.Tasktbl;
import openpro.datamodel.entities.Wbstbl;
import openpro.ejb.session.ResourceCostEJB;

@ManagedBean (name = "ResourceCostWebBean")
@SessionScoped
public class ResourceCostBeanManaged {

	//Resourcecosttbl is filled through gui.
	@EJB
	ResourceCostEJB resourceCostEJB;
	
	@ManagedProperty("#{WbsWebBean}")
	private WbsBeanManaged wbsBean;
	
	private AddResourceToProject addResource=new AddResourceToProject();
	
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
	 * @return the addResource
	 */
	public synchronized AddResourceToProject getAddResource() {
		return addResource;
	}

	/**
	 * @param addResource the addResource to set
	 */
	public synchronized void setAddResource(AddResourceToProject addResource) {
		this.addResource = addResource;
	}

	public class AddResourceToProject
	{
		BigInteger projectId;
		String userId;
		int billable;
		float costperhour;
		public void addResourceToProject()
		{
			resourceCostEJB.addResourceCostTbl(projectId, userId, (byte)billable, costperhour, (float)0, (float)0);
		}
		/**
		 * @return the billable
		 */
		public synchronized int getBillable() {
			return billable;
		}
		/**
		 * @param billable the billable to set
		 */
		public synchronized void setBillable(int billable) {
			this.billable = billable;
		}
		
		/**
		 * @return the projectId
		 */
		public synchronized BigInteger getProjectId() {
			return projectId;
		}
		/**
		 * @param projectId the projectId to set
		 */
		public synchronized void setProjectId(BigInteger projectId) {
			this.projectId = projectId;
		}
		/**
		 * @return the userId
		 */
		public synchronized String getUserId() {
			return userId;
		}
		/**
		 * @param userId the userId to set
		 */
		public synchronized void setUserId(String userId) {
			this.userId = userId;
		}
		
		/**
		 * @return the costperhour
		 */
		public synchronized float getCostperhour() {
			return costperhour;
		}
		/**
		 * @param costperhour the costperhour to set
		 */
		public synchronized void setCostperhour(float costperhour) {
			this.costperhour = costperhour;
		}
		
	}
	
	public List<ResourceCostWrapper> getProjectResourceCostStatus()
	{
		List<ResourceCostWrapper> rListWrappers=new ArrayList<ResourceCostWrapper>();
		List<Resourcecosttbl> rList=new ArrayList<Resourcecosttbl>();
		
		rList=resourceCostEJB.findAll();
		//project can have several 100s of wbs items
		//each user has lesser number of wbs items
		//since project_pk and user_pk are unique. 
		for(int i=0;i<rList.size();i++)
		{
			//take each user and calculate the wbs time sheets
			rListWrappers.add(new ResourceCostWrapper(rList.get(i)));
		}
		
		return rListWrappers;
	}
	
	//one resource and one project metrics
	public class ResourceCostWrapper
	{
		private Resourcecosttbl resourceCostTbl;
		List<WbsStatusWrapper> wbsWrapperList=new ArrayList<WbsStatusWrapper>();
		private float estimatedWorkHours=0;
		private float actualHoursWorked=0;
		private float estimatedCost=0;
		private float actualCost=0;
		public ResourceCostWrapper(Resourcecosttbl costTbl)
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
				
				//resourceCostEJB.updateResourceCostTbl(resourceCostTbl,resourceCostTbl.getBillable(),resourceCostTbl.getCostperhour(), estimatedWorkHours, actualHoursWorked);
				resourceCostEJB.addResourceCostTbl(resourceCostTbl.getProjecttbl().getProjectId(),resourceCostTbl.getUsertbl().getUserId(),resourceCostTbl.getBillable(),resourceCostTbl.getCostperhour(), estimatedWorkHours, actualHoursWorked);
			//}
		}
		
	}
}
