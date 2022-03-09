<template>
  <section :class="$style.wrap">
    <slot name="title"></slot>
    <ol :class="$style.list">
      <li v-for="(count, idx) in Object.values(result)" :key="idx" :class="$style.item">
        <em :class="$style.ranking">{{ idx + 1 }}등</em>
        <strong :class="$style.count">{{ count }}</strong>
      </li>
    </ol>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';
import { STOP_INTERVAL } from '@/constants/base';
import { WIN, WIN_HISTORY } from '@/store/mutations-type';

const matchingCountToRanking = {
  6: 1,
  5: 3,
  4: 4,
  3: 5,
};

export default {
  name: 'result',
  data() {
    return {};
  },
  computed: {
    ...mapGetters([
      'myNumber',
      'lotteryCount',
      'lotteryNumber',
      'lotteryBonus',
      'result',
    ]),
  },
  created() {
    this.EventBus.$on('checkMatching', this.checkMatching);
  },
  methods: {
    checkMatching() {
      let ranking = null;
      let matchingCnt = 0;
      let stopInterval = STOP_INTERVAL;

      this.lotteryNumber.forEach((num) => {
        if (this.myNumber.includes(num)) {
          matchingCnt++;
        }
      });

      ranking = matchingCountToRanking[matchingCnt];

      // 2등 검사
      if (matchingCnt === 5) {
        if (this.myNumber.includes(this.lotteryBonus)) {
          ranking = 2;
        }
      }

      if (ranking) {
        this.$store.commit(WIN, { ranking });
        this.$store.commit(WIN_HISTORY, {
          ranking,
          lotteryCount: this.lotteryCount,
          lotteryNumber: this.lotteryNumber,
          lotteryBonus: this.lotteryBonus,
          myNumber: this.myNumber,
        });
        this.EventBus.$emit('win');

        // 등수에 따라서 정지 시간 다르게
        stopInterval *= 6 - ranking;
      }

      setTimeout(() => this.EventBus.$emit('lottery'), ranking ? stopInterval : 0);
    },
  },
};
</script>

<style module>
.wrap {
  margin: 20px 0 25px 0;
  border-top: solid 3px #ddd;
  border-bottom: solid 3px #ddd;
}
.list {
  overflow: hidden;
  padding: 0;
}
.item {
  float: left;
  width: 20%;
  padding: 15px 0;
  text-align: center;
}
.ranking {
  display: block;
  margin-bottom: 8px;
  font-size: 20px;
  font-weight: bold;
  color: #666;
}
.count {
  font-size: 22px;
}
</style>
