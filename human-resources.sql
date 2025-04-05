-- 人力管理子系统

-- 创建数据库
CREATE database human_resources;

use human_resources;

-- 创建企业表

CREATE TABLE company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '企业ID',
    name VARCHAR(100) NOT NULL COMMENT '企业名称',
    code VARCHAR(50) UNIQUE NOT NULL COMMENT '企业编码',
    address VARCHAR(255) COMMENT '企业地址',
    contact_person VARCHAR(50) COMMENT '联系人姓名',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT COMMENT '企业状态(1启用/0禁用)'
) COMMENT='企业信息表';
