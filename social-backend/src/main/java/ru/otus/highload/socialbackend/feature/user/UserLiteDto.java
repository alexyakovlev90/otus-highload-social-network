package ru.otus.highload.socialbackend.feature.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLiteDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
}
