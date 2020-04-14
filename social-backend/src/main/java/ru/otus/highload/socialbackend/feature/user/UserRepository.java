package ru.otus.highload.socialbackend.feature.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialbackend.domain.User;

import java.util.Optional;


@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByLogin(String login);
}
