import {createApp} from 'vue'
import App from './App.vue'

createApp(App).mount('#app')
import {addEvent, geteventById, getevents,} from "@/utils/API/events";
import {login} from "@/utils/API/user";

login("admin", "admin")

getevents()

geteventById(2)

addEvent({
    createTime: "",
    id: 0,
    normalTodoList: [
        {
            createTime: "",
            deadline: "",
            finished: true,
            id: 0,
            important: 0,
            title: "",
            updateTime: ""
        }
    ],
    title: "",
    updateTime: ""
})

