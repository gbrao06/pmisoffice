/**
 * 
 */
package openpro.web.beans.managed;

/**
 * @author CRT
 *
 */
import javax.ejb.CreateException;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.SessionScoped;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Tasktbl;
import openpro.datamodel.entities.Wbstbl;

import openpro.ejb.session.WbsEJB;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
 

@ManagedBean (name = "WbsWebBean")
@SessionScoped

public class WbsBeanManaged {

	@EJB
	WbsEJB wbsEJB; 
		
	//@ManagedProperty("#{genreLabelListJSF}")	
	@ManagedProperty("#{TaskWebBean}")
	private TaskBeanManaged taskBean;
	
	@ManagedProperty("#{UserWebBean}")
	private UserBeanManaged userBean;
	
	//Tasktbl selectedTaskTbl=null;
					/**
		 * @return the task_name
		 */
		public synchronized String getTask_name() {
			return task_name;
		}

		/**
		 * @param task_name the task_name to set
		 */
		public synchronized void setTask_name(String task_name) {
			this.task_name = task_name;
		}
		/////selected items
		
		private BigInteger project_id=null;
		
		
		private String userId=null;
		private String parent_task_name=null;
		private Projecttbl projectTbl=null;
		private String task_name=null;
		private float effortsPerDay=0;
		private String wbs_id;
		//private String strStartEstimate=null;
		//private String strEndEstimate=null;
		//private String strStartActual=null;
		//private String strEndActual=null;
		
		private int priority=4;
		private String user_name=null;

		/**
		 * @return the wbs_id
		 */
		public synchronized String getWbs_id() {
			return wbs_id;
		}

		/**
		 * @param wbs_id the wbs_id to set
		 */
		public synchronized void setWbs_id(String wbs_id) {
			this.wbs_id = wbs_id;
		}

		/**
		 * @return the effortsPerDay
		 */
		public synchronized float getEffortsPerDay() {
			return effortsPerDay;
		}

		/**
		 * @param effortsPerDay the effortsPerDay to set
		 */
		public synchronized void setEffortsPerDay(float effortsPerDay) {
			this.effortsPerDay = effortsPerDay;
		}
		//{{ start Wrapper
		public class WbsTaskWrapper
		{
			private Wbstbl wbsTable=null;
			/**
			 * @return the wbsTable
			 */
			public synchronized Wbstbl getWbsTable() {
				return wbsTable;
			}


			/**
			 * @param wbsTable the wbsTable to set
			 */
			public synchronized void setWbsTable(Wbstbl wbsTable) {
				this.wbsTable = wbsTable;
			}


			public WbsTaskWrapper(Wbstbl tbl)
			{
				wbsTable=tbl;
			}
			
			public float getPercentComplete()
			{		
				float planneddays=0;
				planneddays=UtilBean.calculateDifference(wbsTable.getStartEstimate(),wbsTable.getEndEstimate());
				if(planneddays==0)
					return 0;
				Calendar cal = Calendar.getInstance();
				Date today=cal.getTime();
				if (today.compareTo(wbsTable.getStartEstimate()) < 0)
				{
					return 0;
				}
				if (today.compareTo(wbsTable.getEndEstimate()) > 0)
				{
					return 100;
				}
				float spentdays=UtilBean.calculateDifference(wbsTable.getStartEstimate(),today);
				
				if(spentdays==0 || planneddays==0)
				{
					return 0;
				}
				
				if(spentdays>planneddays)
					return 100;
				
				return ((spentdays*100)/planneddays);
			}
			private float getActualHoursWorked(Wbstbl wbsItem)
			{
				System.out.println("Enetered:ProjectStatusBean:getActualHoursWorked");
				if(wbsItem==null)
					return 0;
				float total=0;
				for(int i=0;i<wbsItem.getTimesheettbls().size();i++)
				{	
					System.out.println("Enetered:ProjectStatusBean:WbsId:"+wbsItem.getWbsId()+":weekTime:"+wbsItem.getTimesheettbls().get(i).getTotal());
					
					total+=wbsItem.getTimesheettbls().get(i).getTotal();
				}
				System.out.println("Exited:ProjectStatusBean:getActualHoursWorked:"+total);
				return total;
			}
			
