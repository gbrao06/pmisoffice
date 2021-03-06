package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the timesheettbl database table.
 * 
 */
@Entity
@Table(name="timesheettbl")
public class Timesheettbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	private float fri;

	@Column(name="last_updated")
	private Timestamp lastUpdated;

	private float mon;

	private float sat;

	private float sun;

	private float thu;

	private float total;

	private float tue;

	private float wed;

	@Temporal(TemporalType.DATE)
	@Column(name="week_date", nullable=false)
	private Date weekDate;

	//bi-directional many-to-one association to Wbstbl
	@ManyToOne
	@JoinColumn(name="wbs_pk", nullable=false)
	private Wbstbl wbstbl;

	public Timesheettbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getFri() {
		return this.fri;
	}

	public void setFri(float fri) {
		this.fri = fri;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public float getMon() {
		return this.mon;
	}

	public void setMon(float mon) {
		this.mon = mon;
	}

	public float getSat() {
		return this.sat;
	}

	public void setSat(float sat) {
		this.sat = sat;
	}

	public float getSun() {
		return this.sun;
	}

	public void setSun(float sun) {
		this.sun = sun;
	}

	public float getThu() {
		return this.thu;
	}

	public void setThu(float thu) {
		this.thu = thu;
	}

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getTue() {
		return this.tue;
	}

	public void setTue(float tue) {
		this.tue = tue;
	}

	public float getWed() {
		return this.wed;
	}

	public void setWed(float wed) {
		this.wed = wed;
	}

	public Date getWeekDate() {
		return this.weekDate;
	}

	public void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
	}

	public Wbstbl getWbstbl() {
		return this.wbstbl;
	}

	public void setWbstbl(Wbstbl wbstbl) {
		this.wbstbl = wbstbl;
	}

}