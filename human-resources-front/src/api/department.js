import request from '@/utils/request'

// 获取部门列表
export function getDepartmentList(params) {
  return request({
    url: '/department/list',
    method: 'get',
    params
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
    url: '/department/add',
    method: 'post',
    data
  })
}

// 更新部门
export function updateDepartment(data) {
  return request({
    url: '/department/update',
    method: 'put',
    data
  })
}

// 删除部门
export function deleteDepartment(id) {
  return request({
    url: `/department/delete/${id}`,
    method: 'delete'
  })
}

// 切换部门状态
export function toggleDepartmentStatus(id, status) {
  return request({
    url: '/department/status',
    method: 'put',
    data: { id, status }
  })
}

// 获取部门详情
export function getDepartmentDetail(id) {
  return request({
    url: `/department/detail/${id}`,
    method: 'get'
  })
}