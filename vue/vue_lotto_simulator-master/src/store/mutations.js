import { PRICE } from '@/constants/base';
import {
  PLAY,
  STOP,
  LOTTERY,
  MY_NUMBER,
  ADD_MY_NUMBER,
  REMOVE_MY_NUMBER,
  ADD_MONEY,
  WIN,
  WIN_HISTORY,
} from './mutations-type';
import EventBus from '@/common/eventBus';

export default {
  [PLAY]: (state) => {
    state.isPlaying = true;
    EventBus.$emit('lottery');
  },
  [STOP]: (state) => {
    state.isPlaying = false;
  },
  [LOTTERY]: (state, payload) => {
    state.lotteryCount++;
    state.lotteryNumber = payload.number;
    state.lotteryBonus = payload.bonus;
    state.wallet.used += PRICE;
    state.wallet.have -= PRICE;
    EventBus.$emit('checkMatching');
  },
  [MY_NUMBER]: (state, payload) => {
    state.myNumber = payload.numbers;
  },
  [REMOVE_MY_NUMBER]: (state, payload) => {
    const idx = state.myNumber.indexOf(payload.num);
    if (idx > -1) {
      state.myNumber.splice(idx, 1);
    }
  },
  [ADD_MY_NUMBER]: (state, payload) => {
    state.myNumber.push(payload.num);
    state.myNumber.sort((a, b) => a - b);
  },
  [WIN]: (state, payload) => {
    state.result[payload.ranking]++;
    state.ranking = payload.ranking;
  },
  [WIN_HISTORY]: (state, payload) => {
    state.winHistory.unshift(payload);
    state.winHistory = state.winHistory.slice(0, 100);
  },
  [ADD_MONEY]: (state, payload) => {
    state.wallet.have += payload.money;
  },
};
