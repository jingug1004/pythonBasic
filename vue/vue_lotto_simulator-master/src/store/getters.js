import { PRICE } from '@/constants/base';

export default {
  isReady: state => state.isPlaying && state.myNumber.length === 6,
  isPlaying: state => state.isPlaying,
  color: state => state.color,
  isMoney: state => state.wallet.have >= PRICE,
  myNumber: state => state.myNumber,
  lotteryCount: state => state.lotteryCount,
  lotteryNumber: state => state.lotteryNumber,
  lotteryBonus: state => state.lotteryBonus,
  winHistory: state => state.winHistory,
  result: state => state.result,
  wallet: state => state.wallet,
};
