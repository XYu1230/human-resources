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