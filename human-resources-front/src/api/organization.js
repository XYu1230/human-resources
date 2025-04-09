import request from './request'

// 获取组织列表
export function getOrganizationList(params) {
  return request({
    url: '/organization/list',
    method: 'get',
    params
  })
}

// 获取组织树形结构
export function getOrganizationTree(params) {
  return request({
    url: '/organization/tree',
    method: 'get',
    params
  })
}

// 添加组织
export function addOrganization(data) {
  return request({
    url: '/organization',
    method: 'post',
    data
  })
}

// 更新组织
export function updateOrganization(id, data) {
  return request({
    url: `/organization/${id}`,
    method: 'put',
    data
  })
}

// 删除组织
export function deleteOrganization(id) {
  return request({
    url: `/organization/${id}`,
    method: 'delete'
  })
}

// 切换组织状态
export function toggleOrganizationStatus(id, status) {
  return request({
    url: `/organization/${id}/status`,
    method: 'put',
    data: { status }
  })
}