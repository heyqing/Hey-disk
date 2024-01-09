set names utf8mb4;
set
foreign_key_checks = 0;


-- ---------------------------------
-- Table structure for hey_disk_file
-- ---------------------------------
drop table if exists `hey_disk_file`;
create table `hey_disk_file`
(
    `file_id`   bigint   not     null    comment '文件id',
    `filename`  varchar(255)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件名称',
    `real_path`  varchar(700)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件物理路径',
    `file_size`  varchar(255)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件实际大小',
    `file_size_desc`  varchar(255)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件大小展示字符',
    `file_suffix`  varchar(255)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件后缀',
    `file_preview_content_type`  varchar(255)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件预览的响应头Content-Type的值',
    `identifier`  varchar(255)    character set utf8mb4 collate utf8mb4_bin not null default '' comment '文件唯一标识',
    `create_user` bigint     not null  comment '创建人',
    `create_time`  datetime     not null default current_timestamp comment '创建时间',
    primary key (`file_id`) using btree
) engine = InnoDB
  default  charset  = utf8mb4
  collate = utf8mb4_bin
  row_format = dynamic comment = '物理文件信息表';


-- ---------------------------------
-- Table structure for hey_disk_share
-- ---------------------------------

drop table if exists `hey_disk_share`;
create table `hey_disk_share`
(
    `share_id`  bigint(0) not null comment '分享id',
    `share_name` varchar(255) character set utf8mb4 collate utf8mb4_bin not null  default '' comment '分享名称',
    `share_type` tinyint(1) not null default 0 comment '分享类型（0 有提取码）',
    `share_day_type` tinyint(1) not null default 0 comment '分享类型（0 永久有效 1 7天有效 2 30天有效）',
    `share_day` tinyint(1) not null default 0 comment '分享有效天数（0 永久有效）',
    `share_end_time` datetime(0) not null default current_timestamp(0) comment '分享结束时间',
    `share_url` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '分享链接地址',
    `share_code` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '分享提取码',
    `share_status` tinyint(1) not null default 0 comment '分享状态（0 正常 1 有文件被删除）',
    `create_user` bigint(0) not null comment '分享创建人',
    `create_time` datetime(0) not null default current_timestamp(0) comment '创建时间',
    primary key (`share_id`) using btree ,
    unique index `uk_create_user_time` (`create_user`,`create_time`) using btree comment '创建人，创建时间唯一索引'
)engine = InnoDB
character set utf8mb4
collate = utf8mb4_bin
comment '用户分享表' row_format = dynamic ;

-- ---------------------------------
-- Table structure for hey_disk_share_file
-- ---------------------------------

drop table if exists `hey_disk_share_file`;
create table `hey_disk_share_file`
(
    `id` bigint(0) not null auto_increment comment '主键',
    `share_id` bigint(0) not null comment '分享id',
    `file_id` bigint(0) not null comment '文件记录id',
    `create_user` bigint(0) not null comment '分享创建人',
    `create_time` datetime(0) not null default current_timestamp(0) comment '创建时间',
    primary key (`id`) using btree ,
    unique index `uk_share_id_file_id` (`share_id`,`file_id`) using btree comment '分享id，文件id联合唯一索引'
)engine InnoDB
auto_increment = 1
character set utf8mb4
collate = utf8mb4_bin comment '用户分享文件表'
row_format = dynamic ;

-- ---------------------------------
-- Table structure for hey_disk_user
-- ---------------------------------

drop table if exists `hey_disk_uesr`;
create table `hey_disk_user`
(
    `user_id` bigint(0) not null comment '用户id',
    `username` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '用户名',
    `password` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '密码',
    `salt` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '随机盐值',
    `question` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '密保问题',
    `answer` varchar(255) character set utf8mb4 collate utf8mb4_bin not null default '' comment '密保答案',
    `create_time` datetime(0)  not null default current_timestamp(0) comment '创建时间',
    `update_time` datetime(0)  not null default current_timestamp(0) comment '更新时间',
    primary key (`user_id`) using btree ,
    unique index `uk_username` (`username`) using btree comment '用户名唯一索引'
) engine = InnoDB
character set utf8mb4
collate utf8mb4_bin comment '用户信息表'
row_format = dynamic ;

-- ---------------------------------
-- Table structure for hey_disk_user_file
-- ---------------------------------

