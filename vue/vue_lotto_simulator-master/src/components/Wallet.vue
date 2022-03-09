<template>
  <section :class="$style.wrap">
    <slot name="title"></slot>
    <div :class="[$style.case, $style.use]">
      <em :class="$style.money">{{ wallet.used | numberWithCommas }}원</em> 사용
    </div>
    <div :class="[$style.case, $style.have]">
      <em :class="$style.money">{{ wallet.have | numberWithCommas }}원</em> 남음
    </div>
    <button type="button" :class="$style.btn_charge" @click="onShowCharge">충전</button>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';
import { STOP } from '@/store/mutations-type';
import { CHARGE } from '@/constants/router';

export default {
  name: 'wallet',
  computed: {
    ...mapGetters(['wallet']),
  },
  methods: {
    onShowCharge() {
      this.$store.commit(STOP);
      this.$router.push(CHARGE);
    },
  },
};
</script>

<style module>
.wrap {
  text-align: right;
}
.case {
  margin-bottom: 7px;
  font-size: 17px;
}
.use {
  color: #000;
}
.have {
  color: #666;
}
.money {
  display: inline-block;
  width: 160px;
  font-size: 20px;
  text-align: right;
}
.btn_charge {
  width: 90px;
  height: 30px;
  line-height: 28px;
  margin-top: 5px;
  font-size: 18px;
  color: #333;
  border: solid 1px #333;
  background-color: #fff;
  cursor: pointer;
  outline: none;
}
</style>
