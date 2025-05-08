package org.spring.data.jpa.bugs;

public class OurEntityPercentageStatus {

    private String commonName;
    private Double percentageStatus;

    public OurEntityPercentageStatus(String commonName, Double percentageStatus) {
        this.commonName = commonName;
        this.percentageStatus = percentageStatus;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Double getPercentageOut() {
        return percentageStatus;
    }

    public void setPercentageOut(Double percentageOut) {
        this.percentageStatus = percentageOut;
    }
}
