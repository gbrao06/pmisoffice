package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the wbstbl database table.
 * 
 */
@Entity
@Table(name="wbstbl")
public class Wbstbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="efforts_per_day", nullable=false)
	private float effortsPerDay;

	@Temporal(TemporalType.DATE)
	@Column(name="end_actual")
	private Date endActual;

	@Temporal(TemporalType.DATE)
	@Column(name="end_estimate")
	private Date endEstimate;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	@Column(nullable=false)
	private int priority;

	@Temporal(TemporalType.DATE)
	@Column(name="start_actual")
	private Date startActual;

	@Temporal(TemporalType.DATE)
	@Column(name="start_estimate")
	private Date startEstimate;

	@Column(name="wbs_id", nullable=false, length=45)
	private String wbsId;

	@Column(length=45)
	private String wbstblcol;

	//bi-directional many-to-one association to Timesheettbl
	@OneToMany(mappedBy="wbstbl")
	private List<Timesheettbl> timesheettbls;

	//bi-directional many-to-one association to Projecttbl
	@ManyToOne
	@JoinColumn(name="project_pk", nullable=false)
	private Projecttbl projecttbl;

	//bi-directional many-to-one association to Tasktbl
	@ManyToOne
	@JoinColumn(name="task_pk", nullable=false)
	private Tasktbl tasktbl;

	//bi-directional many-to-one association to Usertbl
	@ManyToOne
	@JoinColumn(name="user_pk", nullable=false)
	private Usertbl usertbl;

	public Wbstbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getEffortsPerDay() {
		return this.effortsPerDay;
	}

	public void setEffortsPerDay(float effortsPerDay) {
		this.effortsPerDay = effortsPerDay;
	}

	public Date getEndActual() {
		return this.endActual;
	}

	public void setEndActual(Date endActual) {
		this.endActual = endActual;
	}

	public Date getEndEstimate() {
		return this.endEstimate;
	}

	public void setEndEstimate(Date endEstimate) {
		this.endEstimate = endEstimate;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getStartActual() {
		return this.startActual;
	}

	public void setStartActual(Date startActual) {
		this.startActual = startActual;
	}

	public Date getStartEstimate() {
		return this.startEstimate;
	}

	public void setStartEstimate(Date startEstimate) {
		this.startEstimate = startEstimate;
	}

	public String getWbsId() {
		return this.wbsId;
	}

	public void setWbsId(String wbsId) {
		this.wbsId = wbsId;
	}

	public String getWbstblcol() {
		return this.wbstblcol;
	}

	public void setWbstblcol(String wbstblcol) {
		this.wbstblcol = wbstblcol;
	}

	public List<Timesheettbl> getTimesheettbls() {
		return this.timesheettbls;
	}

	public void setTimesheettbls(List<Timesheettbl> timesheettbls) {
		this.timesheettbls = timesheettbls;
	}

	public Projecttbl getProjecttbl() {
		return this.projecttbl;
	}

	public void setProjecttbl(Projecttbl projecttbl) {
		this.projecttbl = projecttbl;
	}

	public Tasktbl getTasktbl() {
		return this.tasktbl;
	}

	public void setTasktbl(Tasktbl tasktbl) {
		this.tasktbl = tasktbl;
	}

	public Usertbl getUsertbl() {
		return this.usertbl;
	}

	public void setUsertbl(Usertbl usertbl) {
		this.usertbl = usertbl;
	}

}