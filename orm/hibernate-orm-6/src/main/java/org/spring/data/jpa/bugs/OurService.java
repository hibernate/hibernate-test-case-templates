package org.spring.data.jpa.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OurService {

    private final EntityManager entityManager;

    public OurService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<OurEntityPercentageStatus> createPercentageQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<OurEntityPercentageStatus> criteriaQuery = criteriaBuilder.createQuery(OurEntityPercentageStatus.class);
        Root<OurEntity> root = criteriaQuery.from(OurEntity.class);
        criteriaQuery.groupBy(root.get("commonName"));

        OurStatus ourStatus = OurStatus.STATUS_1;

        // Get percentage of ourEntities in status STATUS_1. PSEUDOCODE: SUM(case when our_status == STATUS_1 then 1 else 0 end) / cast(Count(*) as decimal)) * 100  as percentage_out
        Selection<Double> percentageOutSelection = criteriaBuilder.prod(criteriaBuilder.quot(criteriaBuilder.sum(
            criteriaBuilder.selectCase(root.get("ourStatus"))
                .when(ourStatus.ordinal(), 1.0)
                .otherwise(0.0)
                .as(Double.class)), criteriaBuilder.count(root)), 100.0).as(Double.class).alias("percentage_out");

        //Get objects with dealerDatabaseId and percentage_out
        criteriaQuery.select(
            criteriaBuilder.construct(OurEntityPercentageStatus.class, root.get("commonName"), percentageOutSelection));
        CriteriaQuery<OurEntityPercentageStatus> finalQuery = criteriaQuery.multiselect(root.get("commonName"),
            percentageOutSelection);
        return entityManager.createQuery(finalQuery).getResultList();
    }
}
