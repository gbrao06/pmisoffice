package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the issuetbl database table.
 * 
 */
@Embeddable
public class IssuetblPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="issue_id", unique=true, nullable=false)
	private String issueId;

	public IssuetblPK() {
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssueId() {
		return this.issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IssuetblPK)) {
			return false;
		}
		IssuetblPK castOther = (IssuetblPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.issueId.equals(castOther.issueId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.issueId.hashCode();
		
		return hash;
	}
}