
import request from './request'

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