			public float getPercentWorkComplete()
			{
				float planned=getPlannedEffort();
				float actual=getActualEffort();
				if(actual>planned)
					return 100;
				if(actual==0 || planned==0)
					return 0;
				
				return ((actual*100)/planned);
			}
			
			public float getPercentPhysicalComplete()
			{
				return 0;
			}
			
			public float getPlannedEffort()
			{
				float planneddays=UtilBean.calculateDifference(wbsTable.getStartEstimate(),wbsTable.getEndEstimate());
				return planneddays*wbsTable.getEffortsPerDay();
				
			}
			
			public float getActualEffort()
			{
				//float actualdays=calculateDifference(wbsTable.getStartActual(),wbsTable.getEndActual());
				return getActualHoursWorked(wbsTable);
			}
		}


		/**
		 * @return the startEstimate
		 */
		public synchronized Date getStartEstimate() {
			return startEstimate;
		}

		/**
		 * @param startEstimate the startEstimate to set
		 */
		public synchronized void setStartEstimate(Date startEstimate) {
			this.startEstimate = startEstimate;
		}

		/**
		 * @return the endEstimate
		 */
		public synchronized Date getEndEstimate() {
			return endEstimate;
		}

		/**
		 * @param endEstimate the endEstimate to set
		 */
		public synchronized void setEndEstimate(Date endEstimate) {
			this.endEstimate = endEstimate;
		}

		/**
		 * @return the startActual
		 */
		public synchronized Date getStartActual() {
			return startActual;
		}

		/**
		 * @param startActual the startActual to set
		 */
		public synchronized void setStartActual(Date startActual) {
			this.startActual = startActual;
		}

		/**
		 * @return the endActual
		 */
		public synchronized Date getEndActual() {
			return endActual;
		}

		/**
		 * @param endActual the endActual to set
		 */
		public synchronized void setEndActual(Date endActual) {
			this.endActual = endActual;
		}

		private Date startEstimate=null;
		private Date endEstimate=null;
		private Date startActual=null;
		private Date endActual=null;
		
		/**
		 * @return the user_name
		 */
		public synchronized String getUser_name() {
			return user_name;
		}

		/**
		 * @param user_name the user_name to set
		 */
		public synchronized void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		/**
		 * @return the priority
		 */
		public synchronized int getPriority() {
			return priority;
		}

