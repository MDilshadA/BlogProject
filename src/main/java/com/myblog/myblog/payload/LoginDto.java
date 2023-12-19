package com.myblog.myblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    private String usernameOrEmail;
    @NotEmpty
    private String password;
}
