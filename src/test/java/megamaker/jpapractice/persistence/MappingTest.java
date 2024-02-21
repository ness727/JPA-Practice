package megamaker.jpapractice.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.domain.Role;
import megamaker.jpapractice.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class MappingTest {
    @Autowired EntityManager entityManager;
    @Autowired PlatformTransactionManager transactionManager;

    @Test
    //@Transactional
    void create() {
        log.info("EntityManager = {}", entityManager.getClass());
        log.info("this = {}", this.getClass());

        // given
        Role role = Role.builder().name("학생").build();
        User user = User.builder().name("민수").role(role).createdAt(new Timestamp(System.currentTimeMillis())).build();

        // when
        log.info("----- 트랜잭션 시작 -----");
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        entityManager.persist(role);
        entityManager.persist(user);
        transactionManager.commit(status);
        log.info("----- 트랜잭션 종료 -----");

        // then
        Assertions.assertThat("학생".equals(entityManager.find(User.class, user.getId()).getRole().getName())).isTrue();
    }

    @Test
    void get() {
        log.info("EntityManager = {}", entityManager.getClass());
        log.info("this = {}", this.getClass());
        // given
        Role role = entityManager.find(Role.class, 16);
        User user = entityManager.find(User.class, 16);

        // when
        Role userRole = user.getRole();

        // then
        Assertions.assertThat(userRole.getId()).isEqualTo(role.getId());
    }

    @Transactional
    @Commit
    @Test
    void update() {
        // given
        String newName = "지토";
        User user = entityManager.find(User.class, 16);

        // when
        user.setName(newName);
        // flush() 호출해도 영속 컨텍스트가 비워지는건 아님!
        //entityManager.flush();
        //entityManager.clear();

        // then
        Assertions.assertThat(entityManager.find(User.class, 16).getName()).isEqualTo(newName);
    }

    @Transactional
    @Commit
    @Test
    void remove() {
        // given
        User user = entityManager.find(User.class, 18);

        // when
        entityManager.remove(user);
        log.info("영속성 컨텍스트에 있는가? = {}", entityManager.contains(user));

        // then
        Assertions.assertThat(entityManager.find(User.class, user.getId())).isNull();
        log.info("삭제함");
    }
}
