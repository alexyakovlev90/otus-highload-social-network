package ru.otus.highload.socialbackend.feature.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserInfoItemDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private Integer age;
    private Boolean sex;
    private String interest;
    private String city;
    private Date registerDate;

    private Boolean friend;
    private Boolean loggedIn;
}
