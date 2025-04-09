import request from './request'

// 用户相关接口
export default {
  // 用户登录
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  }
} 