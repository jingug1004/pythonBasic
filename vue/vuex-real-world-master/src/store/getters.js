export const user = state => login => {
  return state.entities.users[login]
}
