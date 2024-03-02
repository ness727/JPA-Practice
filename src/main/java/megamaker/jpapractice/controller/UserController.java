package megamaker.jpapractice.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.domain.User;
import megamaker.jpapractice.service.MemberService;
import megamaker.jpapractice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    @ResponseBody
    @GetMapping("/user/test")
    public String join() {
        User user = User.builder().name("민수").build();

        // 회원 조회
        User foundUser = userService.findByName(user);

        // 지연 로딩 프록시 객체 확인
        boolean isPostLoaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(foundUser.getPost());
        boolean isRoleLoaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(foundUser.getRole());
        log.info("isPostLoaded = {}", isPostLoaded);
        log.info("isRoleLoaded = {}", isRoleLoaded);
        log.info("----------------");

        // OSIV 객체 그래프 탐색
        foundUser.getPost().size();
        foundUser.getRole().getName();

        isPostLoaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(foundUser.getPost());
        isRoleLoaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(foundUser.getRole());
        log.info("isPostLoaded = {}", isPostLoaded);
        log.info("isRoleLoaded = {}", isRoleLoaded);

        foundUser.setName("지토");
        entityManager.flush();

        return "test";
    }
}
