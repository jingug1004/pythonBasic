// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './containers/App.vue'
import router from './router'
import { sync } from 'vuex-router-sync'
import configureStore from './store/configureStore'

Vue.config.productionTip = false

const store = configureStore()
sync(store, router)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
