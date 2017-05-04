/**
 * 
 */
package openpro.web.beans.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;

/**
 * @author CRT
 *
 */

@ManagedBean (name = "dataCheckBoxWebBean")
@SessionScoped

public class DataTableCheckBoxWebBean {

	private HtmlDataTable dataTable;

	/**
	 * @return the dataTable
	 */
	public synchronized HtmlDataTable getDataTable() {
		return dataTable;
	}

	/**
	 * @param dataTable the dataTable to set
	 */
	public synchronized void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}
	
}
