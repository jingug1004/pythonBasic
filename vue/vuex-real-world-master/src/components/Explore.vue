<template>
    <div>
        <p>Type a username or repo full name and hit 'Go':</p>
        <input ref="input" :value="value" @keyup.enter="doneEdit">
        <button @click="handleGoClick">Go!</button>
    </div>
</template>
<script src="./Explore.js">

</script>
