import Vue from 'vue';
import Router from 'vue-router';
// import Main from '@/views/Main';
// import Choose from '@/components/Choose';
// import Charge from '@/components/Charge';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: () => import('@/views/Main'),
      children: [
        {
          path: 'choose',
          name: 'choose',
          component: () => import('@/components/Choose'),
        },
        {
          path: 'charge',
          name: 'charge',
          component: () => import('@/components/Charge'),
        },
      ],
    },
  /*
    {
      path: '/',
      name: 'main',
      component: Main,
      children: [
        {
          path: '/choose',
          name: 'choose',
          component: Choose,
        },
        {
          path: '/charge',
          name: 'charge',
          component: Charge,
        },
      ],
    },
    */
  ],
});
