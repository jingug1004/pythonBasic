import Vue from 'vue';
import App from './App';
import store from './store';
import router from './router';
import EventBus from './common/eventBus';
import { numberWithCommas } from './common/utils';

import 'velocity-animate';
import 'babel-polyfill';
import './assets/css/reset.css';
import './assets/css/common.css';
import 'material-design-icons-iconfont/dist/material-design-icons.css';
// import './assets/css/material-design-icons.css';

Vue.config.productionTip = false;

Vue.mixin({
  created() {
    this.EventBus = EventBus;
  },
});

Vue.filter('numberWithCommas', numberWithCommas);

new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App },
});
