<template>
    <div>
        <template v-if="!repo || !owner">
            <h1>
                <i>Loading {{name}} details...</i>
            </h1>
        </template>
        <template v-else>
            <div>
                <repo :owner="owner" :repo="repo" />
                <hr />
                <list :items="stargazers"
                    :on-load-more-click="handleLoadMoreClick"
                    :loading-label="`Loading stargazers of ${name}...`"
                    :is-fetching="stargazersPagination.isFetching"
                    :page-count="stargazersPagination.pageCount"
                    :nextPageUrl="stargazersPagination.nextPageUrl">
                    <template slot="item" scope="{itemProps:user}">
                        <user :user="user" :key="user.login" />
                    </template>
                </list>
            </div>
        </template>
    </div>
</template>
<script src="./RepoPage.js">
</script>
