<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">人力资源管理系统</h2>
      <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            prefix-icon="User"
            placeholder="用户名">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            prefix-icon="Lock"
            type="password" 
            placeholder="密码">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            class="login-button" 
            :loading="loading"
            @click="handleLogin">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import userApi from '../../api/user'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)

// 登录表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 处理登录
const handleLogin = () => {
  loginFormRef.value.validate(async (valid) => {
    if (!valid) return
    
    try {
      loading.value = true
      
      // 调用登录API
      const response = await userApi.login(loginForm)
      console.log('Login response:', response)
      
      // 提取 token
      const token = response.data?.token
      console.log('Extracted token:', token)

      if (!token) {
        ElMessage.error('登录失败：未获取到Token')
        loading.value = false
        return
      }
      
      // 保存token和用户信息到localStorage
      localStorage.setItem('token', token)
      console.log('Token saved in localStorage:', localStorage.getItem('token'))
      localStorage.setItem('user', JSON.stringify({
        id: response.data?.id,
        username: response.data?.username,
        realName: response.data?.realName
      }))
      
      ElMessage.success('登录成功')
      
      // 跳转到首页
      router.push('/company')
    } catch (error) {
      console.error('登录失败:', error)
      ElMessage.error(error.response?.data?.message || '登录失败，请检查用户名和密码')
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}

.login-box {
  width: 400px;
  padding: 40px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #409EFF;
}

.login-button {
  width: 100%;
}
</style> 