drop table if exists `hey_disk_user_file`;
create table `hey_disk_user_file`
(
    `file_id` bigint(20) not null comment '文件记录id',
    `user_id` bigint(20) not null comment '用户id',
    `parent_id` bigint(20) not null comment '上级文件夹id，顶级文件夹为0',
    `real_file_id` bigint(20) not null default '0' comment '真实文件id',
    `filename` varchar(255) collate utf8mb4_bin not null default '' comment '文件名',
    `folder_flag` tinyint(1)  not null default 0 comment '是否为文件夹（0否1是）',
    `file_size_desc` varchar(255) collate utf8mb4_bin  not null default '--' comment '文件大小展示字符',
    `file_type` tinyint(1)    not null default 0 comment '文件类型',
    `del_flag` tinyint(1)    not null default 0 comment '删除标识（0否1是）',
    `create_user` bigint(20)    not null  comment '创建人',
    `create_time` datetime    not null default current_timestamp on update current_timestamp comment '创建时间',
    `update_user` bigint(20) not null comment '更新人',
    `update_time` datetime not null default current_timestamp comment '更新时间',
    primary key (`file_id`) using btree ,
    key `index_file_list` (`user_id`,`del_flag`,`parent_id`,`file_type`,`file_id`,`filename`,`folder_flag`,`file_size_desc`,`create_time`,`update_time`)
        using btree comment '查询文件列表索引'
)engine = InnoDB
default charset =utf8mb4
collate =utf8mb4_bin
row_format = dynamic comment '用户文件信息表';


-- ---------------------------------
-- Table structure for hey_disk_user_search_history
-- ---------------------------------

drop table if exists `hey_disk_user_search_history`;
create table `hey_disk_user_search_history`
(
    `id` bigint(0) not null auto_increment comment '主键',
    `user_id` bigint(0) not null  comment '用户id',
    `search_content` varchar(255) character set utf8mb4 collate utf8mb4_bin not null  default '' comment '搜索文案',
    `create_time` datetime(0) not null default current_timestamp(0) comment '创建时间',
    `update_time` datetime(0) not null default current_timestamp(0) comment '更新时间',
    primary key (`id`) using btree ,
    unique index `uk_user_id_search_content_update_time` (`user_id`,`search_content`,`update_time`) using btree comment '用户id搜索内容和更新时间唯一索引',
    unique index `uk_user_id_search_content` (`user_id`,`search_content`) using btree comment '用户id，搜索内容唯一索引'
)engine = InnoDB
auto_increment = 1 character set = utf8mb4 collate = utf8mb4_bin comment '用户搜索历史表' row_format = dynamic ;



-- ---------------------------------
-- Table structure for hey_disk_file_chunk
-- ---------------------------------

drop table if exists `hey_disk_file_chunk`;
create table `hey_disk_file_chunk`
(
    `id` bigint not null auto_increment comment '主键',
    `identifier` varchar(255) collate utf8mb4_bin not null default '' comment '文件唯一标识',
    `real_path` varchar(700) collate utf8mb4_bin not null default '' comment '文件真实存储路径',
    `chunk_number` int  not null default '0' comment '分片编号',
    `expiration_time` datetime  not null default current_timestamp comment '过期时间',
    `create_user` bigint  not null  comment '创建人',
    `create_time` datetime  not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `uk_identifier_chunk_number_create_user` (`identifier`,`chunk_number`,`create_user`) using btree
        comment '文件唯一标识，分片编号和用户id的唯一索引'
)engine = InnoDB
auto_increment = 101
default charset = utf8mb4
collate =  utf8mb4_bin comment = '文件分片信息表';


-- ---------------------------------
-- Table structure for hey_disk_error_log
-- ---------------------------------

drop table if exists `hey_disk_error_log`;
create table `hey_disk_error_log`
(
    `id` bigint not null auto_increment comment '主键id',
    `log_content` varchar(900) collate utf8mb4_bin not null  default '' comment '日志内容',
    `log_status` tinyint default 0 comment '日志状态（0 未处理 1 已处理）',
    `create_user` bigint not null comment '创建人',
    `create_time` datetime not null default current_timestamp comment  '创建时间',
    `update_user` bigint not null comment '更新人',
    `update_time` datetime not null default current_timestamp comment '更新时间',
    primary key (`id`)
)engine = InnoDB
default charset = utf8mb4 collate utf8mb4_bin comment '错误日志表';


set foreign_key_checks = 1;



















