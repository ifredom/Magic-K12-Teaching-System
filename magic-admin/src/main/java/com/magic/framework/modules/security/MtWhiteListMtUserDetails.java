package com.magic.framework.modules.security;

import java.util.Collection;
import java.util.List;

public class MtWhiteListMtUserDetails implements MtUserDetails {

    private String username;
    private String password;
    private MtGrantedAuthority authority;

    public MtWhiteListMtUserDetails(String username, String password, MtGrantedAuthority authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }
    @Override
    public Collection<? extends MtGrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
