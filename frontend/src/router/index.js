import {createRouter, createWebHistory} from 'vue-router'

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
  },
  {
    path: '/chat',
    name: 'Chat',
    component: () => import('../views/Chat.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Simple route guard example (enable as needed)
// router.beforeEach((to, from, next) => {
//   const isAuthenticated = localStorage.getItem('user'); // Assuming login info is saved in localStorage
//   if (to.name !== 'Login' && to.name !== 'Register' && !isAuthenticated) next({ name: 'Login' })
//   else next()
// })

export default router
