import { createRouter, createWebHistory, Router, RouteRecordRaw } from 'vue-router';

import IndexLayout from '@/layout/IndexLayout/index.vue';
import appRoutes from './modules';
import { useUserStore } from '@/store';
import { UserInfoResp } from '@/api/auth';
import { isLogin } from '@/utils/auth';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: 'dashboard',
  },
  {
    path: '/login',
    component: () => import('@/views/login/index.vue')
  },
  {
    name: "root",
    path: "/",
    component: IndexLayout,
    children: appRoutes,
  },
  {
    path: '/error',
    component: IndexLayout,
    children: [
      {
        path: '401',
        component: () => import('@/views/error/401.vue')
      }
    ]
  }
  // {
  //   path: '/:pathMatch(.*)*',
  //   name: 'notFound',
  //   component: () => import('@/views/not-found/index.vue'),
  // },
];

const router: Router = createRouter({
  history: createWebHistory("/"),
  routes
});

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  // 是否需要权限认证
  if (to.matched.some(m => m.meta.requireAuth)) {
    if (!isLogin()) {
      next({path: '/login'});
    } else if (to.meta && to.meta.permissions) {
      const userInfo: UserInfoResp = await userStore.getUserInfo();
      let isAccess: boolean = true;
      const requirePerm: string[] = <string[]>to.meta.permissions;
      for (let i = 0; i < requirePerm.length; i++) {
        if (userInfo.permissions.indexOf(requirePerm[i]) == -1) {
          isAccess = false;
          break;
        }
      }
      if (isAccess) next();
      else next('/error/401');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
