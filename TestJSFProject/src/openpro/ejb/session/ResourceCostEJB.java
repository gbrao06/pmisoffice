package openpro.ejb.session;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import openpro.datamodel.entities.Resourcecosttbl;

/**
 * Session Bean implementation class ResourceCostEJB
 */
@Stateless
@LocalBean
public class ResourceCostEJB {

	@PersistenceContext(unitName = "TestPU")
    EntityManager em;
    
    /**
     * Default constructor. 
     */
    public ResourceCostEJB() {
        // TODO Auto-generated constructor stub
    }

public List<Resourcecosttbl> findAll() {
    	
		List<Resourcecosttbl> allResourceCostList= new ArrayList<Resourcecosttbl>();
		allResourceCostList.clear();
	    	TypedQuery<Resourcecosttbl> query = em.createQuery("select o from Resourcecosttbl o", Resourcecosttbl.class);
	    	allResourceCostList = query.getResultList();
	    	
	    	return allResourceCostList;
	    }    
    
public void addResourceCostTbl(BigInteger projectId,String userId, byte billable,float costperhour,float estHours,float actHours) {
	
	//check if exists.
	List<Resourcecosttbl> allResourceCostList= new ArrayList<Resourcecosttbl>();
	allResourceCostList.clear();
    	TypedQuery<Resourcecosttbl> query = em.createQuery("select o from Resourcecosttbl o", Resourcecosttbl.class);
    	allResourceCostList = query.getResultList();
    
	for(int i=0;i<allResourceCostList .size();i++)
	{
		if(allResourceCostList.get(i).getProjecttbl().getProjectId().equals(projectId) && allResourceCostList.get(i).getUsertbl().getUserId().equals(userId))	
		{
			//exist..just update
			updateResourceCostTbl(allResourceCostList.get(i),billable,costperhour,estHours,actHours);
			return;
		}
	}
	
	//else new record 
	
	Resourcecosttbl tbl=new Resourcecosttbl();
	
	tbl.setBillable(billable);
	tbl.setCostperhour(costperhour);
	tbl.setEstimatedWorkHrs(estHours);
	tbl.setActualHrsWorked(actHours);
	tbl.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));

	float estCost=costperhour*estHours;
	float actCost=costperhour*actHours;
	tbl.setActualCost(actCost);
	tbl.setEstimatedCost(estCost);
	em.persist(tbl);
}

public void updateResourceCostTbl(Resourcecosttbl tbl, byte billable,float costPerHour,float estimatedHours,float actualHours) {

	if(tbl==null){
		System.out.println("ResourceCostEJB::updateResourceCostTbl:tbl==null");
		return;
	}
	
	Resourcecosttbl tbl1=null;
	tbl1=em.find(Resourcecosttbl.class,tbl.getId());
	
	if(tbl1!=null)
	{		
		tbl.setBillable(billable);
		
		tbl1.setCostperhour(costPerHour);
		tbl1.setEstimatedWorkHrs(estimatedHours);
		tbl1.setActualHrsWorked(actualHours);
		float estCost=tbl1.getCostperhour()*estimatedHours;
		float actCost=tbl1.getCostperhour()*actualHours;
		tbl1.setEstimatedCost(estCost);
		tbl1.setActualCost(actCost);
		
		em.flush();
	}
	
}

	
}
