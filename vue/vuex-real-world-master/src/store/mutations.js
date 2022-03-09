import * as types from './mutation-types'
import paginate from './paginate'

export default {
  [types.USER_SUCCESS] (state, payload) {
  },
  [types.REPO_SUCCESS] (state, payload) {
  },
  [types.USER_REQUEST] () {},
  [types.REPO_REQUEST] () {},
  ...paginate({
    stateKey: 'starredByUser',
    mapPayloadToKey: payload => payload.login,
    types: [types.STARRED_REQUEST, types.STARRED_SUCCESS, types.STARRED_FAILURE]
  }),
  ...paginate({
    stateKey: 'stargazersByRepo',
    mapPayloadToKey: payload => payload.fullName,
    types: [types.STARGAZERS_REQUEST, types.STARGAZERS_SUCCESS, types.STARGAZERS_FAILURE]
  }),
  [types.RESET_ERROR_MESSAGE] (state, payload) {
    state.errorMessage = payload.error
  }
}
