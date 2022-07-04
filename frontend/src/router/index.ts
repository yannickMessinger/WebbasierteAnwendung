import { createRouter, createWebHistory } from 'vue-router'
import {useLogin} from '@/services/useLogin'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/AngeboteView.vue')
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path:'/gebot/:angebotidstr',
      name:'GebotView',
      component: () => import('../views/GebotView.vue'),
      props: true



    },
    {
      path:'/login',
      name:'Login/Logout',
      component: () => import('../views/LoginView.vue')
      



    }
  ]
})

/*
router.beforeEach( async (to, from) => {
  // wenn z.B. ein 'berechtigt' nicht wahr ist,
  // alle Nicht-/login-Navigationen auf /login leiten
    if (!useLogin().logindata.loggedin && to.name !== 'Login') {
  return { name: 'Login/Logout' }
    }
  })
*/  

export default router
