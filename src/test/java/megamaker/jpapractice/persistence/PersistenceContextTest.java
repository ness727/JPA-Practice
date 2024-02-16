package megamaker.jpapractice.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {
    @Autowired EntityManager em;
    @Autowired PlatformTransactionManager tm;
    @Autowired DataSource dataSource;

    @Test
    void newPostTest() {
        // given
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Post post = Post.builder()
                .title("첫 번째 글")
                .body("반가워요")
                .build();

        // when
        log.info("----- 트랜잭션 시작 -----");
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        em.persist(post);  // 저장

        // 실제로 insert문이 바로 실행되는지 검증
//        String sql = "SELECT * FROM post WHERE title like '첫 번째 글'";
//        Post findPost = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
//            Post.builder()
//                .id(rs.getLong("id"))
//                .title(rs.getString("title"))
//                .body(rs.getString("body"))
//                .build()
//        );
//        log.info("findPost = {}", findPost);
        log.info("a");
        tm.commit(status);
        log.info("----- 트랜잭션 종료 -----");

        // then
        // commit 전에 DB를 조회하면 아직 데이터가 없을 것이다.
        //Assertions.assertThat(findPost).isNull();
        //Post findPost = em.find(Post.class, 1);
        //Assertions.assertThat("첫 번째 글".equals(findPost.getTitle())).isTrue();
    }

    @Test
    void findPostTest() {
        // given
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Post post = Post.builder()
                .title("첫 번째 글")
                .body("반가워요")
                .build();

        // when
        log.info("----- 트랜잭션 시작 -----");
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        em.persist(post);  // 저장

        String sql = "SELECT * FROM post WHERE title like '첫 번째 글'";

        em.clear();  // 영속성 컨텍스트를 비운다!
        Post findPost = em.find(Post.class, 1);  // 조회
        findPost = em.find(Post.class, 1);  // 조회

        tm.commit(status);
        log.info("----- 트랜잭션 종료 -----");

        // then
        findPost = em.find(Post.class, 1);  // 조회
    }

    @Test
    void updatePostTest() {
        // given
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Post post = Post.builder()
                .title("첫 번째 글")
                .body("반가워요")
                .build();

        // when
        log.info("----- 트랜잭션 시작 -----");
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        em.persist(post);  // 저장
        post.setTitle("수정된 글");  // 수정

        // DB의 데이터 가져옴
        String sql = "SELECT * FROM post WHERE title like '첫 번째 글'";
        Post findPost = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                Post.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .body(rs.getString("body"))
                        .build()
        );

        // then
        // 아직 커밋하기 전이니 DB에 수정 사항이 반영되어있지 않아야 함
        log.info("post.title = {}, findPost.title = {}", post.getTitle(), findPost.getTitle());
        Assertions.assertThat(post.getTitle().equals(findPost.getTitle())).isFalse();

        tm.commit(status);
        log.info("----- 트랜잭션 종료 -----");

        sql = "SELECT * FROM post WHERE id = ?";
        findPost = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                Post.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .body(rs.getString("body"))
                        .build()
        , post.getId());

        // 커밋 했으니 DB에 수정 사항이 반영되어 있어야 함
        log.info("post.title = {}, findPost.title = {}", post.getTitle(), findPost.getTitle());
        Assertions.assertThat(post.getTitle().equals(findPost.getTitle())).isTrue();
    }

    @Test
    void removePostTest() {
        // given
        log.info("----- 트랜잭션 시작 -----");
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());
        Post post = em.find(Post.class, 17);  // 조회

        // when
        em.remove(post);  // 삭제

        tm.commit(status);
        log.info("----- 트랜잭션 종료 -----");

        // then
        Assertions.assertThat(em.find(Post.class, 17)).isNull();
    }
}
