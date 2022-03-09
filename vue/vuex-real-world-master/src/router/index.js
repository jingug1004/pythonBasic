import Vue from 'vue'
import Router from 'vue-router'
import UserPage from '@/containers/UserPage.vue'
import RepoPage from '@/containers/RepoPage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/:login',
      name: 'UserPage',
      component: UserPage
    },
    {
      path: '/:login/:name',
      name: 'RepoPage',
      component: RepoPage
    }
  ]
})
