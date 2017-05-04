package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tasktbl database table.
 * 
 */
@Entity
@Table(name="tasktbl")
public class Tasktbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="group_type", nullable=false, length=45)
	private String groupType;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	@Column(name="parent_task_id")
	private BigInteger parentTaskId;

	@Column(name="project_id", nullable=false)
	private BigInteger projectId;

	@Column(name="task_name", nullable=false, length=60)
	private String taskName;

	//bi-directional many-to-one association to Wbstbl
	@OneToMany(mappedBy="tasktbl")
	private List<Wbstbl> wbstbls;

	public Tasktbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public BigInteger getParentTaskId() {
		return this.parentTaskId;
	}

	public void setParentTaskId(BigInteger parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public BigInteger getProjectId() {
		return this.projectId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<Wbstbl> getWbstbls() {
		return this.wbstbls;
	}

	public void setWbstbls(List<Wbstbl> wbstbls) {
		this.wbstbls = wbstbls;
	}

}