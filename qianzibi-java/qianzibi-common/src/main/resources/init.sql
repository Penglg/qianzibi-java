CREATE TABLE `sys_account` (
                               `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                               `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
                               `user_name` varchar (20) DEFAULT NULL COMMENT '用户名',
                               `password` varchar(50) DEFAULT NULL COMMENT '密码',
                               `position` varchar(150) DEFAULT NULL COMMENT '职位',
                               `status` int(11) DEFAULT '1' COMMENT '状态0:禁用 1:启用',
                               `roles`  varchar(100) DEFAULT NULL COMMENT '用户拥有的角色多个用逗号隔开',
                               `create_time`datetime DEFAULT NULL COMMENT '创建时间',
                               PRIMARY KEY (`user_id`),
                               UNIQUE KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1000011 DEFAULT CHARSET=utf8mb4 COMMENT='账号信息';

CREATE TABLE `sys_menu` (
                            `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'menu_id，自增主键',
                            `menu_name` varchar (32) NOT NULL COMMENT '菜单名',
                            `menu_type` int(11) NOT NULL DEFAULT '0' COMMENT '菜单类型 0：菜单. 1:按钮.',
                            `menu_url`  varchar (256) DEFAULT NULL COMMENT '菜单跳转到的地址',
                            `p_id` int(11) NOT NULL COMMENT '上级菜单ID',
                            `sort` tinyint(4) DEFAULT '0' COMMENT '菜单排序',
                            `permission_code` varchar(50) DEFAULT NULL COMMENT '权限编码',
                            `icon` varchar(50) DEFAULT NULL COMMENT '图标',
                            PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1055 DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE TABLE `sys_role` (
                            `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                            `role_name` varchar(100) NOT NULL COMMENT '角色名称',
                            `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `last_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                            PRIMARY KEY (`role_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

CREATE TABLE `sys_role_2_menu` (
                                   `role_id` int(11) NOT NULL COMMENT '角色ID',
                                   `menu_id` int(11) NOT NULL COMMENT '菜单ID',
                                   `check_type` tinyint(1) DEFAULT NULL COMMENT '0:半选. 1:全选',
                                   PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色对应的菜单权限表';

CREATE TABLE `category` (
                            `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                            `category_name` varchar(30) DEFAULT NULL COMMENT '名称',
                            `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
                            `icon_path` varchar(150) DEFAULT NULL COMMENT '图标',
                            `bg_color` varchar(10) DEFAULT NULL COMMENT '背景颜色',
                            `type` tinyint(1) DEFAULT NULL COMMENT '0:问题分类; 1:考题分类; 2:问题分类和考题分类',
                            PRIMARY KEY (`category_id`)
) ENGINE=INNODB AUTO_INCREMENT=10014 DEFAULT CHARSET=utf8mb4 COMMENT='分类';

-- DROP TABLE IF EXISTS `question_info`;

-- 题目表
CREATE TABLE `question_info` (
                                 `question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `title` varchar(150) DEFAULT NULL COMMENT '标题',
                                 `category_id` int(11) DEFAULT NULL COMMENT '分类名称',
                                 `difficulty_level` tinyint(1) DEFAULT NULL COMMENT '难度',
                                 `question` text COMMENT'问题描述',
                                 `answer_analysis` text COMMENT '回答解释',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `status` tinyint(4) DEFAULT '0' COMMENT '0:未发布; 1:已发布',
                                 `create_user_id` varchar(15) DEFAULT NULL COMMENT '用户ID',
                                 `create_user_name` varchar(30) DEFAULT NULL COMMENT '姓名' ,
                                 `read_count` int(11) DEFAULT '0' COMMENT '阅读数量',
                                 `collect_count` int(11) DEFAULT '0' COMMENT'收藏数',
                                 `post_user_type` tinyint(1) DEFAULT '0' COMMENT '0:内部; 1:外部投稿',
                                 PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='问题';

--
CREATE TABLE `exam_question`(
                                `question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题ID',
                                `title` varchar(150) DEFAULT NULL COMMENT '标题',
                                `category_id` int(11) DEFAULT NULL COMMENT '分类ID',
                                `category_name` varchar(30) DEFAULT NULL COMMENT '分类名称',
                                `difficulty_level` tinyint(1) DEFAULT NULL COMMENT '难度',
                                `question_type` tinyint(4) DEFAULT NULL COMMENT '问题类型0:判断 1:单选题 2:多选',
                                `question` text COMMENT '问题描述',
                                `question_answer` varchar(20) DEFAULT NULL COMMENT '答案',
                                `answer_analysis` text COMMENT '回答解释',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `status` tinyint(4) DEFAULT '0' COMMENT '0:未发布 1:已发布',
                                `create_user_id` varchar(15) DEFAULT NULL COMMENT '用户ID',
                                `create_user_name` varchar(30) DEFAULT NULL COMMENT '姓名',
                                `post_user_type` tinyint(1) DEFAULT '0' COMMENT '0:内部 1:外部投稿',
                                PRIMARY KEY (`question_id`)
) ENGINE=InNoDB AUTO_INCREMENT=10052 DEFAULT CHARSET=utf8mb4 COMMENT='考试题目';

CREATE TABLE `exam_question_item` (
                                      `item_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '选项ID',
                                      `question_id` int(11) NOT NULL COMMENT '问题ID',
                                      `title` varchar(200) DEFAULT NULL COMMENT '标题',
                                      `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
                                      PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `share_info` (
                              `share_id` int (11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                              `title` varchar(150) DEFAULT NULL COMMENT'标题',
                              `cover_type` tinyint (4) DEFAULT NULL COMMENT'0:无封面 1:横幅 2:小图标',
                              `cover_path`varchar(100)DEFAULT NULL COMMENT'封面路径',
                              `content` text COMMENT '内容',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `status` tinyint(4) DEFAULT '0' COMMENT '0:未发布 1:已发布',
                              `create_user_id` varchar(15) DEFAULT NULL COMMENT '用户ID',
                              `create_user_name` varchar(30) DEFAULT NULL COMMENT '姓名',
                              `read_count` int(11) DEFAULT '0' COMMENT '阐读数量',
                              `collect_count` int(11) DEFAULT '0' COMMENT '收藏数',
                              `post_user_type` tinyint(1) DEFAULT '0' COMMENT '0:内部1:外部投稿',
                              PRIMARY KEY (`share_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8mb4 COMMENT='文章';


-- App管理
CREATE TABLE `app_device` (
                              `device_id` varchar(32) NOT NULL COMMENT '设备ID',
                              `device_brand` varchar(30) DEFAULT NULL COMMENT '手机品牌',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `last_use_time` datetime DEFAULT NULL COMMENT '最后使用时间',
                              `ip` varchar(128) DEFAULT NULL COMMENT 'ip',
                              PRIMARY KEY (`device_id`),
                              KEY `index_brand` (`device_brand`),
                              KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息';

CREATE TABLE `app_user_info` (
                                 `user_id` varchar(15) NOT NULL COMMENT '用户ID',
                                 `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
                                 `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
                                 `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                                 `password` varchar(32) DEFAULT NULL COMMENT '密码',
                                 `sex` tinyint(4) DEFAULT NULL COMMENT '性别0:女 1:男',
                                 `join_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
                                 `last_use_device_id` varchar(32) DEFAULT NULL COMMENT '最后使用的设备ID',
                                 `last_use_device_brand` varchar(30) DEFAULT NULL COMMENT '手机品牌',
                                 `last_login_ip` varchar(128) DEFAULT NULL COMMENT '最后登录IP',
                                 `status` tinyint(1) DEFAULT NULL COMMENT '0:禁用1:正常',
                                 PRIMARY KEY (`user_id`),
                                 UNIQUE KEY `idx_key_email` (`email`) USING BTREE,
                                 KEY `idx_join_time` (`join_time`),
                                 KEY `idx_last_login_time` (`last_login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `app_carousel` (
                                `carousel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT'自增ID',
                                `img_path` varchar(150) DEFAULT NULL COMMENT '图片',
                                `object_type` tinyint(4) DEFAULT NULL COMMENT '0:分享 1:问题 2:考题 3:外部',
                                `object_id` varchar(20) DEFAULT NULL COMMENT '文章ID',
                                `outer_link` varchar(200)DEFAULT NULL COMMENT'外部连接',
                                `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
                                PRIMARY KEY (`carousel_id`)
)ENGINE=InnODB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='app轮播';

CREATE TABLE `app_feedback` (
                                `feedback_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                `user_id` varchar(15) NOT NULL COMMENT '用户ID',
                                `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
                                `content` varchar(500) DEFAULT NULL COMMENT '反馈内容',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `p_feedback_id` int(11) DEFAULT NULL COMMENT '父级ID',
                                `status` tinyint(4) DEFAULT NULL COMMENT '状态 0:未回复 1:已回复',
                                `send_type` tinyint(4) DEFAULT NULL COMMENT '0:访客 1:管理员',
                                `client_last_send_time` datetime DEFAULT NULL COMMENT '访客最后发送时间',
                                PRIMARY KEY (`feedback_id`),
                                UNIQUE KEY `idx_key`(`feedback_id`,`user_id`)
) ENGINE=InnODB AUTO_INCREMENT=10021 DEFAULT CHARSET=utf8mb4 COMMENT='问题反馈';

CREATE TABLE `app_update` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                              `version` varchar(10) DEFAULT NULL COMMENT '版本号',
                              `update_desc` varchar(500) DEFAULT NULL COMMENT '更新描述',
                              `update_type` tinyint(1)DEFAULT NULL COMMENT '更新类型 0:全更新 1:局部热更新',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `status` tinyint(4) DEFAULT NULL COMMENT '0:未发布 1:灰度发布 2:全网发布',
                              `grayscale_device`varchar(1000) DEFAULT NULL COMMENT '灰度设备ID',
                              PRIMARY KEY (`id`)
) ENGINE=InnODB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='app发布';