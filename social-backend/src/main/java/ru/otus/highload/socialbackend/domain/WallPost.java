package ru.otus.highload.socialbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "wall_post")
public class WallPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_user_id")
    private Long fromUser;

    @Column(name = "to_user_id")
    private Long toUser;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    private String text;
}
