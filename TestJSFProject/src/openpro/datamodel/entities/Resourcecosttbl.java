package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the resourcecosttbl database table.
 * 
 */
@Entity
@Table(name="resourcecosttbl")
public class Resourcecosttbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="actual_cost")
	private float actualCost;

	@Column(name="actual_hrs_worked")
	private float actualHrsWorked;

	private byte billable;

	private float costperhour;

	@Column(name="estimated_cost")
	private float estimatedCost;

	@Column(name="estimated_work_hrs")
	private float estimatedWorkHrs;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	//bi-directional many-to-one association to Projecttbl
	@ManyToOne
	@JoinColumn(name="project_pk", nullable=false)
	private Projecttbl projecttbl;

	//bi-directional many-to-one association to Usertbl
	@ManyToOne
	@JoinColumn(name="user_pk", nullable=false)
	private Usertbl usertbl;

	public Resourcecosttbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getActualCost() {
		return this.actualCost;
	}

	public void setActualCost(float actualCost) {
		this.actualCost = actualCost;
	}

	public float getActualHrsWorked() {
		return this.actualHrsWorked;
	}

	public void setActualHrsWorked(float actualHrsWorked) {
		this.actualHrsWorked = actualHrsWorked;
	}

	public byte getBillable() {
		return this.billable;
	}

	public void setBillable(byte billable) {
		this.billable = billable;
	}

	public float getCostperhour() {
		return this.costperhour;
	}

	public void setCostperhour(float costperhour) {
		this.costperhour = costperhour;
	}

	public float getEstimatedCost() {
		return this.estimatedCost;
	}

	public void setEstimatedCost(float estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public float getEstimatedWorkHrs() {
		return this.estimatedWorkHrs;
	}

	public void setEstimatedWorkHrs(float estimatedWorkHrs) {
		this.estimatedWorkHrs = estimatedWorkHrs;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Projecttbl getProjecttbl() {
		return this.projecttbl;
	}

	public void setProjecttbl(Projecttbl projecttbl) {
		this.projecttbl = projecttbl;
	}

	public Usertbl getUsertbl() {
		return this.usertbl;
	}

	public void setUsertbl(Usertbl usertbl) {
		this.usertbl = usertbl;
	}

}