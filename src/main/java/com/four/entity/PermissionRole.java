package com.four.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission_role")
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    private Integer roleId;

}
