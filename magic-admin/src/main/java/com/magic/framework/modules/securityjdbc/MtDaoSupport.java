package com.magic.framework.modules.securityjdbc;

import org.springframework.dao.support.DaoSupport;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
/**
 * Magic Server
 * +----------------------------------------------------------------------
 * 作用: 检查 JDBC配置
 * 1. JDBCTemplate 是否已配置
 * +----------------------------------------------------------------------
 *
 * @author ifredom
 * +----------------------------------------------------------------------
 * @version v0.0.1
 * +----------------------------------------------------------------------
 * @date 2024/10/09 13:37
 * +----------------------------------------------------------------------
 * @email 1950735817@qq.com
 * +----------------------------------------------------------------------
 */
public class MtDaoSupport extends DaoSupport {
    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {

    }
}
