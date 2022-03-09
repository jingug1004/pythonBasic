export default {
  name: 'List',
  props: {
    loadingLabel: {
      default: 'Loading',
      type: String,
      required: true
    },
    pageCount: Number,
    items: {
      type: Array,
      required: true
    },
    isFetching: {
      default: true,
      type: Boolean,
      required: true
    },
    onLoadMoreClick: {
      type: Function,
      required: true
    },
    nextPageUrl: String
  },
  computed: {
    isEmpty () {
      return this.items.length === 0
    },
    isLastPage () {
      return !this.nextPageUrl
    }
  }
}
