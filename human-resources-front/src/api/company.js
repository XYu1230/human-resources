// 暂时没用到这三个.js文件

import axios from 'axios'

// 设置基础URL，指向后端服务器
const baseURL = 'http://localhost:8080'

// 创建axios实例
const request = axios.create({
  baseURL,
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 可以在这里添加token等认证信息
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 可以统一处理响应数据
    return response.data
  },
  error => {
    return Promise.reject(error)
  }
)

// 企业管理相关接口
export default {
  // 获取企业列表
  getCompanyList(params) {
    return request({
      url: '/company/list',
      method: 'get',
      params
    })
  },
  
  // 获取企业详情
  getCompanyDetail(id) {
    return request({
      url: `/company/${id}`,
      method: 'get'
    })
  },
  
  // 新增企业
  addCompany(data) {
    return request({
      url: '/company',
      method: 'post',
      data
    })
  },
  
  // 更新企业
  updateCompany(id, data) {
    return request({
      url: `/company/${id}`,
      method: 'put',
      data
    })
  },
  
  // 删除企业
  deleteCompany(id) {
    return request({
      url: `/company/${id}`,
      method: 'delete'
    })
  },
  
  // 更新企业状态
  updateCompanyStatus(id, status) {
    return request({
      url: `/company/${id}/status`,
      method: 'put',
      data: { status }
    })
  }
}