		/**
		 * @param priority the priority to set
		 */
		public synchronized void setPriority(int priority) {
			this.priority = priority;
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
		public List<String> getTaskNamesByProjectId(BigInteger pid)
		{
			
			System.out.println("WbsBeanManaged::returning taskBean::getTaskNameByProjectId is");
			if(pid!=null)
			{
				project_id=pid;
				System.out.println("project_id="+pid.longValue());
			}
				
			return taskBean.getTaskNamesByProjectId(pid);	
		}
		
		/**
		 * @return the parent_task_name
		 */
		public synchronized String getParent_task_name() {
			return parent_task_name;
		}

		/**
		 * @param parent_task_name the parent_task_name to set
		 */
		public synchronized void setParent_task_name(String parent_task_name) {
			this.parent_task_name = parent_task_name;
		}

		//used only when parent is already created
		public String getParentOfSelectedTask(BigInteger proId,String taskName)
		{	
			System.out.println("Entered::WbsBeanManaged::getParentOfSelectedTask");
			parent_task_name="Parent Task Not Found";
			project_id=proId;
			task_name=taskName;
			Tasktbl selectedTaskTbl =null;
			selectedTaskTbl=getSelectedTasktbl(proId,taskName);
			if(selectedTaskTbl!=null)
			{				
				Tasktbl parent=null;
				parent=taskBean.findByPrimaryKeyID(selectedTaskTbl.getParentTaskId());
				if(parent!=null)
				{
					System.out.println("Exited::WbsBeanManaged::getParentOfSelectedTask:is"+parent.getTaskName());
					parent_task_name=parent.getTaskName();
				}
				update_estimates(selectedTaskTbl);		
			}
			
			System.out.println("Exited::WbsBeanManaged::getParentOfSelectedTask");
			return parent_task_name;
		}
		
		public Tasktbl getSelectedTasktbl(BigInteger proId,String taskName)
		{
			System.out.println("Entered::WbsBeanManaged::getSelectedTasktbl:");
			project_id=proId;
			task_name=taskName;
			List<Tasktbl> taskTblList=new ArrayList<Tasktbl>();
			taskTblList.clear();
			taskTblList=taskBean.findAllByProjectIdAndTaskName(project_id,task_name);
			if(taskTblList!=null)
			{
				if(taskTblList.size()==1)
				{
					System.out.println("WbsBeanManaged.getTaskListByProjectIdAndTaskName:returned  1 element->sending 0th: pro id="+taskTblList.get(0).getProjectId().longValue());
					System.out.println("Tasktbl Info:task_pk="+taskTblList.get(0).getId()+":taskname="+taskTblList.get(0).getTaskName());
					if(taskTblList.get(0).getParentTaskId()!=null)
						System.out.println("parenttaskid="+taskTblList.get(0).getParentTaskId().longValue());
				}
				else if(taskTblList.size()>1)
				{
					System.out.println("WbsBeanManaged.getTaskListByProjectIdAndTaskName:returned  more than 1 element->sending 0th:pro id="+taskTblList.get(0).getProjectId().longValue());
					System.out.println("Tasktbl Info:task_pk="+taskTblList.get(0).getId()+":taskname="+taskTblList.get(0).getTaskName());
					if(taskTblList.get(0).getParentTaskId()!=null)
						System.out.println("parenttaskid="+taskTblList.get(0).getParentTaskId().longValue());
				}
				else
				{
					System.out.println("Exit:TaskBean.getTaskListByProjectIdAndTaskName:returned less than 1 element->sending 0th");
					return null;
				}
				
				return taskTblList.get(0);
				
			}
			System.out.println("Exited::WbsBeanManaged::getSelectedTasktbl:returning null");
			return null;
		}
		//display purpose
	
	/**
		 * @return the wbsEJB
		 */
		public synchronized WbsEJB getWbsEJB() {
			return wbsEJB;
		}

		/**
		 * @param wbsEJB the wbsEJB to set
		 */
		public synchronized void setWbsEJB(WbsEJB wbsEJB) {
			this.wbsEJB = wbsEJB;
		}

		/**
		 * @return the taskBean
		 */
		public synchronized TaskBeanManaged getTaskBean() {
			return taskBean;
		}

		/**
		 * @param taskBean the taskBean to set
		 */
		public synchronized void setTaskBean(TaskBeanManaged taskBean) {
			this.taskBean = taskBean;
		}

		/**
		 * @return the project_id
		 */
		public synchronized BigInteger getProject_id() {
			return project_id;
		}

		/**
		 * @param project_id the project_id to set
		 */
		public synchronized void setProject_id(BigInteger project_id) {
			this.project_id = project_id;
		}
		/**
		 * @return the projectTbl
		 */
		public synchronized Projecttbl getProjectTbl() {
			return projectTbl;
		}

		/**
		 * @param projectTbl the projectTbl to set
		 */
		public synchronized void setProjectTbl(Projecttbl projectTbl) {
			this.projectTbl = projectTbl;
		}
		
		private Date Convert_To_Date(String str) {
			System.out.println("Entered:WbsBeanManaged::ConvertoTDate:str="+str);
	        Date date1 = null;

	        //String str = "Thu, 1 Mar 2012 13:57:06 -0600";
	        //DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
	        //dt=Dateformat.getDateInstance(DateFormat.MEDIUM).parse(str);
	        //SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssZ");
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        
	        try {
	            //dt = formatter.parse(str);
	        	Date dateStr = formatter.parse(str);
		        String formattedDate = formatter.format(dateStr);
		        System.out.println("yyyy-MM-dd date is ==>"+formattedDate);
		        date1 = formatter.parse(formattedDate);
		        
	        }
	        catch ( ParseException pe) {
	            System.out.println(pe.getMessage());
	        }
	        
	        //String strDt = dt.toString(); 
	        System.out.println(date1);
	        System.out.println("Exited:WbsBeanManaged::ConvertoTDate");
	        return date1;
	    }

		public Wbstbl addWbsTask()
		{
			System.out.println("Entered:WbsBeanManaged::addWbsTask");
			
			Projecttbl ptable=taskBean.getProjectByProjectID(project_id);	
			if(ptable==null)
			{
				System.out.println("Exit:WbsBeanManaged:addWbsTask::ptable=null");
				return null;
			}
		
			//Date startEstimate1=Convert_To_Date(strStartEstimate);
			//Date endEstimate1=Convert_To_Date(strEndEstimate);
			//Date startActual1=Convert_To_Date(strStartActual);
			//Date endActual1=Convert_To_Date(strEndActual);
			List<Tasktbl>taskList=new ArrayList<Tasktbl>();
			taskList.clear();
			taskList=taskBean.findAllByProjectIdAndTaskName(project_id, task_name);
			Tasktbl tasktbl=null;
			if(taskList!=null && taskList.size()>0)
			{
				tasktbl=taskList.get(0);
				if(tasktbl==null)
				{
					System.out.println("Exit:WbsBeanManaged:addWbsTask::tasktbl=null");
					return null;
				}
			}
			else
			{
				System.out.println("Exit:WbsBeanManaged:Empy TaskList:taskList.size()=0");
				return null;
			}
			
		try {
				System.out.println("projid="+ptable.getProjectId().longValue());
				System.out.println("taskname="+tasktbl.getTaskName());
				return wbsEJB.addWbsTask(wbs_id,tasktbl,priority, ptable, userBean.getCurrentUserTbl(), startEstimate, endEstimate, startActual, endActual, effortsPerDay);
			} catch (CreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
		return null;
		}
		
		public synchronized boolean updateTasks()
		{
			taskBean.updateTasks();
			return true;
		}
		
		public synchronized List<WbsTaskWrapper> get_user_wbs_wrapper_list()
		{
			user_name="gbrao06";//hack
			List<Wbstbl> list=new ArrayList<Wbstbl>();
			list.clear();
			
			list=wbsEJB.findAllByUserName(userBean.getCurrentUserID());
			List<WbsTaskWrapper>wrapperList=new ArrayList<WbsTaskWrapper>();
			wrapperList.clear();
			for(int i=0;i<list.size();i++)
			{
				wrapperList.add(new WbsTaskWrapper(list.get(i)));
			}
			
			return wrapperList;
		}

		public synchronized List<WbsTaskWrapper> get_user_wbs_wrapper_list_by_project(BigInteger projectId)
		{
			
			List<WbsTaskWrapper>wrapperList=new ArrayList<WbsTaskWrapper>();
			wrapperList.clear();
			if(projectId==null)
				return wrapperList;
			List<Wbstbl> list=new ArrayList<Wbstbl>();
			list.clear();
			
			list=wbsEJB.findAllByUserName(userBean.getCurrentUserID());
			
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getProjecttbl().getProjectId().equals(projectId))
				{
					wrapperList.add(new WbsTaskWrapper(list.get(i)));
					continue;
				}
			}
			
			return wrapperList;
		}

