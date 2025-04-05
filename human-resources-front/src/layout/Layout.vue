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
          <el-menu-item index="1">
            <el-icon><icon-menu /></el-icon>
            <span>企业管理</span>
          </el-menu-item>
          <!-- 其他模块菜单项可以在这里添加 -->
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