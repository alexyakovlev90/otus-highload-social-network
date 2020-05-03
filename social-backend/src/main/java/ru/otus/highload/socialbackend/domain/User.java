package ru.otus.highload.socialbackend.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

//    @JsonIgnore
    @NotNull
//    @Size(min = 60, max = 60)
    @Column(name = "PASSWORD_HASH")
    private String password;

    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "SEX")
    private Boolean sex;

    @Size(max = 1024)
    @Column(name = "INTEREST")
    private String interest;

    @Size(max = 100)
    @Column(name = "CITY")
    private String city;

    @Column(name = "REGISTER_DATE")
    private Date registerDate;
}