		public synchronized List<Wbstbl> get_user_wbs_list()
		{
			user_name="gbrao06";//hack
			return wbsEJB.findAllByUserName(userBean.getCurrentUserID());			
		}

		public synchronized List<Wbstbl> get_user_wbs_by_project(BigInteger proId,String userId)
		{

			List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
			wbsList.clear();
			wbsList=get_user_wbs_list();
			
			List<Wbstbl> userTaskList=new ArrayList<Wbstbl>();
			userTaskList.clear();
			for(int i=0;i<wbsList.size();i++)
			{
				Wbstbl wbsTable=null;
				wbsTable=wbsList.get(i);
				if(wbsTable!=null && wbsTable.getProjecttbl()!=null && wbsTable.getProjecttbl().getProjectId().equals(proId))
				{
					if(wbsTable.getUsertbl().getUserId()!=null && wbsTable.getUsertbl().getUserId().equals(userId))
					{
						userTaskList.add(wbsTable);	
					}
				}
			}
			
			return userTaskList;
		}

		public synchronized List<String> get_user_tasks_by_project(BigInteger proId,String userId)
		{

			List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
			wbsList.clear();
			wbsList=get_user_wbs_list();
			List<String> userTaskList=new ArrayList<String>();
			userTaskList.clear();
			for(int i=0;i<wbsList.size();i++)
			{
				Wbstbl wbsTable=null;
				wbsTable=wbsList.get(i);
				if(wbsTable!=null && wbsTable.getProjecttbl()!=null && wbsTable.getProjecttbl().getProjectId().equals(proId))
				{
					if(wbsTable.getUsertbl().getUserId()!=null && wbsTable.getUsertbl().getUserId().equals(userId))
					{
						userTaskList.add(wbsTable.getTasktbl().getTaskName());	
					}
				}
			}
			
			return userTaskList;
		}
		
