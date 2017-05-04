package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the issuetbl database table.
 * 
 */
@Entity
@Table(name="issuetbl")
public class Issuetbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssuetblPK id;

	@Column(name="assigned_to", nullable=false, length=45)
	private String assignedTo;

	@Column(length=45)
	private String category;

	@Column(length=60)
	private String comments;

	@Column(length=45)
	private String component;

	@Temporal(TemporalType.DATE)
	@Column(name="date_reported", nullable=false)
	private Date dateReported;

	@Temporal(TemporalType.DATE)
	@Column(name="date_resolved", nullable=false)
	private Date dateResolved;

	@Column(name="depends_on")
	private BigInteger dependsOn;

	@Column(name="issue_description", nullable=false, length=100)
	private String issueDescription;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	@Column(length=45)
	private String platform;

	@Column(length=45)
	private String product;

	@Column(name="project_id", nullable=false)
	private BigInteger projectId;

	@Column(name="qa_contact", length=45)
	private String qaContact;

	@Column(name="reported_by", nullable=false, length=45)
	private String reportedBy;

	@Column(nullable=false)
	private int severity;

	@Column(nullable=false, length=45)
	private String status;

	@Column(length=45)
	private String subcategory;

	@Column(length=100)
	private String url;

	@Column(length=45)
	private String version;

	public Issuetbl() {
	}

	public IssuetblPK getId() {
		return this.id;
	}

	public void setId(IssuetblPK id) {
		this.id = id;
	}

	public String getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComponent() {
		return this.component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Date getDateReported() {
		return this.dateReported;
	}

	public void setDateReported(Date dateReported) {
		this.dateReported = dateReported;
	}

	public Date getDateResolved() {
		return this.dateResolved;
	}

	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}

	public BigInteger getDependsOn() {
		return this.dependsOn;
	}

	public void setDependsOn(BigInteger dependsOn) {
		this.dependsOn = dependsOn;
	}

	public String getIssueDescription() {
		return this.issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigInteger getProjectId() {
		return this.projectId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

	public String getQaContact() {
		return this.qaContact;
	}

	public void setQaContact(String qaContact) {
		this.qaContact = qaContact;
	}

	public String getReportedBy() {
		return this.reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public int getSeverity() {
		return this.severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubcategory() {
		return this.subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}