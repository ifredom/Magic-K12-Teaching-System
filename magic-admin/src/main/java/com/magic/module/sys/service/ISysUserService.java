package com.magic.module.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.module.sys.entity.SysUserEntity;
import com.magic.module.sys.model.UserAndOrderModel;

public interface ISysUserService extends IService<SysUserEntity> {

    // 自定义一个复杂查询
    UserAndOrderModel getUnionUserInfo(long id);

    // 模拟复杂分页查询
     IPage<SysUserEntity> selectUserBySex(Page<SysUserEntity> page, Integer sex);


    SysUserEntity getUserByNameAndOrderId(UserAndOrderModel userAndOrderModel);
}
