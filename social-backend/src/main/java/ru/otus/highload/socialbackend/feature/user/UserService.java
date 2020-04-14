package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.getByLogin(login)
                .orElseThrow(RuntimeException::new);
    }
}
