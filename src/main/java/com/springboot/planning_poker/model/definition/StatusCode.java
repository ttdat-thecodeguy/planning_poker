package com.springboot.planning_poker.model.definition;

public class StatusCode {
    public static final String TABLE_NOT_FOUND="Table Not in DB";
    public static final String USER_NOT_FOUND="User not found in DB";
    public static final String ROLE_NOT_FOUND ="Role not found in DB";
    public static final String ISSUE_NOT_FOUND = "Issue not found in DB";
    public static final String GAME_JOINS_NOT_FOUND = "Game deck not found in DB";
    public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found";

    public static final String ROLE_EXISTS = "role is exists. Please choose another one";
    public static final String EMAIL_EXISTS = "Email is exists. Please choose another one";

    public static final String ITEM_INVALID = "Item cant be invalid";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String USER_DISABLED = "USER_DISABLED";
}
