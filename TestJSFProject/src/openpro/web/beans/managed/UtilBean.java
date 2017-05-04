package openpro.web.beans.managed;

import java.util.Calendar;
import java.util.Date;

public class UtilBean {

	public static int calculateDifference(Date a, Date b)
	{
		System.out.println("entered:calculateDifference");
		
		if(a==null || b==null || !a.getClass().equals(Date.class) || !b.getClass().equals(Date.class))
			return 0;
	    int tempDifference = 0;
	    int difference = 0;
	    Calendar earlier = Calendar.getInstance();
	    Calendar later = Calendar.getInstance();

	    if (a.compareTo(b) < 0)
	    {
	        earlier.setTime(a);
	        later.setTime(b);
	    }
	    else
	    {
	        earlier.setTime(b);
	        later.setTime(a);
	    }

	    while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR))
	    {
	        tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
	        difference += tempDifference;

	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }

	    if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR))
	    {
	        tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
	        difference += tempDifference;
	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }
	    System.out.println("exited:calculateDifference");
	    return difference;
	}

}
