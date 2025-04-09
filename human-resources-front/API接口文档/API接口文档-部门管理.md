# 部门管理模块接口文档

## 基础信息

- 基础URL: `/api`
- 请求头: Content-Type: application/json
- 响应格式: JSON

## 通用响应格式

```json
{
  "code": 200,       // 状态码，200表示成功，非200表示失败
  "message": "操作成功", // 提示信息
  "data": {}        // 响应数据，具体格式根据接口而定
}
```

## 接口列表

### 1. 获取部门列表

- **接口描述**: 获取指定公司的所有部门列表
- **请求方法**: GET
- **请求URL**: `/department/list/{companyId}`
- **路径参数**: 

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| companyId | Long | 是 | 企业ID |

- **响应示例**:

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "总部",
      "code": "HQ",
      "companyId": 1,
      "leaderId": 1,
      "leaderName": "张三",
      "status": 1
    },
    {
      "id": 2,
      "name": "人力资源部",
      "code": "HR",
      "companyId": 1,
      "leaderId": 2,
      "leaderName": "李四",
      "status": 1
    }
  ]
}
```

### 2. 获取部门详情

- **接口描述**: 根据ID获取部门详情
- **请求方法**: GET
- **请求URL**: `/department/{id}`
- **路径参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| id | Long | 是 | 部门ID |

- **响应示例**:

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "name": "总部",
    "code": "HQ",
    "companyId": 1,
    "leaderId": 1,
    "status": 1
  }
}
```

### 3. 新增部门

- **接口描述**: 新增部门
- **请求方法**: POST
- **请求URL**: `/department`
- **请求参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| name | String | 是 | 部门名称，最大长度100 |
| code | String | 是 | 部门编码，最大长度50，唯一 |
| companyId | Long | 是 | 所属企业ID |
| leaderId | Long | 否 | 部门负责人ID |
| status | Integer | 是 | 状态，1-启用，0-禁用 |

- **请求示例**:

```json
{
  "name": "新部门",
  "code": "NEW-DEPT",
  "companyId": 1,
  "leaderId": 3,
  "status": 1
}
```

- **响应示例**:

```json
{
  "code": 200,
  "message": "新增成功",
  "data": {
    "id": 3
  }
}
```

### 4. 更新部门

- **接口描述**: 更新部门信息
- **请求方法**: PUT
- **请求URL**: `/department/{id}`
- **路径参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| id | Long | 是 | 部门ID |

- **请求参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| name | String | 是 | 部门名称，最大长度100 |
| code | String | 是 | 部门编码，最大长度50，唯一 |
| companyId | Long | 是 | 所属企业ID |
| leaderId | Long | 否 | 部门负责人ID |
| status | Integer | 是 | 状态，1-启用，0-禁用 |

- **请求示例**:

```json
{
  "name": "更新后的部门名称",
  "code": "UPDATED-DEPT",
  "companyId": 1,
  "leaderId": 5,
  "status": 1
}
```

- **响应示例**:

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

### 5. 删除部门

- **接口描述**: 根据ID删除部门
- **请求方法**: DELETE
- **请求URL**: `/department/{id}`
- **路径参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| id | Long | 是 | 部门ID |

- **响应示例**:

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

### 6. 更新部门状态

- **接口描述**: 更新部门状态（启用/禁用）
- **请求方法**: PUT
- **请求URL**: `/department/{id}/status`
- **路径参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| id | Long | 是 | 部门ID |

- **请求参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| status | Integer | 是 | 状态，1-启用，0-禁用 |

- **请求示例**:

```json
{
  "status": 0
}
```

- **响应示例**:

```json
{
  "code": 200,
  "message": "状态更新成功",
  "data": null
}
```

### 7. 获取公司下简单员工列表

- **接口描述**: 获取指定公司下的简单员工列表（用于选择部门负责人）
- **请求方法**: GET
- **请求URL**: `/employee/simple/company/{companyId}`
- **路径参数**:

| 参数名 | 类型 | 是否必须 | 描述 |
| ------ | ---- | -------- | ---- |
| companyId | Long | 是 | 企业ID |

- **响应示例**:

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "张三"
    },
    {
      "id": 2,
      "name": "李四"
    }
  ]
}
```

## 数据库表结构

```sql
CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    name VARCHAR(100) NOT NULL COMMENT '部门名称',
    code VARCHAR(50) UNIQUE COMMENT '部门编码',
    company_id BIGINT COMMENT '所属企业ID',
    leader_id BIGINT DEFAULT NULL COMMENT '部门负责人ID（员工）',
    status TINYINT DEFAULT '1' COMMENT '部门状态(1启用/0禁用)',
    FOREIGN KEY (company_id) REFERENCES company(id)
) COMMENT='部门信息表';
```