		public synchronized Wbstbl get_user_wbs_by_project_and_task(BigInteger proId,String task)
		{
			List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
			wbsList.clear();
			wbsList=get_user_wbs_list();
			List<String> userTaskList=new ArrayList<String>();
			userTaskList.clear();
			for(int i=0;i<wbsList.size();i++)
			{
				Wbstbl wbsTable=null;
				wbsTable=wbsList.get(i);
				if(wbsTable!=null && wbsTable.getProjecttbl()!=null && wbsTable.getProjecttbl().getProjectId().equals(proId))
				{
					if(wbsTable.getTasktbl()!=null && wbsTable.getTasktbl().getTaskName().equals(task))
					{
						userTaskList.add(wbsTable.getTasktbl().getTaskName());
						
						return wbsTable;
					}
				}
			}
			
			return null;
		}
	
		public synchronized Wbstbl get_user_wbs_by_task_pk(Tasktbl selectedTaskTbl)
		{
			if(selectedTaskTbl==null)
				return null;
			
			List<Wbstbl> wbsList=new ArrayList<Wbstbl>();
			wbsList.clear();
			wbsList=get_user_wbs_list();
			for(int i=0;i<wbsList.size();i++)
			{
				Wbstbl wbsTable=null;
				wbsTable=wbsList.get(i);
				if(wbsTable!=null && wbsTable.getTasktbl()!=null && wbsTable.getTasktbl().getId().equals(selectedTaskTbl.getId()))
				{
					
					return wbsTable;

				}
			}
			
			return null;
		}
		

