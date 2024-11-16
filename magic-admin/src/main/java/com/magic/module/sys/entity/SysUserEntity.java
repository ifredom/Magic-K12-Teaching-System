package com.magic.module.sys.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("sys_user")
@ApiModel(value = "SysUserEntity对象")
public class SysUserEntity implements Serializable {

    @ApiModelProperty("用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("盐加密")
    @TableField("salt")
    private String salt;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("用户性别")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("头像地址")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("部门ID")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty("岗位编号数组")
    @TableField("post_ids")
    private String postIds;

    @ApiModelProperty("用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("帐号状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("是否删除（0代表存在1代表删除）")
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("最后登录IP")
    @TableField("last_ip")
    private String lastIp;

    @ApiModelProperty("最后登录时间")
    @TableField("last_date")
    private Date lastDate;


    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("创建人")
    @TableField("creator")
    private String creator;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("更新人")
    @TableField("updater")
    private String updater;

    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    @TableField("dataScope")
    private Integer dataScope;

    @ApiModelProperty("租户编号")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("位置")
    @TableField("position")
    @NotBlank(message = "位置不能为空")
    @Size(min = 3, max = 20, message = "位置长度必须在3到20之间")
    private String position;

    @ApiModelProperty("工资")
    @TableField("salary")
    private Long salary;

}
