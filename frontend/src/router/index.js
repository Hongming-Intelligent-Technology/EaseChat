import {createRouter, createWebHistory} from 'vue-router'
import request from '../utils/request'

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

router.beforeEach(async (to) => {
  if (to.name === 'Login' || to.name === 'Register') {
    return true
  }

  try {
    const res = await request.get('/user/current')
    if (res.data?.code === 200) {
      return true
    }
  } catch {
    // Redirect handled below.
  }

  return {name: 'Login'}
})

export default router
