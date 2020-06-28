package ru.otus.highload.socialbackend.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialbackend.domain.WallPost;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface WallPostRepository extends JpaRepository<WallPost, Long> {


    @Query(value= "SELECT distinct wp.* FROM wall_post wp " +
            " INNER JOIN friend_request fr ON wp.from_user_id=fr.user_id AND wp.to_user_id=fr.friend_id" +
            "                               OR wp.from_user_id=fr.friend_id AND wp.to_user_id=fr.user_id" +
            " WHERE wp.from_user_id = ?1 AND wp.id > ?2 LIMIT ?3",
            nativeQuery = true)
    List<WallPost> getByToUserInWithOffset(Long userId, Long minId, Long limit);
}
