<template>
    <div class="repo">
        <h3>
            <router-link :to="`/${login}/${name}`">
                {{name}}
            </router-link>
            by
            <router-link :to="`/${login}`">
                {{login}}
            </router-link>
        </h3>
        <p v-if="description">{{description}}</p>
    </div>
</template>
<script src="./Repo.js">
</script>

