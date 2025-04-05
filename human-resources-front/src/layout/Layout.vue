<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="1"
          class="el-menu-vertical"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item index="1" @click="$router.push('/company')">
            <el-icon><icon-menu /></el-icon>
            <span>企业管理</span>
          </el-menu-item>
          <el-menu-item index="2" @click="$router.push('/organization')">
            <el-icon><icon-menu /></el-icon>
            <span>组织管理</span>
          </el-menu-item>
          <el-menu-item index="3" @click="$router.push('/department')">
            <el-icon><icon-menu /></el-icon>
            <span>部门管理</span>
          </el-menu-item>
          <el-menu-item index="4" @click="$router.push('/position')">
            <el-icon><icon-menu /></el-icon>
            <span>职位管理</span>
          </el-menu-item>
          <el-menu-item index="5" @click="$router.push('/employee')">
            <el-icon><icon-menu /></el-icon>
            <span>员工管理</span>
          </el-menu-item>
          <el-menu-item index="6" @click="$router.push('/onboarding')">
            <el-icon><icon-menu /></el-icon>
            <span>入职管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="text-align: center; font-size: 20px; line-height: 60px;">
          人力资源管理系统
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { Menu as IconMenu } from '@element-plus/icons-vue'
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
  height: 100vh;
}

.el-menu-vertical {
  height: 100%;
}
</style>