package openpro.web.beans.managed;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import openpro.datamodel.entities.Usertbl;
import openpro.ejb.session.UserTimeEntryEJB;

@ManagedBean (name = "UserTimeEntryWebBean")
@SessionScoped

public class UserTimeEntryBeanManaged {

	@EJB
	UserTimeEntryEJB userTimeEntryEJB; 
	
	
	public boolean isTimeSheetFound(String userId,Date date)
	{
		 return userTimeEntryEJB.isTimeEntryFound(userId, date);
	}
	
	public boolean updateTimeEntryEditFlag(String userName,Date date, boolean flag)
	{
		return userTimeEntryEJB.updateTimeEntry(userName, date,flag);				
	}
	
	
}
