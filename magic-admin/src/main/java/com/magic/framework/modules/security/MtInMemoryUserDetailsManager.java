package com.magic.framework.modules.security;


import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class MtInMemoryUserDetailsManager implements MtUserDetailsManager, MtUserDetailsService{

    // 定义一个hashmap来存储用户信息
    private Map<String,MtMutableUserDetail> users = new HashMap<>();
    @Override
    public void createUser(MtUserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "用户不存在");
        this.users.put(user.getUsername().toLowerCase(), new MtMutableUser(user));
    }

    @Override
    public void updateUser(MtUserDetails user) {
        Assert.isTrue(userExists(user.getUsername()), "用户应该存在");
        this.users.put(user.getUsername().toLowerCase(), new MtMutableUser(user));
    }

    @Override
    public void deleteUser(String username) {
        this.users.remove(username.toLowerCase());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO: 这里实际上需要先实现  SecurityContextHolder 用来存储用户信息。临时，先创建一个用户
        MtUserDetails user = createTemporaryUser();
    }

    @Override
    public boolean userExists(String username) {
        return this.users.containsKey(username.toLowerCase());
    }

    @Override
    public MtUserDetails loadUserByUsername(String username) throws MtUsernameNotFoundException {
        MtUserDetails user = this.users.get(username.toLowerCase());
        if (user == null) {
            throw new MtUsernameNotFoundException(username);
        }
        return new MtUser(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    private MtUserDetails createTemporaryUser() {
        // TODO: 临时创建一个用户
        // 创建用户权限
        MtGrantedAuthority authority = new MtWhiteListGrantedAuthority("ROLE_ADMIN");
        // 创建用户
        MtUserDetails user = new MtWhiteListMtUserDetails("admin", "123456", authority);
        return user;
    }


}
