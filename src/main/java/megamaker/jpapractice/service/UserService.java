package megamaker.jpapractice.service;

import lombok.RequiredArgsConstructor;
import megamaker.jpapractice.domain.User;
import megamaker.jpapractice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(User user) {
        userRepository.save(user);
    }

    public User findByName(User user) {
        return userRepository.findOneByName(user);
    }

}
