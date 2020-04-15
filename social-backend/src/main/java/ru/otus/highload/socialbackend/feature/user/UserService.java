package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.auth.PasswordUtils;
import ru.otus.highload.socialbackend.domain.User;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        String password = user.getPassword();
        if (password != null) {
            String securePassword = PasswordUtils.generateSecurePassword(password, PasswordUtils.SALT);
            user.setPassword(securePassword);
        }
        return userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.getByLogin(login)
                .orElse(null);
    }
}
