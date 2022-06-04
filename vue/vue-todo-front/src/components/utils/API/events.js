import {deleter, get, post, put} from "../http"

//get all events by list
export function getevents() {
    return get("/events/list");
}

//get event by id
export function geteventById(ID) {
    return get("/events/"+ID);
}

//add events
export function addEvent(config){
    return post("/events",config);
}

//delete events
export function delEveTodo(config){
    return deleter("/events/deleteTodo",config);
}

//update event
export function updateEve(config){
    return put("/events",config);
}

//add todo
export function addEveTodo(config){
    return put("/events",config);
}