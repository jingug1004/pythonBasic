import { DEFAULT_MONEY } from '@/constants/base';

export default {
  isPlaying: false,
  color: [
    { min: 1, max: 10, value: 'orange' },
    { min: 11, max: 20, value: 'blue' },
    { min: 21, max: 30, value: 'red' },
    { min: 31, max: 40, value: 'gray' },
    { min: 41, max: 45, value: 'green' },
  ],
  myNumber: [],
  lotteryCount: 0,
  lotteryNumber: [1, 9, 17, 25, 33, 41],
  lotteryBonus: 45,
  winHistory: [],
  wallet: { used: 0, have: DEFAULT_MONEY },
  result: { 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 },
};
