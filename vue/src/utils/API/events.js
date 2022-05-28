import {deleter, get, post, put} from "@/utils/http"

//get all events by list
export function getevents() {
    get("/events/list"
    ).then(res => {
        console.log(res);
    }).catch(err => {
        console.log(err);
    })
}

//get event by id
export function geteventById(ID) {
    get("/events/"+ID
    ).then(res => {
        console.log(res);
    }).catch(err => {
        console.log(err);
    })
}

//add events
export function addEvent(config){
    post("/events",config
    ).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//delete events
export function delEveTodo(config){
    deleter("/events/deleteTodo",config
    ).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//update event
export function updateEve(config){
    put("/events",config
    ).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//add todo
export function addEveTodo(config){
    put("/events",config
    ).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}