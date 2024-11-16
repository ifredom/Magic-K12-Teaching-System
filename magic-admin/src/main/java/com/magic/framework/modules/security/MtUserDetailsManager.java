package com.magic.framework.modules.security;

public interface MtUserDetailsManager extends MtUserDetailsService {

    void createUser(MtUserDetails user);

    void updateUser(MtUserDetails user);

    void deleteUser(String username);

    void changePassword(String oldPassword, String newPassword);

    boolean userExists(String username);
}
