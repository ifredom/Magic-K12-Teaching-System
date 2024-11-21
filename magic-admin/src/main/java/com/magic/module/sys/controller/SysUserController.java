package com.magic.module.sys.controller;

/*
查询用户新增、修改、删除、查询等操作的controller
 */

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magic.framework.aop.repeatedSubmit.RepeatSubmitLimit;
import com.magic.framework.cache.MagicLocalCache;
import com.magic.framework.constant.CacheConstants;
import com.magic.framework.lock.api.ILock;
import com.magic.framework.lock.api.Locker;
import com.magic.framework.model.AjaxResult;
import com.magic.module.sys.entity.SysUserEntity;
import com.magic.module.sys.mapper.SysUserMapper;
import com.magic.module.sys.model.UserAndOrderModel;
import com.magic.module.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户管理")
public class SysUserController {
    private MagicLocalCache<Integer> cache = new MagicLocalCache<Integer>();

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ILock mysqlLock;

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSource;


    private Locker locker;

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    @ApiOperation(value = "hello", notes = "hello")
    public String getHello1() {
            return "hello1";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ApiOperation(value = "hello", notes = "hello")
    public String getHello() {
        String sql = "SELECT * FROM sys_user";
        RowMapper<SysUserEntity> rowMapper = new BeanPropertyRowMapper<>(SysUserEntity.class);
        List<SysUserEntity> list = new JdbcTemplate(dataSource).query(sql, rowMapper);
        System.out.println(list.size());
        for (SysUserEntity user : list) {
            System.out.println(user);
        }

        UserAndOrderModel userAndOrderModel = new UserAndOrderModel();
        userAndOrderModel.setId(4L);
        userAndOrderModel.setUsername("user3");


        SysUserEntity u =  sysUserService.getUserByName(userAndOrderModel);
        System.out.println(u);

        return u.toString();
    }



    @RequestMapping(value = "/getlock", method = RequestMethod.GET)
    @ApiOperation(value = "获取Mysql锁", notes = "获取Mysql锁信息")
    public String getUserInfo() {
        System.out.println("获取Mysql锁");
        locker = mysqlLock.acquireLock("test", Duration.ofSeconds(5));
        return "获取Mysql锁";
    }
    @RequestMapping(value = "/releaselock", method = RequestMethod.GET)
    @ApiOperation(value = "释放Mysql锁", notes = "释放Mysql锁信息")
    public String releaseUserInfo() {
        boolean released = mysqlLock.releaseLock(locker);
        System.out.println("释放Mysql锁：" + released);
        return "释放Mysql锁";
    }

    @RequestMapping(value = "/orderRepeatSubmit", method = RequestMethod.POST)
    @ApiOperation(value = "订单重复提交", notes = "根据参数orderId和userId判断订单是否重复提交")
    @RepeatSubmitLimit(businessKey = "id", businessParam = "#dictType")
    public AjaxResult testOrderRepeatSubmit(@RequestParam String remark, @RequestParam String id, @RequestParam String dictType) {

        if (StrUtil.isEmpty(remark) || StrUtil.isEmpty(id)) {
            return AjaxResult.error("订单ID不能为空");
        }

        System.out.println("从数据库查询用户信息...");
        System.out.println("remark:" + remark + " id:" + id);

        SysUserEntity sysUserEntity = sysUserMapper.selectById(id);

        SysUserEntity user = new SysUserEntity();
        user.setUsername("order");
        user.setRemark("123");
        sysUserService.save(user);

        return AjaxResult.success(sysUserEntity);
    }

    @PostMapping("list")
    @ApiOperation(value = "获取用户列表",notes = "用户分页")
    public AjaxResult getUserList(@RequestParam int pageNum, @RequestParam int pageSize) {

        Page<SysUserEntity> userPage = new Page<>(1, 10);

        LambdaQueryWrapper<SysUserEntity> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        IPage<SysUserEntity> userIPage = sysUserMapper.selectPage(userPage , userLambdaQueryWrapper);
        System.out.println("总页数： "+userIPage.getPages());
        System.out.println("总记录数： "+userIPage.getTotal());

        IPage<SysUserEntity> result = sysUserMapper.selectUserBySex(userPage,2);

        return AjaxResult.success(result);
    }


    @PostMapping("/getUser")
    @ApiOperation(value = "获取用户信息", notes = "表单提交验证")
    public AjaxResult getUser(@Valid @RequestBody UserAndOrderModel userAndOrderModel, BindingResult bindingResult) {
        System.out.println("Received UserAndOrderModel: " + userAndOrderModel);

        // 如果有错误提示信息
        if (bindingResult.hasErrors()) {
            return AjaxResult.error(bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = userAndOrderModel.getId();

        UserAndOrderModel u =  sysUserService.getUnionUserInfo(id);

        System.out.println(u);

        if(u==null){
            return AjaxResult.error(8888,"用户不存在");
        }

        return AjaxResult.success(u);
    }

    @RequestMapping(value = "/getUser2", method = RequestMethod.POST)
    @Cacheable(value = CacheConstants.DICT_DETAILS, key = "#user.id")
    public AjaxResult getUser2(SysUserEntity user) {
        SysUserEntity sysUserEntity =  sysUserService.lambdaQuery().eq(SysUserEntity::getId,user.getId()).one();

        List<SysUserEntity> sysUserEntityList =  sysUserMapper.selectList(null);
        System.out.println(sysUserEntityList.size());


        new SysUserEntity();

        if(sysUserEntity ==null){
            return AjaxResult.error("用户不存在");
        }

        return AjaxResult.success(sysUserEntity);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除字典")
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public AjaxResult deleteUser(@RequestParam int openId, @RequestParam String username) {
        System.out.println("删除用户"+openId);
        sysUserMapper.deleteById(openId);
        return AjaxResult.success("删除成功");
    }

    /**
     *  添加用户
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    public AjaxResult<Void> addUser(@Validated @RequestBody SysUserEntity user){
        user.setUsername("管理员");
        sysUserService.save(user);
        return AjaxResult.success();
    }

    
    /**
     *  添加超级管理员
     */
    @PostMapping("/addSuperAdmin")
    @ApiOperation(value = "添加超级管理员")
    public AjaxResult<Void> addSuperAdmin(@Validated @RequestParam SysUserEntity user){
        user.setUsername("超级管理员");
        sysUserService.save(user);
        return AjaxResult.success();
    }

}
