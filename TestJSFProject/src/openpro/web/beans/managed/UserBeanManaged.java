package openpro.web.beans.managed;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import openpro.datamodel.entities.Usertbl;
import openpro.ejb.session.UserEJB;

@ManagedBean (name = "UserWebBean")
@SessionScoped
public class UserBeanManaged {

	@EJB
	UserEJB userEJB; 
	
	private List<Usertbl> userList=new ArrayList<Usertbl>();
	private List<String> userIDList=new ArrayList<String>();
	
	String user_id=null;//unique
	String pass_wd=null;
	String email=null;//unique
	String first_name=null;
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

	/**
	 * @return the user_id
	 */
	public synchronized String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public synchronized void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the pass_wd
	 */
	public synchronized String getPass_wd() {
		return pass_wd;
	}

	/**
	 * @param pass_wd the pass_wd to set
	 */
	public synchronized void setPass_wd(String pass_wd) {
		this.pass_wd = pass_wd;
	}

	/**
	 * @return the email
	 */
	public synchronized String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public synchronized void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the first_name
	 */
	public synchronized String getFirst_name() {
		return first_name;
	}

	/**
	 * @param first_name the first_name to set
	 */
	public synchronized void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public synchronized String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public synchronized void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the role
	 */
	public synchronized String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public synchronized void setRole(String role) {
		this.role = role;
	}

	String last_name=null;
	String role=null;
			
	/**
	 * @return the userEJB
	 */
	public synchronized UserEJB getUserEJB() {
		return userEJB;
	}

	/**
	 * @param userEJB the userEJB to set
	 */
	public synchronized void setUserEJB(UserEJB userEJB) {
		this.userEJB = userEJB;
	}

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
	
	public List<Usertbl> findAll()
	{
		userList.clear();
		userList=userEJB.findAll();	
		return userList;
	}
	
	public List<String> getAllUserIDs()
	{
		userIDList.clear();
		userIDList=userEJB.getAllUserIDs();
		return userIDList;
	}
	
	public void addUser() 
	{
		System.out.println("Entered:UserBeanManaged:addUser userID is::");
		getUserName();

		userEJB.addUser(user_id,pass_wd, email,first_name,last_name,role);
		System.out.println("Exited:UserBeanManaged:addUser userID is::");
	}
	
	public String getUserName()
	{
		if(email!=null)
		{
			String[] names = email.split("@");
			user_id=names[0];
			System.out.println("UserID is::"+user_id);
		
			return user_id;
		}
		
		return user_id;
	}
	
	public synchronized String getCurrentUserID()
	{
		return "gbrao06";
	}
	
	public synchronized Usertbl getCurrentUserTbl()
	{
		return userEJB.getUserTbl(getCurrentUserID());
	}
	
	public synchronized Usertbl getUserTblByUserID(String userId)
	{
		return userEJB.getUserTbl(userId);
	}
}
