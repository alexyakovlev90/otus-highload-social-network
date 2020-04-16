package ru.otus.highload.socialbackend.feature.friend_request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialbackend.domain.FriendRequest;

import java.util.List;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.userId = ?1 or fr.friendId = ?1")
    List<FriendRequest> getUserFriends(Long userId);

    @Query(value= "SELECT * FROM FRIEND_REQUEST fr " +
            " WHERE fr.USER_ID = ?1 and fr.FRIEND_ID = ?2 OR fr.USER_ID = ?2 and fr.FRIEND_ID = ?1 " +
            " LIMIT 1",
            nativeQuery = true)
    FriendRequest getByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
