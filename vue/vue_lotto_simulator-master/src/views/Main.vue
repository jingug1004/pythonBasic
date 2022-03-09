<template>
  <div :class="$style.container">
    <header :class="$style.header">
      <h1 :class="$style.title">{{ appName }}</h1>
      <controller>
        <h2 slot="title" class="blind">시작/정지</h2>
      </controller>
    </header>
    <main :class="$style.main">
      <lottery-number :getColor="getColor">
        <h2 slot="title" class="blind">추첨 번호</h2>
      </lottery-number>
      <my-number :getColor="getColor">
        <h2 slot="title" class="blind">내 번호</h2>
      </my-number>
      <result>
        <h2 slot="title" class="blind">결과</h2>
      </result>
      <wallet>
        <h2 slot="title" class="blind">지갑</h2>
      </wallet>
      <win-history>
        <h2 slot="title" class="blind">추첨 이력</h2>
      </win-history>
    </main>
    <router-view></router-view>
  </div>
</template>

<script>
import { APP_NAME, PLAY_INTERVAL } from '@/constants/base';
import { STOP } from '@/store/mutations-type';
import { LOTTERY } from '@/store/actions-type';
import { mapGetters } from 'vuex';
import { delay } from '@/common/utils';
import Controller from '@/components/Controller';
import LotteryNumber from '@/components/LotteryNumber';
import MyNumber from '@/components//MyNumber';
import WinHistory from '@/components/WinHistory';
import Result from '@/components/Result';
import Wallet from '@/components/Wallet';

export default {
  components: {
    Controller,
    LotteryNumber,
    MyNumber,
    WinHistory,
    Result,
    Wallet,
  },
  data() {
    return {
      appName: APP_NAME,
    };
  },
  computed: {
    ...mapGetters([
      'color',
      'isReady',
      'isPlaying',
      'isMoney',
      'myNumber',
    ]),
  },
  created() {
    this.EventBus.$on('lottery', this.lottery);
    if (this.myNumber.length === 0) {
      this.$router.push('choose');
    }
  },
  methods: {
    lottery() {
      if (!this.isReady) return;
      if (this.isMoney) this.play();
      else this.noMoney();
    },
    noMoney() {
      this.$store.commit(STOP);
    },
    play() {
      (async () => {
        // 재추첨 시간 동안 딜레이
        await delay(PLAY_INTERVAL);
        // 서버에 추첨 요청
        this.$store.dispatch(`${LOTTERY}`);
      })();
    },
    getColor(num) {
      for (let i = 0, len = this.color.length; i < len; i++) {
        const item = this.color[i];
        if (item.min <= num && num <= item.max) return item.value;
      }
      return 'initial';
    },
  },
};
</script>

<style module>
.container {
  position: relative;
  width: 340px;
  min-width: 340px;
  margin-top: 10px;
}
.header {
  position: relative;
  height: 60px;
  line-height: 60px;
  margin-bottom: 20px;
  padding-left: 5px;
  border-top: solid 3px #ddd;
  border-bottom: solid 3px #ddd;
  overflow: hidden;
}
.title {
  font-size: 25px;
  font-weight: bold;
  vertical-align: top;
  color: #333;
}
.main {
  padding-bottom: 25px;
  border-bottom: solid 3px #ddd;
}
</style>
