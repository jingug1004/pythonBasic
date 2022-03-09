import compose from './compose'
// todo decorate vuex commit function

/*
  const newMutations = applyMiddleware(mutations, next => (state, payload) => {
    console.log(payload)
    next(state, payload)
  })
 */
export default function applyMiddleware (mutations, ...middlewares) {
  // todo raised error when change mutations
  // Uncaught TypeError:
  // Cannot set property loadUser of #<Object> which has only a getter
  const newMutations = {}
  Object.keys(mutations).forEach(actionType => {
    let action = mutations[actionType]
    const newAction = compose(...middlewares)(action)
    newMutations[actionType] = newAction
  })
  return newMutations
}
