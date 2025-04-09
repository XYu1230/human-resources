<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="activeIndex"
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
            <span>组织架构</span>
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
          <div class="header-container">
            <div class="title">人力资源管理系统</div>
            <div class="user-info">
              <el-dropdown @command="handleCommand">
                <span class="user-dropdown-link">
                  {{ userName }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { Menu as IconMenu, ArrowDown } from '@element-plus/icons-vue'
import { ref, watch, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const activeIndex = ref('1')

// 获取用户名
const userName = computed(() => {
  const userStr = localStorage.getItem('user')
  if (!userStr) return '用户'
  
  try {
    const user = JSON.parse(userStr)
    return user.realName || user.username || '用户'
  } catch (e) {
    return '用户'
  }
})

// 处理下拉菜单命令
const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      // 清除token和用户信息
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      // 重定向到登录页
      router.push('/login')
    }).catch(() => {})
  }
}

// 根据路由路径设置激活菜单项
const setActiveMenu = (path) => {
  const routeMap = {
    '/company': '1',
    '/organization': '2',
    '/department': '3',
    '/position': '4',
    '/employee': '5',
    '/onboarding': '6'
  }
  activeIndex.value = routeMap[path] || '1'
}

// 初始化和监听路由变化
setActiveMenu(route.path)
watch(() => route.path, (newPath) => {
  setActiveMenu(newPath)
})
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
  padding: 0 20px;
}

.el-aside {
  color: #333;
  height: 100vh;
}

.el-menu-vertical {
  height: 100%;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-info {
  font-size: 14px;
}

.user-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
</style>