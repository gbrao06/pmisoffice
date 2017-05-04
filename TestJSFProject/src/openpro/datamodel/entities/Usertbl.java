package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the usertbl database table.
 * 
 */
@Entity
@Table(name="usertbl")
public class Usertbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(nullable=false, length=60)
	private String email;

	@Column(name="first_name", nullable=false, length=60)
	private String firstName;

	@Column(name="last_name", nullable=false, length=60)
	private String lastName;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	@Column(nullable=false, length=45)
	private String passwd;

	@Column(length=60)
	private String role;

	@Column(name="user_id", nullable=false, length=45)
	private String userId;

	//bi-directional many-to-one association to Resourcecosttbl
	@OneToMany(mappedBy="usertbl")
	private List<Resourcecosttbl> resourcecosttbls;

	//bi-directional many-to-one association to Usertimeentrytbl
	@OneToMany(mappedBy="usertbl")
	private List<Usertimeentrytbl> usertimeentrytbls;

	//bi-directional many-to-one association to Wbstbl
	@OneToMany(mappedBy="usertbl")
	private List<Wbstbl> wbstbls;

	public Usertbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Resourcecosttbl> getResourcecosttbls() {
		return this.resourcecosttbls;
	}

	public void setResourcecosttbls(List<Resourcecosttbl> resourcecosttbls) {
		this.resourcecosttbls = resourcecosttbls;
	}

	public List<Usertimeentrytbl> getUsertimeentrytbls() {
		return this.usertimeentrytbls;
	}

	public void setUsertimeentrytbls(List<Usertimeentrytbl> usertimeentrytbls) {
		this.usertimeentrytbls = usertimeentrytbls;
	}

	public List<Wbstbl> getWbstbls() {
		return this.wbstbls;
	}

	public void setWbstbls(List<Wbstbl> wbstbls) {
		this.wbstbls = wbstbls;
	}

}