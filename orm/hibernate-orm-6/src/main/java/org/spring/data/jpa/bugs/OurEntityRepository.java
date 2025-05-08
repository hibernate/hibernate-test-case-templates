package org.spring.data.jpa.bugs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OurEntityRepository extends JpaRepository<OurEntity, Long> {

}
