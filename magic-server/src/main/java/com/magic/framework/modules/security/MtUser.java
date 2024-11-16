package com.magic.framework.modules.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

public class MtUser implements MtUserDetails {
    private static final long serialVersionUID = 1L;

    private static final Log logger = LogFactory.getLog(MtUser.class);

    private String password;

    private final String username;

    private final Set<MtGrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    public MtUser(String username, String password, Collection<? extends MtGrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public MtUser(String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Collection<? extends MtGrantedAuthority> authorities) {
        this.username = username;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.password = password;
    }

    // TODO: 此方法实现待定
    private static SortedSet<MtGrantedAuthority> sortAuthorities(Collection<? extends MtGrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null MtGrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<MtGrantedAuthority> sortedAuthorities = new TreeSet<>(new MtUser.AuthorityComparator());
        for (MtGrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "MtGrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }
    private static class AuthorityComparator implements Comparator<MtGrantedAuthority>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(MtGrantedAuthority g1, MtGrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set. If the authority is null, it is a custom authority and should
            // precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }

    }

    @Override
    public Collection<MtGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
}
