package ru.otus.highload.socialbackend.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialbackend.domain.User;

import java.util.List;
import java.util.Optional;


@SuppressWarnings("unused")
@Repository
public interface UserMasterRepository extends JpaRepository<User, Long> {
    Optional<User> getByLogin(String login);

    @Query(value= "SELECT * FROM user WHERE first_name like ?1 and last_name like ?2" +
            " LIMIT 10",
            nativeQuery = true)
    List<User> getByFirstNameAndLastName(String firstName, String lastName);
}
