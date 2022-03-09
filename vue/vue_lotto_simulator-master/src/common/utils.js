export const lotto = () => {
  const numList = Array.apply(null, { length: 45 }).map((v, n) => n + 1);
  const lottoList = [];
  let i = -1;
  while (++i < 7) lottoList.push(
    numList.splice(Math.floor(Math.random() * numList.length), 1)[0],
  );
  return {
    numbers: lottoList.slice(1).sort((a, b) => a - b),
    bonus: lottoList[0],
  };
};

export const numberWithCommas = num =>
  num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');

export const delay = ms => new Promise(resolve => setTimeout(resolve, ms));
