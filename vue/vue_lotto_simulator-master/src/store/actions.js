import { LOTTERY } from './actions-type';
import { lotto, delay } from '@/common/utils';

export default {
  [LOTTERY]: (context) => {
    // 돈이 없으면 추첨하지 않음
    if (!context.getters.isMoney) return;
    // 서버에서 추첨 번호를 가져온다고 가정하고 0.01초 딜레이
    const getLotto = async () => {
      await delay(10);
      return lotto();
    };
    // 새로 받아온 추첨 번호 등록
    getLotto().then((v) => {
      context.commit(`${LOTTERY}`, {
        number: v.numbers,
        bonus: v.bonus,
      });
    });
  },
};
