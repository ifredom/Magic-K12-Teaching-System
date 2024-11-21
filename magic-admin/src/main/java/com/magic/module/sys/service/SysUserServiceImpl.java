package com.magic.module.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.magic.module.sys.entity.SysUserEntity;
import com.magic.module.sys.mapper.SysUserMapper;
import com.magic.module.sys.model.UserAndOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserAndOrderModel getUnionUserInfo(long userId) {

        return sysUserMapper.getUnionUserInfo(userId);

    }

    @Override
    public IPage<SysUserEntity> selectUserBySex(Page<SysUserEntity> page, Integer sex) {
        return sysUserMapper.selectUserBySex(page,sex);
    }

    @Override
    public SysUserEntity getUserByName(UserAndOrderModel userAndOrderModel) {
        return sysUserMapper.getUserByName(userAndOrderModel);
    }


}
