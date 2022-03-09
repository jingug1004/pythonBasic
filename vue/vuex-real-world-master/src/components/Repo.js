export default {
  name: 'Repo',
  props: ['repo', 'owner'],
  computed: {
    login () {
      return this.owner.login
    },
    name () {
      return this.repo.name
    },
    description () {
      return this.repo.description
    }
  }
}
