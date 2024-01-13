package com.heyqing.disk.server.modules.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户文件信息表
 * @TableName hey_disk_user_file
 */
@TableName(value ="hey_disk_user_file")
@Data
public class HeyDiskUserFile implements Serializable {
    /**
     * 文件记录id
     */
    @TableId(value = "file_id")
    private Long fileId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 上级文件夹id，顶级文件夹为0
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 真实文件id
     */
    @TableField(value = "real_file_id")
    private Long realFileId;

    /**
     * 文件名
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 是否为文件夹（0否1是）
     */
    @TableField(value = "folder_flag")
    private Integer folderFlag;

    /**
     * 文件大小展示字符
     */
    @TableField(value = "file_size_desc")
    private String fileSizeDesc;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private Integer fileType;

    /**
     * 删除标识（0否1是）
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user")
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}