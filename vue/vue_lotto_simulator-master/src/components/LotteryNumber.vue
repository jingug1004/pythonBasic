<template>
  <section :class="$style.wrap">
    <slot name="title"></slot>
    <ul :class="[$style.list, opacity ? $style.opacity : '']">
      <li :class="$style.item" v-for="(num, idx) in lotteryNumber" :key="idx">
        <span :class="$style.num"
          :style="{ backgroundColor:getColor(num) }">{{ num }}</span>
      </li>
      <li :class="$style.item">
        <i class="material-icons" :class="$style.ico">add</i>
      </li>
      <li :class="$style.item">
        <span :class="$style.num"
          :style="{ backgroundColor:getColor(lotteryBonus) }">{{ lotteryBonus }}</span>
      </li>
    </ul>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'lottery-number',
  props: {
    getColor: {
      type: Function,
      required: true,
      validator(func) {
        return typeof func(1) === 'string';
      },
    },
  },
  data() {
    return {
      opacity: 'opacity',
    };
  },
  created() {
    this.EventBus.$on('win', () => {
      this.opacity = 'opacity';
    });
    this.EventBus.$on('lottery', () => {
      this.opacity = '';
    });
  },
  computed: {
    ...mapGetters(['color', 'lotteryCount', 'lotteryNumber', 'lotteryBonus']),
  },
  methods: {
    getLotteryCount() {
      return this.lotteryCount;
    },
  },
};
</script>

<style module>
.wrap {
  height: 50px;
}
.list {
  list-style: none;
  padding: 0;
  overflow: hidden;
  background-color: initial;
  transition: opacity 0.5s ease;
}
.item {
  float: left;
}
.num {
  display: inline-block;
  width: 42px;
  height: 42px;
  line-height: 39px;
  margin: 1px;
  font-size: 20px;
  text-align: center;
  border-radius: 50%;
  color: #fff;
  transition: background-color 0.3s ease;
}
.ico {
  height: 45px;
  margin-top: 11px;
  color: #666;
}
.opacity {
  opacity: 0.3;
}
</style>
