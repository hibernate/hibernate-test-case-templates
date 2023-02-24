package com.zurichna.claim.entity.test;

import com.zurichna.claim.entity.validation.common.ValidClaimNumber;
import com.zurichna.claim.entity.validation.common.ValidProductionCode;
import com.zurichna.claim.entity.validation.validatorgroups.PrimaryClaimNumberValidationGroup;
import com.zurichna.claim.status.Remark;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class ClaimFolderPK {

	@Column(updatable = false, name = "DB_PARTN_NBR")
	protected String partitionNumber;

	@ValidProductionCode(groups = PrimaryClaimNumberValidationGroup.class)
	@Column(updatable = false, name = "PROD_TRNG_CD")
	protected String productionOrTrainingCode;

	@ValidClaimNumber(groups = PrimaryClaimNumberValidationGroup.class)
	@Column(updatable = false, name = "CLM_NBR")
	protected String claimNumber;
	
	

	

	public String getPartitionNumber() {
		if (this.partitionNumber == null) {
			this.partitionNumber = this.claimNumber.substring(8);
		}
		return partitionNumber;
	}

	public void setPartitionNumber(String partitionNumber) {
		this.partitionNumber = partitionNumber;
	}

	public String getProductionOrTrainingCode() {
		return productionOrTrainingCode;
	}

	public void setProductionOrTrainingCode(String productionOrTrainingCode) {
		this.productionOrTrainingCode = productionOrTrainingCode;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
		if(StringUtils.isNotBlank(claimNumber) && claimNumber.length()>8){
			this.partitionNumber=this.claimNumber.substring(8);
		}
	}

	public int hashCode() {
		if (partitionNumber == null) {
			return (int) productionOrTrainingCode.hashCode()
					+ (int) claimNumber.hashCode();
		} else {
			return (int) partitionNumber.hashCode()
					+ (int) productionOrTrainingCode.hashCode()
					+ (int) claimNumber.hashCode();
		}
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ClaimFolderPK)) {
			return false;
		}
		if (obj == this) {
			return true;
		}

		ClaimFolderPK pk = (ClaimFolderPK) obj;
		if (partitionNumber == null) {
			return pk.productionOrTrainingCode
					.equals(this.productionOrTrainingCode)
					&& pk.claimNumber.equals(this.claimNumber);
		} else {
			return pk.partitionNumber.equals(this.partitionNumber)
					&& pk.productionOrTrainingCode
							.equals(this.productionOrTrainingCode)
					&& pk.claimNumber.equals(this.claimNumber);
		}

	}

	@Override
	public String toString() {
		return "ClaimReference [partitionNumber=" + partitionNumber
				+ ", productionOrTrainingCode=" + productionOrTrainingCode
				+ ", claimNumber=" + claimNumber + "]";
	}
	
	public static ClaimFolderPK getPK(String clmNbr, String prodTrngCd){
		ClaimFolderPK pk=new ClaimFolderPK();
		pk.setClaimNumber(clmNbr);
		pk.setProductionOrTrainingCode(prodTrngCd);
		return pk;
		
	}

	/*
	 * check the string lengths
	 */
	private static final int MAX_CLAIM_NBR_LENGTH = 10;
	private static final int MAX_PARTITION_LENGTH = 2;
	private static final int MAX_PT_LENGTH = 1;

	public Remark verify() {
		if (this.claimNumber.length() > MAX_CLAIM_NBR_LENGTH) {
			return new Remark("length of idRef is greater than "
					+ MAX_CLAIM_NBR_LENGTH);
		}
		if (this.partitionNumber.length() > MAX_PARTITION_LENGTH) {
			return new Remark("length of partition is greater than "
					+ MAX_PARTITION_LENGTH);
		}
		if (this.productionOrTrainingCode.length() > MAX_PT_LENGTH) {
			return new Remark("length of productionOrTraining is greater than "
					+ MAX_PT_LENGTH);
		}

		return null;
	}

}
