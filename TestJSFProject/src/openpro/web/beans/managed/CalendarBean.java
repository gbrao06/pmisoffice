package openpro.web.beans.managed;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name = "CalendarWebBean")
@SessionScoped
public class CalendarBean {

	private Date date1;

	/**
	 * @return the date1
	 */
	public synchronized Date getDate1() {
		System.out.println("getDate entered:");
		return date1;
	}

	/**
	 * @param date1 the date1 to set
	 */
	public synchronized void setDate1(Date date1) {
		System.out.println("setDate entered:"+date1);
		this.date1 = date1;
	}  
    
    public void handleDateSelect()
    {
    	System.out.println("handleDateSelectEvent:");
    	Date date=new Date();
    }
}
