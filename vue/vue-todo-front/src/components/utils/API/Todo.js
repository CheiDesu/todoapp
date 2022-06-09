import {deleter, get, post, put} from "../http";

//save todo
export function saveTodo(config){
    return post("/todo",config);
}

//update todo
export function updateTodo(config){
    return put("/todo",config);
}
//delete todo
export function delTodo(id){
    return deleter("/todo/deleteBatch",id);
}

//get todo by list
export function getTodoList(title){
    return get("/todo/list",{title:title});
}

//get todo list in order
export function getOrderTodoList(title){
    return get("/user/list/important",{title:title});
}

//get todo by id
export function getTodoByID(ID){
    return get("/todo/"+ID);
}
