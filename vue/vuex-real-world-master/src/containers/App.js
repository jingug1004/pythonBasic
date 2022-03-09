import Explore from '@/components/Explore.vue'
export default {
  name: 'app',
  components: { Explore },
  computed: {
    inputValue () {
      return this.$store.state.route.path.substring(1)
    }
  },
  methods: {
    handleChange (nextValue) {
      this.$router.push(`/${nextValue}`)
    }
  }
}
