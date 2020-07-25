package ru.dvkombarov.app.security;

public final class Roles {
    public static final String EDITOR = "ROLE_EDITOR";
    public static final String USER = "ROLE_USER";
    public static final String USER_READ_ONLY = "ROLE_USER_READ_ONLY";

    private Roles() {
        throw new UnsupportedOperationException();
    }
}
