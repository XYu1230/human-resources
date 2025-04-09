import axios from 'axios'

// 设置基础URL，指向后端服务器
const baseURL = 'http://localhost:8080/api'

// 创建axios实例
const request = axios.create({
  baseURL,
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')
    if (token) {
      // 添加token到请求头
      config.headers['token'] = `${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 直接返回响应数据
    return response.data
  },
  error => {
    // 处理401未授权错误
    if (error.response && error.response.status === 401) {
      // 清除token
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      // 重定向到登录页
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default request 