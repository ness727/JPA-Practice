package megamaker.jpapractice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import megamaker.jpapractice.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager entityManager;

    public void create(User user) {
        entityManager.persist(user);
    }

    public Optional<User> get(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public void remove() {

    }
}
