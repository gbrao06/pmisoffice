package openpro.ejb.session;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Tasktbl;


/**
 * Session Bean implementation class ProjectEJB
 */
@Stateless
@LocalBean
public class ProjectEJB {

	@PersistenceContext(unitName = "TestPU")
    EntityManager em;
    
    public List<Projecttbl> allProjectList = new ArrayList<Projecttbl>();
    
    /**
     * Default constructor. 
     */
    public ProjectEJB() {
    	
    }

    public List<Projecttbl> findAll() {
    		
    	allProjectList.clear();
    	TypedQuery<Projecttbl> query = em.createQuery("select c from Projecttbl c", Projecttbl.class);
    	allProjectList = query.getResultList();
    	return allProjectList;	
    }

    public List<Projecttbl> findAllByProjectId(BigInteger pid) {
    	
    	//Projecttbl table = (Projecttbl) em.find(Projecttbl.class,pid.toString());
    	
    	List<Projecttbl> pList= new ArrayList<Projecttbl>();
    	pList.clear();
    	
    	TypedQuery<Projecttbl> query = em.createQuery("select c from Projecttbl c where c.project_id=:project_id", Projecttbl.class);
    	query.setParameter("project_id",pid);
    	pList = query.getResultList();
    	
    	return pList;	
    }
    
    public Tasktbl addParentTask(BigInteger pid,String taskName,String group_type)
    {
    	Tasktbl task=new Tasktbl();
        task.setProjectId(pid);
        task.setGroupType(group_type);
        task.setTaskName(taskName);
        task.setParentTaskId(BigInteger.ZERO);
        task.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        return task;
    }
    
    public void addProject(BigInteger pid,String pname,String description,String createdBy) throws CreateException, SQLException
    {
    	
    	Projecttbl projectTable=new Projecttbl();
    	projectTable.setProjectId(pid);
    	projectTable.setProjectName(pname);	
    	projectTable.setProjectDescription(description);
    	projectTable.setCreatedBy(createdBy);
    	projectTable.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
    	
    	//em.getTransaction().begin();
        em.persist(projectTable);
        //create tasktable with parent tasks
        
        /*Tasktbl tbl0=addParentTask(pid,"Planning","development");
        em.persist(tbl0);  
        Tasktbl tbl1=addParentTask(pid,"Analysis","development");
        em.persist(tbl1);
        Tasktbl tbl2=addParentTask(pid,"Design","development");
        em.persist(tbl2);
        Tasktbl tbl3=addParentTask(pid,"Coding","development");
        em.persist(tbl3);
        Tasktbl tbl4=addParentTask(pid,"Debugging and Unit testing","development");
        em.persist(tbl4);
        */
        //em.getTransaction().commit();   
    }
    
    
}
