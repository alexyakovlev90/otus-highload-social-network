package ru.otus.highload.socialbackend.feature.clickhouse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserClickHouseDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private int age;
    private Boolean sex = false;

    @Override
    public String toString() {
        int sexInt = sex ? 1 : 0;
        return "('" + id + "','" + login + "','" + firstName + "','" + lastName + "'," + age + "," + sexInt + ')';
    }
}
