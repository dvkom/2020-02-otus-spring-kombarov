package ru.dvkombarov.app.security;

public final class Roles {
    private static final String ROLE = "ROLE_";
    public static final String EDITOR = "EDITOR";
    public static final String USER = "USER";
    public static final String USER_READ_ONLY = "USER_READ_ONLY";
    public static final String TECH_SUPPORT = "TECH_SUPPORT";
    public static final String ROLE_EDITOR = ROLE + EDITOR;
    public static final String ROLE_USER = ROLE + USER;
    public static final String ROLE_USER_READ_ONLY = ROLE + USER_READ_ONLY;
    public static final String ROLE_TECH_SUPPORT = ROLE + TECH_SUPPORT;

    private Roles() {
        throw new UnsupportedOperationException();
    }
}