		public synchronized void update_estimates(Tasktbl selectedTaskTbl)
		{
			Wbstbl table=get_user_wbs_by_task_pk(selectedTaskTbl);
			startEstimate=null;
			endEstimate=null;
			startActual=null;
			endActual=null;
			if(table!=null)
			{
				priority=table.getPriority();
				if(table.getStartEstimate()!=null)
				{
					//strStartEstimate= table.getStartEstimate().toLocaleString();			
					startEstimate= table.getStartEstimate();
				}
				if(table.getEndEstimate()!=null)
				{
					//strEndEstimate=table.getEndEstimate().toLocaleString();
					endEstimate=table.getEndEstimate();
				}
				if(table.getStartActual()!=null)
				{
					//strStartActual=table.getStartActual().toLocaleString();
					startActual=table.getStartActual();
				}
				if(table.getEndActual()!=null)
				{
					//strEndActual=table.getEndActual().toLocaleString();
					endActual=table.getEndActual();
				}
				effortsPerDay=table.getEffortsPerDay();
				
				wbs_id=table.getWbsId();
			}
			else
				System.out.println("WbsBeanManaged:update_estimates:wbstableofproject and taskname is null:");
			
		}
		
		public synchronized void update_estimates(BigInteger proId,String task)
		{
			Wbstbl table=null;
			table=get_user_wbs_by_project_and_task(proId,task);
			startEstimate=null;
			endEstimate=null;
			startActual=null;
			endActual=null;
			if(table!=null)
			{
				
				if(table.getStartEstimate()!=null)
				{
					//strStartEstimate= table.getStartEstimate().toLocaleString();			
					startEstimate= table.getStartEstimate();
				}
				if(table.getEndEstimate()!=null)
				{
					//strEndEstimate=table.getEndEstimate().toLocaleString();
					endEstimate=table.getEndEstimate();
				}
				if(table.getStartActual()!=null)
				{
					//strStartActual=table.getStartActual().toLocaleString();
					startActual=table.getStartActual();
				}
				if(table.getEndActual()!=null)
				{
					//strEndActual=table.getEndActual().toLocaleString();
					endActual=table.getEndActual();
				}
					//strEndActual=table.getEndActual().toLocaleString();
				effortsPerDay=table.getEffortsPerDay();
					
				wbs_id=table.getWbsId();	
			}
			else
				System.out.println("WbsBeanManaged:update_estimates:wbstableofproject and taskname is null:");
			
		}
		public Date getThisWeek()
		{
			// get today and clear time of day
			System.out.println("enetered:testWeekDate");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.SECOND);
			cal.clear(Calendar.MILLISECOND);

			// get start of this week in milliseconds
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			System.out.println("Start of this week:       " + cal.getTime());
			System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
			
			// start of the next week
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			System.out.println("Start of the next week:   " + cal.getTime());
			System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
			System.out.println("exited:testDate");
			return cal.getTime();
		}
		
				/**
		 * @return the userBean
		 */
		public synchronized UserBeanManaged getUserBean() {
			return userBean;
		}

		/**
		 * @param userBean the userBean to set
		 */
		public synchronized void setUserBean(UserBeanManaged userBean) {
			this.userBean = userBean;
		}
		
		public synchronized void updateWbsTask(BigInteger proId,String task)
		{
			Wbstbl table=get_user_wbs_by_project_and_task(proId,task);
			if(table!=null)
			{
				//Date startEstimate1=Convert_To_Date(strStartEstimate);
				//Date endEstimate1=Convert_To_Date(strEndEstimate);
				//Date startActual1=Convert_To_Date(strStartActual);
				//Date endActual1=Convert_To_Date(strEndActual);
				
				//wbsEJB.updateWbsTask(table,project_id,task_name,userBean.getCurrentUserID(),priority,effortsPerDay,startEstimate,endEstimate,startActual,endActual);
			}
			
		}
		
		public void onSelectWbsRow(Wbstbl wbsTbl)
		{
			if(wbsTbl!=null)
			{
				System.out.println("WbsBeanManaged::onSelectWbsRow:wbsTbl.Id="+wbsTbl.getId());
				System.out.println("WbsBeanManaged::onSelectWbsRow:wbsTbl.taskname="+wbsTbl.getTasktbl().getTaskName());
			}
			else
			{
				System.out.println("WbsBeanManaged::onSelectWbsRow:wbsTbl=null");
			}
		}
		
		
}

