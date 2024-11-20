import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      alias: ['/index.html', '/creer-compte'],
      name: 'account-create',
      component: () => import('../views/AccountCreateVue.vue'),
    },
    {
      path: '/:notFound',
      component: () => import('../views/NotFound.vue')
    }
  ],
})

export default router
