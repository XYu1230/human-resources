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

-- 组织架构表
CREATE TABLE organization (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '组织ID',
    name VARCHAR(100) NOT NULL COMMENT '组织名称',
    parent_id BIGINT DEFAULT NULL COMMENT '上级组织ID',
    level INT DEFAULT 1 COMMENT '组织层级',
    company_id BIGINT NOT NULL COMMENT '所属企业ID',
    status TINYINT DEFAULT '1' COMMENT '组织状态(1启用/0禁用)',
    FOREIGN KEY (company_id) REFERENCES company(id) 
) COMMENT='组织架构表';

-- 部门表
CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    name VARCHAR(100) NOT NULL COMMENT '部门名称',
    code VARCHAR(50) UNIQUE COMMENT '部门编码',
    company_id BIGINT COMMENT '所属企业ID',
    leader_id BIGINT DEFAULT NULL COMMENT '部门负责人ID（员工）',
    status TINYINT DEFAULT '1' COMMENT '部门状态(1启用/0禁用)',
    FOREIGN KEY (company_id) REFERENCES company(id)
) COMMENT='部门信息表';

-- 职位表
CREATE TABLE position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职位ID',
    name VARCHAR(100) NOT NULL COMMENT '职位名称',
    code VARCHAR(50) UNIQUE COMMENT '职位编码',
    description VARCHAR(255) COMMENT '职位描述',
    department_id BIGINT NOT NULL COMMENT '所属部门ID',
    FOREIGN KEY (department_id) REFERENCES department(id)
) COMMENT='职位信息表';

-- 测试数据(用于测试)
INSERT INTO company (name, code, address, contact_person, contact_phone, status) VALUES
('星云科技有限公司', 'XY001', '北京市海淀区中关村大街27号', '王经理', '13800138000', 1),
('恒源国际集团', 'HY002', '上海市浦东新区世纪大道188号', '李总', '13900139000', 1),
('博思软件股份公司', 'BS003', '广州市天河区软件路88号', '张小姐', '13700137000', 0);

INSERT INTO organization (name, parent_id, level, company_id, status) VALUES
('总部', NULL, 1, 1, 1),
('技术中心', 1, 2, 1, 1),
('运营中心', 1, 2, 1, 1),
('研发部', 2, 3, 1, 1),
('测试部', 2, 3, 1, 1),
('人事部', 3, 3, 1, 1);

INSERT INTO department (name, code, company_id, leader_id, status) VALUES
('研发一组', 'RD101', 1,  NULL, 1),
('研发二组', 'RD102', 1,  NULL, 1),
('功能测试组', 'QA201', 1,  NULL, 1),
('自动化测试组', 'QA202', 1, NULL, 1),
('招聘组', 'HR301', 1, NULL,1),
('员工关系组', 'HR302', 1,NULL, 1);

INSERT INTO position (name, code, description, department_id) VALUES
('Java开发工程师', 'DEV001', '负责后台服务开发与维护', 1),
('前端工程师', 'FE001', '负责前端页面开发与交互', 2),
('测试工程师', 'QA001', '负责功能与接口测试', 3),
('自动化测试工程师', 'QA002', '编写与维护自动化测试脚本', 4),
('招聘专员', 'HR001', '负责人才招聘与面试安排', 5),
('员工关系专员', 'HR002', '负责员工关系管理与入转离流程', 6);

