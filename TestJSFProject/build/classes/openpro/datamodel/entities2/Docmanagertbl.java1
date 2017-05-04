package openpro.datamodel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * The persistent class for the docmanagertbl database table.
 * 
 */
@Entity
@Table(name="docmanagertbl")
public class Docmanagertbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="doc_name", nullable=false, length=120)
	private String docName;

	@Lob
	@Column(nullable=false)
	private byte[] document;

	@Column(name="document_description", nullable=false, length=300)
	private String documentDescription;

	@Column(name="last_updated", nullable=false)
	private Timestamp lastUpdated;

	@Column(name="project_id", nullable=false)
	private BigInteger projectId;

	public Docmanagertbl() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public byte[] getDocument() {
		return this.document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public String getDocumentDescription() {
		return this.documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public BigInteger getProjectId() {
		return this.projectId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

}