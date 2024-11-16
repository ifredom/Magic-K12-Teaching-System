package com.magic.framework.modules.security;

import java.io.Serializable;
import java.util.Collection;

/**
 * 作用：定义 用户详情信息接口
 * getAuthorities()：返回用户拥有的权限集合。
 * getPassword()：返回用于认证的密码。
 * getUsername()：返回用于认证的用户名。
 * isAccountNonExpired()：指示账户是否未过期。
 * isAccountNonLocked()：指示账户是否未被锁定。
 * isCredentialsNonExpired()：指示用户的凭证（通常是密码）是否未过期。
 * isEnabled()：指示用户是否启用。
 */

public interface MtUserDetails extends Serializable {
    Collection<? extends MtGrantedAuthority> getAuthorities();
    String getUsername();
    String getPassword();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
