package com.magic.framework.modules.security;

import java.util.Collection;

/**
 * 作用：实现 MtUserDetails 接口，专注于核心职责——管理密码，而其他用户信息的获取则由 delegate 处理。
 *
 *
 * 如果将来需要替换或更改 UserDetails 的实现，delegate 这个名字表明了这个对象可以被替换或重新分配，而不需要修改 MutableUser 类的其他部分。
 */
public class MtMutableUser implements MtMutableUserDetail {

    private static final long serialVersionUID = 1L;

    private final MtUserDetails delegate;

    private String password;

    MtMutableUser( MtUserDetails user){
        this.delegate = user;
        this.password = user.getPassword();
    }
    @Override
    public Collection<? extends MtGrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getUsername() {
        return delegate.getUsername();
    }

    @Override
    public String getPassword() {
        return delegate.getPassword();
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return delegate.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return delegate.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return delegate.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return delegate.isEnabled();
    }
}
