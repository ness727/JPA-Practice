package megamaker.jpapractice.persistence;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.repository.DBUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;


@Slf4j
@SpringBootTest
public class ApplicationContextTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource1;

    @Test
    @DisplayName("데이터소스 비교")
    void dataSourceSameTest() {
        DataSource dataSource2 = (DataSource)applicationContext.getBean("dataSource");

        Assertions.assertThat(dataSource1).isSameAs(dataSource2);  // 동일성 비교

        log.info("dataSource1 = {}", dataSource1);
        log.info("dataSource2 = {}", dataSource2);
    }
}
