package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class UserLentaDto {

    private Long userId;
    private List<WallPostDto> wallPosts = new ArrayList<>();
}
