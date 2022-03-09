import { CALL_API, api, Schemas } from './api'
import * as types from './mutation-types'

// Fetches a single user from Github API.
// Relies on the custom API action 'interpreter' defined in ./api.js.
const fetchUser = login => ({
  [CALL_API]: {
    types: [types.USER_REQUEST, types.USER_SUCCESS, types.USER_FAILURE],
    endpoint: `users/${login}`,
    schema: Schemas.USER
  }
})

// Fetches a single user from Github API unless it is cached.
// Relies on the custom API action 'interpreter' defined in ./api.js.
export const loadUser = (store, [login, requiredFields = []]) => {
  const { state } = store
  const user = state.entities.users[login]
  if (user && requiredFields.every(key => user.hasOwnProperty(key))) {
    return null
  }

  return api(store, fetchUser(login))
}

// Fetches a single repository from Github API.
// Relies on the custom API middleware defined in ../middleware/api.js.
const fetchRepo = fullName => ({
  [CALL_API]: {
    types: [ types.REPO_REQUEST, types.REPO_SUCCESS, types.REPO_FAILURE ],
    endpoint: `repos/${fullName}`,
    schema: Schemas.REPO
  }
})

// Fetches a single repository from Github API unless it is cached.
// Relies on Redux Thunk middleware.
export const loadRepo = (store, [fullName, requiredFields = []]) => {
  const repo = store.state.entities.repos[fullName]
  if (repo && requiredFields.every(key => repo.hasOwnProperty(key))) {
    return null
  }

  return api(store, fetchRepo(fullName))
}

// Fetches a page of starred repos by a particular user.
// Relies on the custom API action 'interpreter' defined in ./api.js.
const fetchStarred = (login, nextPageUrl) => ({
  login,
  [CALL_API]: {
    types: [ types.STARRED_REQUEST, types.STARRED_SUCCESS, types.STARRED_FAILURE ],
    endpoint: nextPageUrl,
    schema: Schemas.REPO_ARRAY
  }
})

// Fetches a page of starred repos by a particular user.
// Bails out if page is cached and user didn't specifically request next page.
// Relies on the custom API action 'interpreter' defined in ./api.js.
export const loadStarred = (store, [login, nextPage]) => {
  const {
    nextPageUrl = `users/${login}/starred`,
    pageCount = 0
  } = store.state.pagination.starredByUser[login] || {}

  if (pageCount > 0 && !nextPage) {
    return null
  }

  return api(store, fetchStarred(login, nextPageUrl))
}

// Fetches a page of stargazers for a particular repo.
// Relies on the custom API action 'interpreter' defined in ./api.js.
const fetchStargazers = (fullName, nextPageUrl) => ({
  fullName,
  [CALL_API]: {
    types: [ types.STARGAZERS_REQUEST, types.STARGAZERS_SUCCESS, types.STARGAZERS_FAILURE ],
    endpoint: nextPageUrl,
    schema: Schemas.USER_ARRAY
  }
})

// Fetches a page of stargazers for a particular repo.
// Bails out if page is cached and user didn't specifically request next page.
// Relies on the custom API action 'interpreter' defined in ./api.js.
export const loadStargazers = (store, [fullName, nextPage]) => {
  const {
    nextPageUrl = `repos/${fullName}/stargazers`,
    pageCount = 0
  } = store.state.pagination.stargazersByRepo[fullName] || {}

  if (pageCount > 0 && !nextPage) {
    return null
  }

  return api(store, fetchStargazers(fullName, nextPageUrl))
}
