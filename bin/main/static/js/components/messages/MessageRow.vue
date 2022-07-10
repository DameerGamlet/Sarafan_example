<template>
    <div>
        <div style="margin-top: 5px;"></div>
        <i>({{ message.id }})</i> {{ message.text }}
        <span style="position: absolute; right: 0">
            <input type="button" value="Редакт." @click="edit" />
            <input type="button" value="X" @click="del" />
        </span>
    </div>
</template>

<script>
    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages'],
        methods: {
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.deleteMessage(this.message)
            }
        }
    }
</script>

<style>
</style>