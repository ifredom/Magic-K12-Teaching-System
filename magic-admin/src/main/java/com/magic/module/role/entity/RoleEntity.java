package com.magic.module.role.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author 1950735817@qq.com
 * @since 2024-09-07
 */

@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "RoleEntity对象", description = "")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("角色权限字符串")
    @TableField("code")
    private String code;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;


//     权限验证测试字段======================
    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    @TableField("data_scope")
    @NotNull("权限范围不能为空")
    private Integer dataScope;

    @ApiModelProperty("数据范围(指定部门数组)")
    @TableField("data_scope_dept_ids")
    private String dataScopeDeptIds;

    @ApiModelProperty("角色状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("角色类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("创建者")
    @TableField("creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新者")
    @TableField("updater")
    private String updater;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("是否删除")
    @TableField("deleted")
    private Boolean deleted;

    @ApiModelProperty("租户编号")
    @TableField("tenant_id")
    private Long tenantId;


}
