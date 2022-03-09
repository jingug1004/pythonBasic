import { mapActions } from 'vuex'
import User from '@/components/User.vue'
import List from '@/components/List.vue'
import Repo from '@/components/Repo.vue'

const loadData = vm => {
  const { fullName } = vm
  vm.loadRepo([fullName, ['description']])
  vm.loadStargazers([fullName])
}

export default {
  name: 'RepoPage',
  components: {
    User,
    List,
    Repo
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      loadData(vm)
    })
  },
  beforeRouteUpdate (to, from, next) {
    next()
    loadData(this)
  },
  computed: {
    login () {
      return this.$route.params.login.toLowerCase()
    },
    name () {
      return this.$route.params.name.toLowerCase()
    },
    fullName () {
      return `${this.login}/${this.name}`
    },
    repo () {
      return this.$store.state.entities.repos[this.fullName]
    },
    owner () {
      return this.$store.state.entities.users[this.login]
    },
    stargazers () {
      return this.mapStateToProps().stargazers
    },
    stargazersPagination () {
      return this.mapStateToProps().stargazersPagination
    }
  },
  methods: {
    ...mapActions([
      'loadRepo',
      'loadStargazers'
    ]),
    handleLoadMoreClick () {
      this.loadStargazers([this.fullName, true])
    },
    mapStateToProps () {
      const {
        pagination: { stargazersByRepo },
        entities: { users }
      } = this.$store.state

      const stargazersPagination = stargazersByRepo[this.fullName] || { ids: [] }
      const stargazers = stargazersPagination.ids.map(id => users[id])

      return {
        stargazers,
        stargazersPagination
      }
    }
  }
}
