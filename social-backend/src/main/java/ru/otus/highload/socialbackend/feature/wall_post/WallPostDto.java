package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.*;
import lombok.experimental.Accessors;
import ru.otus.highload.socialbackend.feature.user.UserLiteDto;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class WallPostDto {

    private Long id;
    private UserLiteDto fromUser;
    private UserLiteDto toUser;
    private String dateCreated;
    private String text;
}
