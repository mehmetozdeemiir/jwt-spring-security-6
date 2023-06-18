package com.example.jwtspringsecurity6.constant;

import javax.print.DocFlavor;

public class GeneralConstant {
    private GeneralConstant(){}

    public static final String FIRSTNAME_VALID_MESSAGE = "First name must be between 2 and 50 characters";
    public static final String LASTNAME_VALID_MESSAGE = "Last name must be between 2 and 50 characters";
    public static final String USERNAME_VALID_MESSAGE = "Username must be between 2 and 50 characters";
    public static final String FIRSTNAME_NULL_MESSAGE = "First name cannot be null";
    public static final String LASTNAME_NULL_MESSAGE = "Last name cannot be null";
    public static final String USERNAME_NULL_MESSAGE = "Username cannot be null";
    public static final String USERNAME_ALREADY_EXIST = "Username already exists!";
    public static final String USERNAME_VALIDATION_MESSAGE = "Username cannot contain spaces!";

    public static final String USER_NOT_FOUND = "User not found!";

    public static final String INVALID_PASSWORD = "Invalid Password";

    public static final String VALID_TOKEN_ERROR = "Unable to find a valid token for the user.";

    public static final String RATE_LIMIT_ERROR_MESSAGE = "Rate limit exceeded. Please try your request again later!";

}
