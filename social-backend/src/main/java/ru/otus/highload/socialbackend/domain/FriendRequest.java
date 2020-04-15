package ru.otus.highload.socialbackend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "FRIEND_REQUEST")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long fromUserId;

    @Column(name = "FRIEND_ID")
    private Long toUserId;

    public FriendRequest(Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
