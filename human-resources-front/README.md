# 人力资源管理系统 - 前端

## 项目介绍

本项目是人力资源管理系统的前端部分，使用Vue 3 + Vite + Element Plus开发。系统分为十九个模块，包括企业管理、组织架构、部门管理等功能。目前已完成企业管理模块的开发。

## 技术栈

- Vue 3 - 前端框架
- Vite - 构建工具
- Vue Router - 路由管理
- Element Plus - UI组件库
- Axios - HTTP请求库

## 项目结构

```
├── public/             # 静态资源
├── src/                # 源代码
│   ├── api/            # API接口
│   ├── assets/         # 项目资源
│   ├── components/     # 公共组件
│   ├── layout/         # 布局组件
│   ├── router/         # 路由配置
│   ├── views/          # 页面组件
│   ├── App.vue         # 根组件
│   ├── main.js         # 入口文件
│   └── style.css       # 全局样式
├── index.html          # HTML模板
├── package.json        # 项目依赖
├── vite.config.js      # Vite配置
└── API接口文档.md       # API接口文档
```

## 功能模块

### 企业管理

- 企业信息的增删改查
- 企业状态管理（启用/禁用）
- 按企业名称、编码、联系人搜索
- 分页显示企业列表

## 开发指南

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

### 构建生产版本

```bash
npm run build
```

### 预览生产版本

```bash
npm run preview
```

## API接口

详细的API接口文档请参考项目根目录下的`API接口文档.md`文件。

## 开发计划

- [x] 企业管理模块
- [ ] 组织架构模块
- [ ] 部门管理模块
- [ ] 编制管理模块
- [ ] 职位管理模块
- [ ] 职级管理模块
- [ ] 入职管理模块
- [ ] 新人成长模块
- [ ] 转正管理模块
- [ ] 调岗管理模块
- [ ] 离职管理模块
- [ ] 合同管理模块
- [ ] 合同台账模块
- [ ] 档案管理模块
- [ ] 体检管理模块
- [ ] 奖惩管理模块
- [ ] 考核管理模块
- [ ] 通知管理模块
- [ ] 字段管理模块
- [ ] 人事报表及明细报表模块

This template should help get you started developing with Vue 3 in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about IDE Support for Vue in the [Vue Docs Scaling up Guide](https://vuejs.org/guide/scaling-up/tooling.html#ide-support).
