<template>
  <section :class="$style.wrap">
    <slot name="title"></slot>
    <ul :class="[$style.list, opacity? $style.opacity : '']">
      <li :class="$style.item" v-for="(num, idx) in myNumber" :key="idx">
        <span :class="$style.num"
          :style="{
            borderColor: getColor(num),
            backgroundColor: getMatchingBgColor(num),
            color: getMatchingTextColor(num),
          }">{{ num }}</span>
      </li>
    </ul>
    <button type="button" @click="onShowChoose" :class="$style.btn_choose">
      <span class="txt_btn">번호<br/>선택</span>
    </button>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';
import { STOP } from '@/store/mutations-type';
import { CHOOSE } from '@/constants/router';

export default {
  name: 'my-number',
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
      opacity: '',
    };
  },
  created() {
    this.EventBus.$on('win', () => {
      this.opacity = '';
    });
    this.EventBus.$on('lottery', () => {
      this.opacity = 'opacity';
    });
  },
  computed: {
    ...mapGetters(['color', 'myNumber', 'lotteryNumber', 'lotteryBonus']),
  },
  methods: {
    getMatchingBgColor(num) {
      if (this.lotteryNumber.includes(num)) {
        return this.getColor(num);
      }
      return '#fff';
    },
    getMatchingTextColor(num) {
      if (this.lotteryNumber.includes(num)) {
        return '#fff';
      }
      return '#000';
    },
    onShowChoose() {
      this.$store.commit(STOP);
      this.$router.push(CHOOSE);
    },
  },
};
</script>

<style module>
.wrap {
  position: relative;
  height: 50px;
  margin-top: 5px;
  overflow: hidden;
}
.list {
  float: left;
  overflow: hidden;
  padding: 0;
  background-color: initial;
  transition: opacity 0.5s ease;
}
.item {
  float: left;
}
.num {
  display: inline-block;
  box-sizing: border-box;
  width: 42px;
  height: 42px;
  line-height: 39px;
  margin: 1px;
  font-size: 20px;
  text-align: center;
  border-radius: 50%;
  border: solid 1px #fff;
  transition: background-color 0.3s ease;
}
.btn_choose {
  float: right;
  width: 43px;
  height: 43px;
  line-height: 14px;
  padding: 0;
  margin: 2px 8px 0 0;
  color: #333;
  border: solid 1px #333;
  border-radius: 50%;
  background-color: #fff;
  cursor: pointer;
  outline: none;
}
.ico {
  margin-left: 2px;
  font-size: 25px;
  vertical-align: top;
}
</style>
