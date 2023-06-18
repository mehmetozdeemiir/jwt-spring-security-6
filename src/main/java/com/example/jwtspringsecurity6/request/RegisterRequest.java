package com.example.jwtspringsecurity6.request;

import com.example.jwtspringsecurity6.validation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.jwtspringsecurity6.constant.GeneralConstant.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = FIRSTNAME_NULL_MESSAGE)
    @Size(min = 2, max = 50, message = FIRSTNAME_VALID_MESSAGE)
    private String firstName;

    @NotBlank(message = LASTNAME_NULL_MESSAGE)
    @Size(min = 2, max = 50, message = LASTNAME_VALID_MESSAGE)
    private String lastName;

    @NotBlank(message = USERNAME_NULL_MESSAGE)
    @Size(min = 2, max = 50, message = USERNAME_VALID_MESSAGE)
    @Pattern(regexp = "^\\S*$", message = USERNAME_VALIDATION_MESSAGE)
    private String userName;

    @ValidPassword
    private String password;
}
