<template>
  <div :class="$style.app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
};
</script>

<style module>
.app {
  display: flex;
  justify-content: center;
}
</style>
