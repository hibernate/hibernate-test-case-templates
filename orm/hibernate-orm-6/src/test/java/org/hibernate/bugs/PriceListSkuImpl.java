package org.hibernate.bugs;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Embeddable
public class PriceListSkuImpl {

    @OneToMany(
            mappedBy = "sku",
           targetEntity = SkuPriceDataImpl.class,
            cascade = {CascadeType.ALL}
    )
    @BatchSize(
            size = 50
    )
    private List<SkuPriceData> priceDataList;

    public List<SkuPriceData> getPriceDataList() {
        return priceDataList;
    }

    public void setPriceDataList(List<SkuPriceData> priceDataList) {
        this.priceDataList = priceDataList;
    }
}
