package com.example.Task.Dto;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.Users;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private Integer age;

    @NotBlank

    private String username;

    @NotBlank
    private String password;

    public Users buildUser() {
        return Users.builder()
                .name(this.name)
                .email(this.email)
                .securedUser(
                        SecuredUser.builder()
                                .username(this.username)
                                .password(this.password)
                                .build())
                .username(this.username)
                .phone(this.phone)
                .age(this.age)
                .build();
    }

}
