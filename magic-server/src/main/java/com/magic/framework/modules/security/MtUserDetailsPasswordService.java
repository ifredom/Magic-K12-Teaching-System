package com.magic.framework.modules.security;

public interface MtUserDetailsPasswordService {
    MtUserDetails changePassword(String username, String newPassword);
}
