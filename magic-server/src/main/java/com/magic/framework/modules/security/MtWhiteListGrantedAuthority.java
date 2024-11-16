package com.magic.framework.modules.security;


/**
 * 白名单角色, 用于创建临时用户
 */
public class MtWhiteListGrantedAuthority implements  MtGrantedAuthority{
    private static final long serialVersionUID = 1L;

    private String authority;

    public MtWhiteListGrantedAuthority(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
