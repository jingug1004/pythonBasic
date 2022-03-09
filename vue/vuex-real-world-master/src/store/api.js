import { normalize, schema } from 'normalizr'
import { camelizeKeys } from 'humps'

const API_ROOT = 'https://api.github.com/'

// Extracts the next page URL from Github API response.
const getNextPageUrl = response => {
  const link = response.headers.get('link')
  if (!link) {
    return null
  }

  const nextLink = link.split(',').find(s => s.indexOf('rel="next"') > -1)
  if (!nextLink) {
    return null
  }

  return nextLink.split(';')[0].slice(1, -1)
}

// Fetches an API response and normalizes the result JSON according to schema.
// This makes every API response have the same shape, regardless of how nested it was.
const callApi = (endPoint, schema) => {
  const fullUrl =
    endPoint.indexOf(API_ROOT) === -1 ? API_ROOT + endPoint : endPoint

  return fetch(fullUrl).then(response =>
        response.json().then(json => {
          if (!response.ok) {
            return Promise.reject(json)
          }

          const camelizedJson = camelizeKeys(json)
          const nextPageUrl = getNextPageUrl(response)

          return Object.assign({},
            normalize(camelizedJson, schema),
            { nextPageUrl }
          )
        })
    )
}

// We use this Normalizr schemas to transform API responses from a nested form
// to a flat form where repos and users are placed in `entities`, and nested
// JSON objects are replaced with their IDs. This is very convenient for
// consumption by reducers, because we can easily build a normalized tree
// and keep it updated as we fetch more data.

// Read more about Normalizr: https://github.com/paularmstrong/normalizr

// GitHub's API may return results with uppercase letters while the query
// doesn't contain any. For example, "someuser" could result in "SomeUser"
// leading to a frozen UI as it wouldn't find "someuser" in the entities.
// That's why we're forcing lower cases down there.

const userSchema = new schema.Entity('users', {}, {
  idAttribute: user => user.login.toLowerCase()
})

const repoSchema = new schema.Entity('repos', {
  owner: userSchema
}, {
  idAttribute: repo => repo.fullName.toLowerCase()
})

// Schemas for Github API responses.
export const Schemas = {
  USER: userSchema,
  USER_ARRAY: [userSchema],
  REPO: repoSchema,
  REPO_ARRAY: [repoSchema]
}

// Attribute key that carries API call info interpreted by this Vuex action.
export const CALL_API = 'Call API'

// A Vuex action that interprets actions with CALL_API info specified.
// Performs the call and promises when such actions are dispatched.
export const api = ({state, commit}, payload) => {
  const callAPI = payload[CALL_API]
  if (typeof callAPI === 'undefined') {
    return
  }

  let { endpoint } = callAPI
  const { schema, types } = callAPI

//   if (typeof endpoint === 'function') {
//     endpoint = endpoint(store.state)
//   }

  if (typeof endpoint !== 'string') {
    throw new Error('Specify a string endpoint URL.')
  }
  if (!schema) {
    throw new Error('Specify one of the exported Schemas.')
  }
  if (!Array.isArray(types) || types.length !== 3) {
    throw new Error('Expected an array of three mutation types.')
  }
  if (!types.every(type => typeof type === 'string')) {
    throw new Error('Expected mutation types to be strings.')
  }

  const mutationWith = data => {
    const finalMutation = Object.assign({}, payload, data)
    delete finalMutation[CALL_API]
    return finalMutation
  }

  const [ requestType, successType, failureType ] = types
  commit(mutationWith({type: requestType}))

  return callApi(endpoint, schema).then(
      response => commit(mutationWith({
        response,
        type: successType
      })),
      error => commit(mutationWith({
        type: failureType,
        error: error.message || 'Something bad happened'
      }))
  )
}
