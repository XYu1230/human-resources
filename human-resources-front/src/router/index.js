import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/Login.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/',
    name: 'Root',
    component: () => import('../views/login/Login.vue'),
    meta: { requiresAuth: false },
  },
  {
    path: '/home',
    component: Layout,
    redirect: '/company',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'company',
        name: 'CompanyManage',
        component: () => import('../views/company/CompanyManage.vue'),
        meta: { title: '企业管理', requiresAuth: true }
      },
      {
        path: 'organization',
        name: 'OrganizationManage',
        component: () => import('../views/organization/OrganizationManage.vue'),
        meta: { title: '组织架构', requiresAuth: true }
      },
      {
        path: 'department',
        name: 'DepartmentManage',
        component: () => import('../views/department/DepartmentManage.vue'),
        meta: { title: '部门管理', requiresAuth: true }
      },
      {
        path: 'position',
        name: 'PositionManage',
        component: () => import('../views/position/PositionManage.vue'),
        meta: { title: '职位管理', requiresAuth: true }
      },
      {
        path: 'employee',
        name: 'EmployeeManage',
        component: () => import('../views/employee/EmployeeManage.vue'),
        meta: { title: '员工管理', requiresAuth: true }
      },
      {
        path: 'onboarding',
        name: 'OnboardingManage',
        component: () => import('../views/onboarding/OnboardingManage.vue'),
        meta: { title: '入职管理', requiresAuth: true }
      }
    ]
  },
  {
    path: '/company',
    component: Layout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'CompanyManageRoot',
        component: () => import('../views/company/CompanyManage.vue'),
        meta: { title: '企业管理', requiresAuth: true }
      }
    ]
  },
  {
    path: '/organization',
    component: Layout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'OrganizationManageRoot',
        component: () => import('../views/organization/OrganizationManage.vue'),
        meta: { title: '组织架构', requiresAuth: true }
      }
    ]
  },
  {
    path: '/department',
    component: Layout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'DepartmentManageRoot',
        component: () => import('../views/department/DepartmentManage.vue'),
        meta: { title: '部门管理', requiresAuth: true }
      }
    ]
  },
  {
    path: '/position',
    component: Layout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'PositionManageRoot',
        component: () => import('../views/position/PositionManage.vue'),
        meta: { title: '职位管理', requiresAuth: true }
      }
    ]
  },
  {
    path: '/employee',
    component: Layout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'EmployeeManageRoot',
        component: () => import('../views/employee/EmployeeManage.vue'),
        meta: { title: '员工管理', requiresAuth: true }
      }
    ]
  },
  {
    path: '/onboarding',
    component: Layout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'OnboardingManageRoot',
        component: () => import('../views/onboarding/OnboardingManage.vue'),
        meta: { title: '入职管理', requiresAuth: true }
      }
    ]
  },
  // 其他路由
  {
    path: '/:pathMatch(.*)*',
    redirect: '/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 检查路由是否需要认证
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth !== false)
  // 获取token
  const token = localStorage.getItem('token')
  
  if (requiresAuth && !token) {
    // 需要认证但没有token，重定向到登录页
    next('/login')
  } else if (to.path === '/login' && token) {
    // 已登录用户访问登录页，重定向到首页
    next('/company')
  } else {
    // 其他情况正常导航
    next()
  }
})

export default router