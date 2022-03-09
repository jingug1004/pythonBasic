<template>
  <section :class="$style.wrap">
    <slot name="title"></slot>
    <ul :class="$style.list">
      <li :class="$style.item" v-for="(value, idx) in winHistory" :key="idx">
        <span>
          {{ value.lotteryCount | numberWithCommas }}
          <span :class="$style.small">회차 </span>
          {{ value.ranking }}
          <span :class="$style.small">등 당첨</span>
        </span>
      </li>
    </ul>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'win-history',
  computed: {
    ...mapGetters(['winHistory']),
  },
};
</script>

<style module>
.wrap {
  position: absolute;
  bottom: 28px;
  left: 0;
}
.list {
  width: 170px;
  height: 94px;
  padding-right: 10px;
  overflow-y: auto;
  text-align: right;
  -webkit-overflow-scrolling: auto;
}
.item {
  padding: 3px;
  font-size: 17px;
  font-weight: bold;
  color: #ccc;
}
.item:nth-child(1) {
  color: #000;
}
.item:nth-child(2) {
  color: #555;
}
.item:nth-child(3) {
  color: #999;
}
.small {
  font-size: 14px;
}
</style>
