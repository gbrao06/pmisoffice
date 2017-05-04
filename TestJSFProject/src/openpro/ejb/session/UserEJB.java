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

import openpro.datamodel.entities.Tasktbl;
import openpro.datamodel.entities.Usertbl;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB {

    /**
     * Default constructor. 
     */
    public UserEJB() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceContext(unitName = "TestPU")
    EntityManager em;
    
    private List<Usertbl> userList=new ArrayList<Usertbl>();
    private List<String> userIDList=new ArrayList<String>();
    
    /**
	 * @return the userList
	 */
	public synchronized List<Usertbl> getUserList() {
		return userList;
	}
	/**
	 * @param userList the userList to set
	 */
	public synchronized void setUserList(List<Usertbl> userList) {
		this.userList = userList;
	}
	public List<Usertbl> findAll() {
    	
		userList.clear();
    	TypedQuery<Usertbl> query = em.createQuery("select c from Usertbl c", Usertbl.class);
    	userList = query.getResultList();
    	return userList;	
    }

	public List<String> getAllUserIDs()
	{
		findAll();
		for(int i=0;i<userList.size();i++)
		{
			userIDList.add(userList.get(i).getUserId());
		}
		
		return userIDList;
	}
	/**
	 * @return the userIDList
	 */
	public synchronized List<String> getUserIDList() {
		return userIDList;
	}
	/**
	 * @param userIDList the userIDList to set
	 */
	public synchronized void setUserIDList(List<String> userIDList) {
		this.userIDList = userIDList;
	}
	
	
	
	public boolean isDuplicate(String user_id,String email)
	{
		findAll();
		for(int i=0;i<userList.size();i++)
		{
			if(userList.get(i).getEmail().equals(email) || userList.get(i).getUserId().equals(user_id))
				return true;
		}
		
		return false;
	}
	
	public Usertbl getUserTbl(String userId)
	{
		findAll();
		for(int i=0;i<userList.size();i++)
		{
			if(userList.get(i).getUserId().equals(userId))
				return userList.get(i);
		}
		
		return null;
	}
	
	public boolean updatePasswdByUserPK(String upk,String passwd)
	{
		Usertbl tbl=null;
		tbl=em.find(Usertbl.class,upk);
		if(tbl!=null)
		{
			tbl.setPasswd(passwd);
			return true;
		}
		
		return false;
	}
	public boolean updatePasswd(String user_id,String passwd)
	{
		findAll();
		for(int i=0;i<userList.size();i++)
		{
			if(userList.get(i).getUserId().equals(user_id))
			{
				return updatePasswdByUserPK(userList.get(i).getId(),passwd);
			}
				
		}
		
		return false;
	}
	
	public synchronized void addUser(String user_id,String passwd,String email,String first_name,String last_name,String role)
	{
		System.out.println("Entered:UserEJB::addUser:user_id is=::"+user_id);
		if(user_id==null || user_id.equalsIgnoreCase("") || email==null || email.equalsIgnoreCase(""))
		{
			System.out.println("Exit:UserEJB::user_id==null || user_id.equalsIgnoreCase || email==null || email.equalsIgnoreCase");
			return;
		}
		
		if(isDuplicate(user_id,email))
			return;
		
		Usertbl tbl=new Usertbl();
		tbl.setUserId(user_id);
		tbl.setEmail(email);
		tbl.setFirstName(first_name);
		tbl.setLastName(last_name);
		tbl.setPasswd(passwd);
		tbl.setRole(role);
		tbl.setLastUpdated(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		em.persist(tbl);
		System.out.println("Exited:UserEJB::addUser");
	}
}

