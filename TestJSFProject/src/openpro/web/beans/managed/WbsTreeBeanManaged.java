package openpro.web.beans.managed;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import openpro.datamodel.entities.Projecttbl;
import openpro.datamodel.entities.Tasktbl;
import openpro.datamodel.entities.Wbstbl;
import openpro.ejb.session.TaskEJB;
import openpro.ejb.session.WbsEJB;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;



@ManagedBean (name = "WbsTreeWebBean")
@SessionScoped
public class WbsTreeBeanManaged {

	private TreeNode root;  
	private TreeNode selectedNode;    
	BigInteger project_id;
	
	@EJB
	WbsEJB wbsEJB;

	@ManagedProperty("#{UserWebBean}")
	private UserBeanManaged userBean;
	
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
	 * @return the root
	 */
	public synchronized TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public synchronized void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * @return the selectedNode
	 */
	public synchronized TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * @param selectedNode the selectedNode to set
	 */
	public synchronized void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	@ManagedProperty("#{WbsWebBean}")
	private WbsBeanManaged wbsBean;

	@ManagedProperty("#{TaskWebBean}")
	private TaskBeanManaged taskBean;

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


	public WbsTreeBeanManaged()
	{
		createTree();
	}
	
	public void updateWbsTree()
	{
		System.out.println("ProjectId is:");
		if(project_id!=null)
			System.out.println(project_id.toString());
	}
			
	public void createTree()
	{
		
		root = new DefaultTreeNode("root", null);  
		//addChild(root);
		//TreeNode documents = new DefaultTreeNode(new WbsDocument("Documents", "-", "Folder"), root);   
        //TreeNode pictures = new DefaultTreeNode(new WbsDocument("Pictures", "-", "Folder"), root);  
        //TreeNode music = new DefaultTreeNode(new WbsDocument("Music", "-", "Folder"), root);  
          
        /*TreeNode work = new DefaultTreeNode(new WbsDocument("Work", "-", "Folder"), documents);  
        TreeNode primefaces = new DefaultTreeNode(new WbsDocument("PrimeFaces", "-", "Folder"), documents);  
          
        //Documents  
        TreeNode expenses = new DefaultTreeNode("document", new WbsDocument("Expenses.doc", "30 KB", "Word Document"), work);  
        TreeNode resume = new DefaultTreeNode("document", new WbsDocument("Resume.doc", "10 KB", "Word Document"), work);  
        TreeNode refdoc = new DefaultTreeNode("document", new WbsDocument("RefDoc.pages", "40 KB", "Pages Document"), primefaces);  
          
        //Pictures  
        TreeNode barca = new DefaultTreeNode("picture", new WbsDocument("barcelona.jpg", "30 KB", "JPEG Image"), pictures);  
        TreeNode primelogo = new DefaultTreeNode("picture", new WbsDocument("logo.jpg", "45 KB", "JPEG Image"), pictures);  
        TreeNode optimus = new DefaultTreeNode("picture", new WbsDocument("optimusprime.png", "96 KB", "PNG Image"), pictures);  
          
        //Music  
        TreeNode turkish = new DefaultTreeNode(new WbsDocument("Turkish", "-", "Folder"), music);  
          
        TreeNode cemKaraca = new DefaultTreeNode(new WbsDocument("Cem Karaca", "-", "Folder"), turkish);  
        TreeNode erkinKoray = new DefaultTreeNode(new WbsDocument("Erkin Koray", "-", "Folder"), turkish);  
        TreeNode mogollar = new DefaultTreeNode(new WbsDocument("Mogollar", "-", "Folder"), turkish);  
          
        TreeNode nemalacak = new DefaultTreeNode("mp3", new WbsDocument("Nem Alacak Felek Benim", "1500 KB", "Audio File"), cemKaraca);  
        TreeNode resimdeki = new DefaultTreeNode("mp3", new WbsDocument("Resimdeki Gozyaslari", "2400 KB", "Audio File"), cemKaraca);  
          
        TreeNode copculer = new DefaultTreeNode("mp3", new WbsDocument("Copculer", "2351 KB", "Audio File"), erkinKoray);  
        TreeNode oylebirgecer = new DefaultTreeNode("mp3", new WbsDocument("Oyle bir Gecer", "1794 KB", "Audio File"), erkinKoray);  
          
        TreeNode toprakana = new DefaultTreeNode("mp3", new WbsDocument("Toprak Ana", "1536 KB", "Audio File"), mogollar);  
        TreeNode bisiyapmali = new DefaultTreeNode("mp3", new WbsDocument("Bisi Yapmali", "2730 KB", "Audio File"), mogollar);  
        */
        }


