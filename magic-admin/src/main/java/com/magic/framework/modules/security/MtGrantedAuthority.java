package com.magic.framework.modules.security;

import java.io.Serializable;

public interface MtGrantedAuthority extends Serializable {
    String getAuthority();
}
