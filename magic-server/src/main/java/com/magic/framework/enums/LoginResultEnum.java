package com.magic.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 作用:登录结果的枚举类
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/20 11:24
 * @email 1950735817@qq.com
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum {

    SUCCESS(0), // 成功
    BAD_CREDENTIALS(10), // 账号或密码不正确
    USER_DISABLED(20), // 用户被禁用
    CAPTCHA_NOT_FOUND(30), // 图片验证码不存在
    CAPTCHA_CODE_ERROR(31), // 图片验证码不正确

    ;

    /**
     * 结果
     */
    private final Integer result;
}
