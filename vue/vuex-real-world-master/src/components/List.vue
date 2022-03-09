<template>
    <div>
        <template v-if="isEmpty && isFetching">
            <h2><i>{{loadingLabel}}</i></h2>
        </template>
        <template v-else-if="isEmpty && isLastPage">
            <h1><i>Nothing here!</i></h1>
        </template>
        <template v-else>
            <div>
                <!-- todo how to deal with v-for key -->
                <slot name="item" v-for="item in items" :item-props="item">
                </slot>
                <button v-if="pageCount > 0 && !isLastPage"
                    style="font-size: 150%;" @click="onLoadMoreClick" :disabled="isFetching">
                    {{isFetching ? 'Loading...' : 'Load More'}}
                </button>
            </div>
        </template>
    </div>
</template>
<script src="./List.js">
</script>
