package org.spring.data.jpa.bugs;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // Add your tests, using standard JUnit 5.
    @Test
    void test() {
        List<OurEntityPercentageStatus> result = ourService.createPercentageQuery();
        assertTrue(result.isEmpty());
    }

    @ComponentScan(basePackages = "org.spring.data.jpa.bugs",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
            ".*TestConfiguration"
        }))
    @SpringBootApplication
    static class TestConfiguration {}
}
