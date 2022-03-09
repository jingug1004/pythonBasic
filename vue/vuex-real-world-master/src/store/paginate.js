import union from 'lodash/union'
import fromPairs from 'lodash/fromPairs'
import Vue from 'vue'
const paginate = ({stateKey, types, mapPayloadToKey}) => {
  if (!Array.isArray(types) || types.length !== 3) {
    throw new Error('Expected types to be an array of three elements.')
  }
  if (!types.every(t => typeof t === 'string')) {
    throw new Error('Expected types to be strings.')
  }
  if (typeof mapPayloadToKey !== 'function') {
    throw new Error('Expected mapPayloadToKey to be a function.')
  }
  const [ requestType, successType, failureType ] = types

  // todo not update whole paginationRecord
  const updatePagination = (paginationRecord = {
    isFetching: false,
    nextPageUrl: undefined,
    pageCount: 0,
    ids: []
  }, payload, type) => {
    switch (type) {
      case requestType:
        return {
          ...paginationRecord,
          isFetching: true
        }
      case successType:
        return {
          ...paginationRecord,
          isFetching: false,
          ids: union(paginationRecord.ids, payload.response.result),
          nextPageUrl: payload.response.nextPageUrl,
          pageCount: paginationRecord.pageCount + 1
        }
      case failureType:
        return {
          ...paginationRecord,
          isFetching: false
        }
      default:
        return paginationRecord
    }
  }

  const mutationGenerator = type => {
    return (state, payload) => {
      // one type collection of pagination, like starredByUser collection
      let paginationSubState = state.pagination[stateKey]

      const key = mapPayloadToKey(payload)
      if (typeof key !== 'string') {
        throw new Error('Expected key to be a string.')
      }

      Vue.set(paginationSubState, key, updatePagination(paginationSubState[key], payload, type))
    }
  }

  return fromPairs(types.map(type => [
    type,
    mutationGenerator(type)
  ]))
}
export default paginate
