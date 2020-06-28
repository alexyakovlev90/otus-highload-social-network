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
            " INNER JOIN friend_request fr ON wp.to_user_id=fr.friend_id OR wp.to_user_id=fr.user_id" +
            " WHERE (fr.friend_id = ?1 OR fr.user_id=?1) AND wp.id >= ?2 " +
            " ORDER BY wp.date_created DESC " +
            " LIMIT ?3",
            nativeQuery = true)
    List<WallPost> getByToUserInWithOffset(Long userId, Long minId, Long limit);


    List<WallPost> getWallPostsByToUserOrderByDateCreatedDesc(Long toUser);
}
