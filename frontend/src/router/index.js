import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 简单的路由守卫示例（根据需求开启）
// router.beforeEach((to, from, next) => {
//   const isAuthenticated = localStorage.getItem('user'); // 假设登录存了localstorage
//   if (to.name !== 'Login' && to.name !== 'Register' && !isAuthenticated) next({ name: 'Login' })
//   else next()
// })

export default router