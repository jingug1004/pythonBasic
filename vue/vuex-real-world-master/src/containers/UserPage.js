import { mapActions, mapState } from 'vuex'
import User from '@/components/User.vue'
import List from '@/components/List.vue'
import Repo from '@/components/Repo.vue'
import zip from 'lodash/zip'

export default {
  name: 'UserPage',
  components: {
    User,
    List,
    Repo
  },
  computed: {
    user () {
      return this.$store.getters.user(this.login)
    },
    login () {
      // We need to lower case the login due to the way GitHub's API behaves.
      // Have a look at ../middleware/api.js for more details.
      return this.$route.params.login.toLowerCase()
    },
    items () {
      const {
        starredRepos,
        starredRepoOwners
      } = this.mapStateToProps()

      return zip(starredRepos, starredRepoOwners)
    },
    starredPagination () {
      return this.mapStateToProps().starredPagination
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.loadData(vm)
    })
  },
  beforeRouteUpdate (to, from, next) {
    next()
    this.loadData(this)
  },
  methods: {
    ...mapActions([
      'loadUser',
      'loadStarred'
    ]),
    loadData ({ login, loadUser, loadStarred }) {
      loadUser([login, [ 'name' ]])
      loadStarred([login])
    },
    handleLoadMoreClick () {
      this.loadStarred([this.login, true])
    },
    mapStateToProps () {
      const {
        pagination: { starredByUser },
        entities: { users, repos }
      } = this.$store.state

      const starredPagination = starredByUser[this.login] || { ids: [] }
      const starredRepos = starredPagination.ids.map(id => repos[id])
      const starredRepoOwners = starredRepos.map(repo => users[repo.owner])

      return {
        starredRepos,
        starredRepoOwners,
        starredPagination
      }
    }
  }
}
