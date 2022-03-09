<template>
  <section :class="$style.dialog">
    <h2 class="blind">번호 선택</h2>
    <div :class="$style.content">
      <ul :class="$style.choose_list">
        <li :class="$style.choose_item" v-for="(num, idx) in numbers" :key="idx">
          <span @click="onChooseNumber(num)"
            :class="$style.choose_num"
            :style="isChooseed(num)
              ? 'background-color: #333; border-color: #000; color: #fff'
              : 'background-color: #fff; border-color: #f00;'"
            >{{ num }}</span>
        </li>
        <li :class="$style.choose_item" v-for="idx in hideNumbers" :key="idx">
          <span :class="[$style.choose_num, $style.hide]"></span>
        </li>
      </ul>
      <div :class="$style.footer">
        <button v-if="myNumber.length > 0" type="button" @click="onResetChoose"
          :class="[$style.btn_footer, $style.btn_reset, getFooterHalfStyle()]">
          다시<br/>선택</button>
        <button type="button" @click="onAutoChoose"
          :class="[$style.btn_footer, $style.btn_auto, getFooterHalfStyle()]">
          자동<br/>선택</button>
        <button v-if="myNumber.length === 6" type="button" @click="onHideChoose"
          :class="[$style.btn_footer, $style.btn_ok]">
          선택<br/>완료</button>
      </div>
    </div>
  </section>
</template>

<script>
import { mapGetters } from 'vuex';
import { MY_NUMBER, REMOVE_MY_NUMBER, ADD_MY_NUMBER } from '@/store/mutations-type';
import { lotto } from '@/common/utils';

export default {
  name: 'choose',
  data() {
    return {
      numbers: Array.from(Array(45).keys(), v => v + 1),
      hideNumbers: Array(4),
    };
  },
  computed: {
    ...mapGetters(['color', 'myNumber', 'lotteryNumber', 'lotteryBonus']),
  },
  methods: {
    isChooseed(num) {
      if (this.myNumber.includes(num)) {
        return true;
      }
      return false;
    },
    onHideChoose() {
      this.$router.go(-1);
    },
    onChooseNumber(choose) {
      const num = Number(choose);
      if (this.myNumber.includes(num)) {
        this.$store.commit(REMOVE_MY_NUMBER, { num });
      } else if (this.myNumber.length < 6) {
        this.$store.commit(ADD_MY_NUMBER, { num });
      }
    },
    onResetChoose() {
      this.$store.commit(MY_NUMBER, { numbers: [] });
    },
    onAutoChoose() {
      this.$store.commit(MY_NUMBER, { numbers: lotto().numbers });
    },
    getFooterHalfStyle() {
      if (this.myNumber.length === 6) {
        return '';
      }
      return this.$style.btn_footer_half;
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
  width: 260px;
  margin-top: 35px;
  background-color: #fff;
}
.choose_list {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  padding: 10px 10px 0 10px;
}
.choose_item {
  margin: 1px;
}
.choose_num {
  display: inline-block;
  box-sizing: border-box;
  width: 30px;
  height: 40px;
  line-height: 38px;
  margin: 1px;
  font-size: 18px;
  text-align: center;
  border: solid 1px #fff;
  color: #f00;
  cursor: pointer;
}
.choose_num.hide {
  cursor: default;
  opacity: 0;
}
.opacity {
  opacity: 0.3;
}
.footer {
  display: block;
  margin-top: 10px;
  text-align: center;
  border-top: solid 1px #aaa;
}
.btn_footer {
  width: 83px;
  height: 50px;
  line-height: 20px;
  background-color: #fff;
  border: none;
  font-size: 18px;
  cursor: pointer;
}
.btn_footer_half {
  width: 125px;
}
.btn_reset {
  color: #f20;
  border-right: solid 1px #aaa;
}
.btn_auto {
  color:#05f;
}
.btn_ok {
  color: #333;
  border-left: solid 1px #aaa;
}
</style>
