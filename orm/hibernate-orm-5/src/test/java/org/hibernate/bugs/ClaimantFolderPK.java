package  org.hibernate.bugs;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClaimantFolderPK {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(updatable = false, name = "CLM_SUB_NBR")
	protected String clmSubNbr;

	
	protected ClaimFolderPK claimFolderPK;
	
	public ClaimFolderPK getClaimFolderPK() {
		return claimFolderPK;
	}

	public void setClaimFolderPK(ClaimFolderPK claimFolderPK) {
		this.claimFolderPK = claimFolderPK;
	}

	public ClaimantFolderPK() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claimFolderPK == null) ? 0 : claimFolderPK.hashCode());
		result = prime * result + ((clmSubNbr == null) ? 0 : clmSubNbr.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ClaimantFolderPK [clmSubNbr=" + clmSubNbr + ", claimFolderPK=" + claimFolderPK + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaimantFolderPK other = (ClaimantFolderPK) obj;
		if (claimFolderPK == null) {
			if (other.claimFolderPK != null)
				return false;
		} else if (!claimFolderPK.equals(other.claimFolderPK))
			return false;
		if (clmSubNbr == null) {
			if (other.clmSubNbr != null)
				return false;
		} else if (!clmSubNbr.equals(other.clmSubNbr))
			return false;
		return true;
	}

	public String getClmSubNbr() {
		return this.clmSubNbr;
	}

	public void setClmSubNbr(String clmSubNbr) {
		this.clmSubNbr = clmSubNbr;
	}


}