	public class WbsDocument
	{		 
		private BigInteger project_id;
		private String task_name;
		private String user_name;
		private Date startEstimate;
		private Date endEstimate;
		private Date actualStart;
		private Date actualEnd;
		
		
		private float effortsPerDay;
		private float hoursEstimated;	
		private String wbs_id;
		private Tasktbl taskTable;
		private int priority=4;	
		private Projecttbl projectTable;
		private Wbstbl wbsTable;
		
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



		/**
		 * @return the projectTable
		 */
		public synchronized Projecttbl getProjectTable() {
			return projectTable;
		}



		/**
		 * @param projectTable the projectTable to set
		 */
		public synchronized void setProjectTable(Projecttbl projectTable) {
			this.projectTable = projectTable;
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
		 * @return the taskTable
		 */
		public synchronized Tasktbl getTaskTable() {
			return taskTable;
		}



		/**
		 * @param taskTable the taskTable to set
		 */
		public synchronized void setTaskTable(Tasktbl taskTable) {
			this.taskTable = taskTable;
		}



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



		/**
		 * @return the hoursEstimated
		 */
		public synchronized float getHoursEstimated() {
			return hoursEstimated;
		}



		/**
		 * @param hoursEstimated the hoursEstimated to set
		 */
		public synchronized void setHoursEstimated(float hoursEstimated) {
			this.hoursEstimated = hoursEstimated;
		}



		public WbsDocument(String wbsId,BigInteger projectId, String  taskName,String  userName,Date pStart,Date pEnd,Date aStart,Date aEnd,float effortsPerDay)
		{
			
			this.project_id=projectId;
			if(this.project_id!=null)
				System.out.println("WbsDocument::Constructor:project_id:"+this.project_id.toString());
			
			this.task_name=taskName;
			this.user_name=userName;
			this.startEstimate=pStart;
			this.endEstimate=pEnd;
			this.actualStart=aStart;
			this.actualEnd=aEnd;
			this.effortsPerDay=effortsPerDay;
			this.wbs_id=wbsId;
			
		}
	
	public void updateWbsDocument()
	{
		//Calendar cal1=Calendar.getInstance();
		//cal1.setTime(this.startEstimate);
		//DateFormat format=DateFormat.getTimeInstance(DateFormat.MEDIUM);
		//String str=format.format(this.startEstimate);
		//this.startEstimate=format.
		calculateHoursEstimated();	
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
	 * @return the actuaStart
	 */
	public synchronized Date getActualStart() {
		return actualStart;
	}

	/**
	 * @param actuaStart the actuaStart to set
	 */
	public synchronized void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	/**
	 * @return the actualEnd
	 */
	public synchronized Date getActualEnd() {
		return actualEnd;
	}

	/**
	 * @param actualEnd the actualEnd to set
	 */
	public synchronized void setActualEnd(Date actualEnd) {
		this.actualEnd = actualEnd;
	}
	
	public float calculateHoursEstimated()
	{
		float diff=0;
		diff=calculateDifference(startEstimate,endEstimate);
		hoursEstimated=effortsPerDay*diff;
		System.out.println("hoursEstimated:="+hoursEstimated);
		return hoursEstimated;
	}
	
	
}

	public void removeChild(TreeNode selectedRootNode)
	{
		 if(selectedRootNode!=null)
		 {
			 TreeNode parent = selectedRootNode.getParent();
			 parent.getChildren().remove(selectedRootNode);
	
		 }
	}
	
	public void addParentNode()
	{
		if(root!=null)
		{
			
			WbsDocument doc=new WbsDocument(null,project_id,null,null,null,null,null,null,0);
			TreeNode node= new DefaultTreeNode(doc,root);
			//root.getChildren().add(node);
			
			
		}
	}
	public void addChild(TreeNode selectedRootNode)
	{
		if(project_id!=null)
			System.out.println("WbsTreeBeanManaged:Projectd="+project_id.toString());
		
		if(selectedRootNode!=null)
		{
			
			System.out.println("addChild");
			WbsDocument doc=new WbsDocument(null,project_id,null,null,null,null,null,null,0);
			TreeNode node= new DefaultTreeNode(doc,selectedRootNode);
			//selectedRootNode.getChildren().add(node);
			if(selectedRootNode.getClass().equals(WbsDocument.class))
			{
				WbsTreeBeanManaged.WbsDocument wbsData=(WbsTreeBeanManaged.WbsDocument)selectedRootNode.getData();
				if(wbsData!=null && wbsData.project_id!=null)
				{
					System.out.println("SelectedNode:wbsData:project_id:"+wbsData.getProject_id().toString());
					System.out.println("SelectedNode:wbsData:task:"+wbsData.task_name);
				}
			}
		}
		
	}
	
	public static int calculateDifference(Date a, Date b)
	{
		System.out.println("entered:calculateDifference");
		
		if(a==null || b==null || !a.getClass().equals(Date.class) || !b.getClass().equals(Date.class))
		{
			System.out.println("exited:calculateDifference:a==null || b==null");
			return 0;
		}
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
	    System.out.println("exited:calculateDifference:difference="+difference);
	    return difference;
	}
	
	public void createWbsTasks()
	{
		if(root!=null &&root.getChildren()!=null)
		{
			//1.updateParentSchedules
			for(int i=0;i<root.getChildren().size();i++)
			{
				updateParentActivitySchedule(root.getChildren().get(i));
			}		
			
			//2.create Top Activity Parent Tasks and corresponding WbsTasks
			createParentActivityTasks();
			
			//3.create inserts of wbstbl for every child along with inserts of tasktbl
			for(int i=0;i<root.getChildren().size();i++)
			{
				processForChildTasksCreation(root.getChildren().get(i));
			}
		}
		
		//once done clear the list.
		int count=root.getChildCount();
		/*for(int i=0;i<count;i++)
		{
			root.getChildren().get(i).setParent(null);		
		}
		*/
	}
	
	
	private void createParentActivityTasks()
	{
		if(root!=null &&root.getChildren()!=null)
		{		
			//2.create Top Activity Parent Tasks and corresponding WbsTasks
			for(int i=0;i<root.getChildren().size();i++)
			{
				TreeNode node=root.getChildren().get(i);
				WbsDocument wbsDoc=((WbsDocument)node.getData());
				if(wbsDoc.task_name==null || wbsDoc.getProject_id()==null || wbsDoc.getTask_name().equalsIgnoreCase(""))
					continue;
				
				Projecttbl ptable=taskBean.getProjectByProjectID(wbsDoc.getProject_id());
				if(ptable==null)
				{
					System.out.println("taskBean.getProjectByProjectID:returned projectTable=null");
					continue;
				}
			    //parentID as null
				Tasktbl taskTbl=taskBean.addDirectWbsTask(wbsDoc.getTask_name(),ptable,"development", null);
				if(taskTbl!=null)
					wbsDoc.setTaskTable(taskTbl);
				else
					System.out.println("taskBean.addDirectWbsTask:returned tasktbl=null");
				
				wbsDoc.setProjectTable(ptable);
				//now createWbsTask.
				try {
					Wbstbl wbsTable=wbsEJB.addWbsTask(wbsDoc.getWbs_id(),taskTbl,wbsDoc.getPriority(),ptable,userBean.getUserTblByUserID(wbsDoc.getUser_name()),wbsDoc.getStartEstimate(),wbsDoc.getEndEstimate(),wbsDoc.getActualStart(),wbsDoc.getActualEnd(),wbsDoc.getEffortsPerDay());
					if(wbsTable!=null)
					{
						wbsDoc.setWbsTable(wbsTable);
					}
					else
						System.out.println("wbsEJB.addWbsTask:returned wbsTable=null");
					
				} catch (CreateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
					
	}
	
	private void processForChildTasksCreation(TreeNode pNode)
	{
		System.out.println("processForChildTasksCreation:");
		
		if(pNode==null || pNode.getChildCount()<=0)
			return;
		WbsDocument wbsParentDoc=((WbsDocument)pNode.getData());
		if(wbsParentDoc==null && wbsParentDoc.task_name==null || wbsParentDoc.getTask_name().equalsIgnoreCase("") || wbsParentDoc.getProjectTable()==null || wbsParentDoc.getTaskTable()==null || wbsParentDoc.getWbsTable()==null)
		{
			System.out.println("parentNode:TaskName:"+wbsParentDoc.task_name);
			return;
		}
		
		for(int i=0;i<pNode.getChildren().size();i++)
		{
			TreeNode node=pNode.getChildren().get(i);
			//check for valid WbsDocument
			WbsDocument wbsDoc=((WbsDocument)node.getData());
			//if(wbsDoc==null || wbsDoc.getTask_name().equalsIgnoreCase("") || wbsDoc.getProjectTable()==null || wbsDoc.getTaskTable()==null || wbsDoc.getWbsTable()==null)
			//{
			//	System.out.println("processForChildTasksCreation:wbsDoc.tables empty");
				
			//	continue;
			//}
			
			Projecttbl ptable=taskBean.getProjectByProjectID(wbsDoc.getProject_id());
			if(ptable==null)
			{
				System.out.println("processForChildTasksCreation:taskBean.getProjectByProjectID returned null");
				
				continue;
			}
			
		    //parentID as null
			Tasktbl taskTbl=taskBean.addDirectWbsTask(wbsDoc.getTask_name(),ptable,"development",wbsParentDoc.getTaskTable());
			if(taskTbl!=null)
			{
				wbsDoc.setTaskTable(taskTbl);
			}
			else
				System.out.println("processForChildTasksCreation:taskBean.addDirectWbsTask returned null");
			
			wbsDoc.setProjectTable(ptable);
			//now createWbsTask
			try {
				Wbstbl wbsTable=wbsEJB.addWbsTask(wbsDoc.getWbs_id(),taskTbl,wbsDoc.getPriority(),ptable,userBean.getUserTblByUserID(wbsDoc.getUser_name()),wbsDoc.getStartEstimate(),wbsDoc.getEndEstimate(),wbsDoc.getActualStart(),wbsDoc.getActualEnd(),wbsDoc.getEffortsPerDay());
				if(wbsTable!=null)
				{
					wbsDoc.setWbsTable(wbsTable);
					
				}
				else
					System.out.println("processForChildTasksCreation:wbsEJB.addWbsTask returned null");
				
				
			} catch (CreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//check for duplicate entry
				
			}
			//recursively create tasks
			processForChildTasksCreation(node);
		}
	}
	
	
	private void updateParentActivitySchedule(TreeNode node)
	{
		//recursive
		if(node==null)
			return;
		if(node.isLeaf() && node.getData().getClass().equals(WbsDocument.class) && node.getParent().getData().getClass().equals(WbsDocument.class))
		{
			//This is the actual lower most sub task
			Date cStartEst=((WbsDocument)node.getData()).getStartEstimate();
			Date cEndEst=((WbsDocument)node.getData()).getEndEstimate();
			
			Date cStartAct=((WbsDocument)node.getData()).getActualStart();
			Date cEndAct=((WbsDocument)node.getData()).getActualEnd();
			
			if(node.getParent()!=null)
			{
				Date pStartEst=((WbsDocument)node.getParent().getData()).getStartEstimate();
				if(pStartEst!=null && cStartEst!=null && pStartEst.compareTo(cStartEst)>0 )
				{
					((WbsDocument)node.getParent().getData()).setStartEstimate(cStartEst);
				}
				Date pEndEst=((WbsDocument)node.getParent().getData()).getEndEstimate();
				if(pEndEst!=null && cEndEst!=null && pEndEst.compareTo(cEndEst)<=0 )
				{
					((WbsDocument)node.getParent().getData()).setEndEstimate(cEndEst);
				}
				
				Date pStartAct=((WbsDocument)node.getParent().getData()).getActualStart();
				if(pStartAct!=null && cStartAct!=null && pStartAct.compareTo(cStartAct)>0 )
				{
					((WbsDocument)node.getParent().getData()).setActualStart(cStartAct);
				}
								
				Date pEndAct=((WbsDocument)node.getParent().getData()).getActualEnd();
				if(pEndAct!=null && cEndAct!=null && pEndAct.compareTo(cEndAct)<=0 )
				{
					((WbsDocument)node.getParent().getData()).setActualEnd(cEndAct);				
				}
			}
			
			
		}
		else
		{
			for(int i=0;i<node.getChildCount();i++)
			{
				updateParentActivitySchedule(node.getChildren().get(i));
			}
		}
		
	}
	
}
