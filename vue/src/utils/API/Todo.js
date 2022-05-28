import {deleter, get, post, put} from "@/utils/http";

//save todo
export function saveTodo(config){
    post("/todo",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//update todo
export function updateTodo(config){
    put("/todo",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//delete todo
export function delTodo(){
    deleter("/todo/delete").then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//get todo by list
export function getTodoList(title){
    get("/todo/list",{title:title}).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//get todo list in order
export function getOrderTodoList(title){
    get("/user/list/important",{title:title}).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//get todo by id
export function getTodoByID(ID){
    get("/todo/"+ID).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}