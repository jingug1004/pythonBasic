<template>
  <section :class="$style.dialog">
    <h2 class="blind">금액 충전</h2>
    <div :class="$style.content">
      <div :class="$style.result">
        <div :class="$style.charge_wrap">
          <transition
            v-on:before-enter="beforeEnter"
            v-on:enter="enter"
            v-on:leave="leave">
            <strong v-if="isAdd"
              :class="$style.charge">+{{ addMoney | numberWithCommas }}</strong>
          </transition>
        </div>
        <em :class="$style.have">{{ wallet.have | numberWithCommas }}원</em> 남음
      </div>
      <ul :class="$style.list">
        <li :class="$style.item" v-for="(money, idx) in choose" :key="idx">
          <span @click="onAddMoney(money)"
            :class="[$style.money, $style['color_' + money]]">
            +{{ money | numberWithCommas }}</span>
        </li>
      </ul>
      <button type="button" @click="onHideCharge"
          :class="[$style.btn_footer, $style.btn_close]">닫기</button>
    </div>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';
import { ADD_MONEY } from '@/store/mutations-type';

export default {
  name: 'charge',
  data() {
    return {
      timer: null,
      isAdd: false,
      addMoney: 0,
      choose: [
        50000,
        10000,
        5000,
        1000,
      ],
    };
  },
  computed: {
    ...mapGetters(['wallet']),
  },
  methods: {
    onAddMoney(money) {
      if (!this.timer) {
        this.addMoney = money;
        this.isAdd = true;
        this.$store.commit(ADD_MONEY, { money });
        this.timer = setTimeout(this.hideMotion, 300);
      }
    },
    hideMotion() {
      this.isAdd = false;
      this.timer = null;
    },
    onHideCharge() {
      this.$router.go(-1);
    },
    beforeEnter(el) {
      const element = el;
      element.style.opacity = 1;
      element.style.transform = 'translateY(0)';
    },
    enter(el, done) {
      Velocity(el,
        { translateY: 0 },
        { duration: 100, complete: done },
      );
    },
    leave(el, done) {
      Velocity(el,
        { opacity: 0, translateY: '-5px' },
        { duration: 100, complete: done },
      );
    },
  },
};
</script>

<style module>
.dialog {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}
.content {
  position: relative;
  width: 240px;
  padding: 10px 10px 60px 10px;
  margin-top: 35px;
  background-color: #fff;
}
.result {
  text-align: right;
  overflow: hidden;
  margin-bottom: 10px;
}
.charge_wrap {
  height: 20px;
  margin: 0 34px 2px 0;
}
.charge {
  display: inline-block;
  font-size: 20px;
  opacity: 0;
  color: red;
}
.have {
  display: inline-block;
  height: 28px;
  font-size: 24px;
  font-weight: bold;
}
.list {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  padding-top: 10px;
  margin: 0 auto;
  text-align: center;
}
.item {
  display: inline-block;
  width: 115px;
  margin-bottom: 10px;
  font-size: 24px;
  font-weight: bold;
}
.money {
  display: block;
  padding: 8px 0;
  background: green;
  color: #fff;
  border: solid 1px #333;
  cursor: pointer;
}
.btn_footer {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 46px;
  background-color: #fff;
  font-size: 18px;
  border: 0;
  border-top: solid 1px #aaa;
  cursor: pointer;
}
.btn_close {
  color: #333;
}
.color_1000 {
  background-color: rgb(93, 163, 255);
  border-color: rgb(0, 89, 206);
}
.color_5000 {
  background-color: rgb(245, 168, 100);
  border-color: rgb(199, 103, 0);
}
.color_10000 {
  background-color: rgb(91, 186, 76);
  border-color: rgb(0, 111, 0);
}
.color_50000 {
  background-color: rgb(255, 185, 7);
  border-color: rgb(177, 127, 0);
}
</style>
