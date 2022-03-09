import Vue from 'vue'
import Vuex from 'vuex'
import createLogger from './plugins/logger'
import * as actions from './actions'
import * as getters from './getters'
import mutations from './mutations'
import applyMiddleware from './simple-middleware/apply-middleware'
import normalizrApiMiddleware from './normalizr-api-middleware'
const debug = process.env.NODE_ENV !== 'production'

const initState = {
  entities: {
    users: {},
    repos: {}
  },
  pagination: {
    starredByUser: {},
    stargazersByRepo: {}
  },
  errorMessage: ''
}

const configureStore = (preloadedState = initState) => {
  Vue.use(Vuex)
  const newMutations = applyMiddleware(mutations, normalizrApiMiddleware)
  const store = new Vuex.Store({
    state: preloadedState,
    strict: debug,
    actions,
    getters,
    mutations: newMutations,
    plugins: debug ? [createLogger()] : []
  })

  return store
}

export default configureStore
