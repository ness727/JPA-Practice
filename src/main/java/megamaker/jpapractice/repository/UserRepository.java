package megamaker.jpapractice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public Optional<User> get(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public User findOneByName(User user) {
        String jpql = "SELECT u FROM User u WHERE u.name LIKE :name";

        User foundUser = entityManager.createQuery(jpql, User.class)
                .setParameter("name", user.getName())
                .setFirstResult(0)
                .setMaxResults(1)
                .getSingleResult();
        return foundUser;
    }

    public void remove() {

    }
}
