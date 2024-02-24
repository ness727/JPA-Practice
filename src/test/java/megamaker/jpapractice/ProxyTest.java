package megamaker.jpapractice;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.domain.Post;
import megamaker.jpapractice.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class ProxyTest {
    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    public void proxyTest() {
        User user = entityManager.getReference(User.class, 19);
        log.info("user = {}", user.getClass().getName());

        boolean loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(user);
        log.info("isLoaded = {}", loaded);

        user.getName();  // 데이터 조회

        loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(user);
        log.info("isLoaded = {}", loaded);
    }

    @Test
    @Transactional
    public void proxyTest2() {
        User user = entityManager.find(User.class, 19);
        log.info("user = {}", user.getClass().getName());

        boolean loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(user);
        log.info("isLoaded = {}", loaded);
    }

    @Test
    @Transactional
    public void proxyTest3() {
        Post post = Post.builder().title("좋은 아침").body("반가워요").build();  // 게시글 생성
        User user = entityManager.find(User.class, 19);  // 회원 가져옴
        post.setUser(user);

        entityManager.persist(post);  // 저장
    }
}
