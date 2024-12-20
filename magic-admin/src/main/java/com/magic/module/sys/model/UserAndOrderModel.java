package com.magic.module.sys.model;

import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserAndOrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "userId不能为空")
    private Long id;
    @NotNull(message = "username不能为空")
    private String username;
    @NotNull(message = "password不能为空")
    private String password;
}
