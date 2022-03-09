export default {
  name: 'Explore',
  props: ['value', 'onChange'],
  methods: {
    doneEdit (e) {
      const value = e.target.value.trim()
      this.onChange(value)
    },
    handleGoClick () {
      const value = this.$refs.input.value.trim()
      this.onChange(value)
    }
  }
}
