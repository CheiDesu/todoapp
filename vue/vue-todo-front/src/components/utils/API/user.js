//login
import {deleter, get, post, put} from "../http";

//login
export function login(username, passwd) {
    return post("/user/login", {
        createTime: "",
        id: 0,
        password: passwd,
        permissions: 0,
        updateTime: "",
        username: username
    })
}

//logout
export function logout(){
    return post("/user/logout");
}

//save
export function save(config){
    return post("/user",config);
}

//updateEmp
export function updateEmp(config){
    return put("/user",config);
}

//remove
export function removeUser(ID){
    return deleter("/user",{ids:ID});
}

//getPage
export function getPage(config){
    return get("/user/page",config);
}

//getById
export function getById(ID){
    return get("/user/"+ID);
}