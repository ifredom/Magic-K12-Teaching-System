package com.magic.framework.modules.security;

/**
 * 作用： 接口名称为什么命名为Detail,就是为了表明Mutable的主要作用。在具体时实现类 MtMutableUser中 得到了体现
 *
*/
public interface MtMutableUserDetail extends MtUserDetails {
    void setPassword(String password);
}
