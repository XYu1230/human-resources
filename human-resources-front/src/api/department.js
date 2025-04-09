import request from './request'

// 获取指定公司的部门列表
export function getDepartmentList(companyId) {
  return request({
    url: `/department/list/${companyId}`,
    method: 'get'
  })
}

// 获取部门树形结构
export function getDepartmentTree(params) {
  return request({
    url: '/department/tree',
    method: 'get',
    params
  })
}

// 添加部门
export function addDepartment(data) {
  return request({
    url: '/department',
    method: 'post',
    data
  })
}

// 更新部门
export function updateDepartment(id, data) {
  return request({
    url: `/department/${id}`,
    method: 'put',
    data
  })
}

// 删除部门
export function deleteDepartment(id) {
  return request({
    url: `/department/${id}`,
    method: 'delete'
  })
}

// 切换部门状态
export function toggleDepartmentStatus(id, status) {
  return request({
    url: `/department/${id}/status`,
    method: 'put',
    data: { status }
  })
}

// 获取部门详情
export function getDepartmentDetail(id) {
  return request({
    url: `/department/${id}`,
    method: 'get'
  })
}