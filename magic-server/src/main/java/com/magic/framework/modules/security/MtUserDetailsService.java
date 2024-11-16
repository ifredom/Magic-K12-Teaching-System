package com.magic.framework.modules.security;

/**
 * 作用： 职责为通过用户名获取到用户
 * 为什么是通过用户名？
 * 因为security抽象中，必要的私有信息只有两个  username 和 password
 *
 * 这里获取的是用户 所以是 UserDetails, 而不是 MuTableUser
 */

public interface MtUserDetailsService {
    MtUserDetails loadUserByUsername(String username) throws MtUsernameNotFoundException;
}
