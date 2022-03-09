import merge from 'lodash/merge'

const normalizrApiMiddleware = next => (state, payload) => {
  if (payload.response && payload.response.entities) {
    state.entities = merge({}, state.entities, payload.response.entities)
  }
  next(state, payload)
}

export default normalizrApiMiddleware
