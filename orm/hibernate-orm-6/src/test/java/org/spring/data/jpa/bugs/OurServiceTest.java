package org.spring.data.jpa.bugs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@Import({ OurServiceTest.TestConfiguration.class })
class OurServiceTest {

    @Autowired
    private OurService ourService;
    @Autowired
    private OurEntityRepository ourEntityRepository;

    // Add your tests, using standard JUnit 5.
    @Test
    void test() {
        OurEntity entity1 = new OurEntity();
        entity1.setName("test1");
        entity1.setCommonName("commonName");
        entity1.setOurStatus(OurStatus.STATUS_1);
        ourEntityRepository.save(entity1);


        OurEntity entity2 = new OurEntity();
        entity2.setName("test2");
        entity2.setCommonName("commonName");
        entity2.setOurStatus(OurStatus.STATUS_1);
        ourEntityRepository.save(entity2);

        OurEntity entity3 = new OurEntity();
        entity3.setName("test3");
        entity3.setCommonName("commonName");
        entity3.setOurStatus(OurStatus.STATUS_2);
        ourEntityRepository.save(entity3);

        List<OurEntityPercentageStatus> result = ourService.createPercentageQuery();
        assertEquals(66, result.get(0).getPercentageOut().intValue());
    }

    @ComponentScan(basePackages = "org.spring.data.jpa.bugs",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
            ".*TestConfiguration"
        }))
    @SpringBootApplication
    static class TestConfiguration {}
}
