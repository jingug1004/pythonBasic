<template>
  <div class="User">
      <router-link :to="`/${user.login}`">
          <img :src="user.avatarUrl" :alt="user.login" width="72" height="72">
          <h3>
              {{user.login}} <span v-if="user.name">({{user.name}})</span>
          </h3>
      </router-link>
  </div>
</template>
<script src="./User.js">
</script>

