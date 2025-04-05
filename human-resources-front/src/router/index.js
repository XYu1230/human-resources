import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: '/company',
    children: [
      {
        path: 'company',
        name: 'CompanyManage',
        component: () => import('../views/company/CompanyManage.vue'),
        meta: { title: '企业管理' }
      },
      {
        path: 'organization',
        name: 'OrganizationManage',
        component: () => import('../views/organization/OrganizationManage.vue'),
        meta: { title: '组织管理' }
      },
      {
        path: 'department',
        name: 'DepartmentManage',
        component: () => import('../views/department/DepartmentManage.vue'),
        meta: { title: '部门管理' }
      },
      {
        path: 'position',
        name: 'PositionManage',
        component: () => import('../views/position/PositionManage.vue'),
        meta: { title: '职位管理' }
      },
      {
        path: 'employee',
        name: 'EmployeeManage',
        component: () => import('../views/employee/EmployeeManage.vue'),
        meta: { title: '员工管理' }
      },
      {
        path: 'onboarding',
        name: 'OnboardingManage',
        component: () => import('../views/onboarding/OnboardingManage.vue'),
        meta: { title: '入职管理' }
      }
    ]
  },
  // 可以添加其他路由，如登录页等
  {
    path: '/:pathMatch(.*)*',
    redirect: '/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router