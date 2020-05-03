package ru.otus.highload.socialbackend.repository.slave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialbackend.domain.FriendRequest;

import java.util.List;


@Repository
public interface FriendRequestSlaveRepository extends JpaRepository<FriendRequest, Long> {

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.userId = ?1 or fr.friendId = ?1")
    List<FriendRequest> getUserFriends(Long userId);

    @Query(value= "SELECT * FROM friend_request fr " +
            " WHERE fr.user_id = ?1 and fr.friend_id = ?2 OR fr.user_id = ?2 and fr.friend_id = ?1 " +
            " LIMIT 1",
            nativeQuery = true)
    FriendRequest getByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
