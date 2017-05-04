package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the usertimeentrytbl database table.
 * 
 */
@Entity
@Table(name="usertimeentrytbl")
public class Usertimeentrytbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	private byte canedit;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date weekdate;

	//bi-directional many-to-one association to Usertbl
	@ManyToOne
	@JoinColumn(name="user_pk", nullable=false)
	private Usertbl usertbl;

	public Usertimeentrytbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte getCanedit() {
		return this.canedit;
	}

	public void setCanedit(byte canedit) {
		this.canedit = canedit;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getWeekdate() {
		return this.weekdate;
	}

	public void setWeekdate(Date weekdate) {
		this.weekdate = weekdate;
	}

	public Usertbl getUsertbl() {
		return this.usertbl;
	}

	public void setUsertbl(Usertbl usertbl) {
		this.usertbl = usertbl;
	}

}