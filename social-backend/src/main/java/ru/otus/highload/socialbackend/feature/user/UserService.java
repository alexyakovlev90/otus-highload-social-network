package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
