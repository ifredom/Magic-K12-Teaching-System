package com.magic.module.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magic.module.sys.entity.SysUserEntity;
import com.magic.module.sys.model.UserAndOrderModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper  extends BaseMapper<SysUserEntity> {

    UserAndOrderModel getUnionUserInfo(long userId);

    SysUserEntity getUserByName(UserAndOrderModel userAndOrderModel);

    IPage<SysUserEntity> selectUserBySex(Page<?> page, int sex);
}
