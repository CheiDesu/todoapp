//login
import {deleter, get, post, put} from "@/utils/http";

//login
export function login(username, passwd) {
    post("/user/login", {
        createTime: "",
        id: 0,
        password: passwd,
        permissions: 0,
        updateTime: "",
        username: username
    }).then(res => {
        console.log(res);
    }).catch(err => {
        console.log(err);
    })
}

//logout
export function logout(){
    post("/user/logout").then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//save
export function save(config){
    post("/user",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//updateEmp
export function updateEmp(config){
    put("/user",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//remove
export function removeUser(ID){
    deleter("/user",{ids:ID}).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//getPage
export function getPage(config){
    get("/user/page",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//getById
export function getById(ID){
    get("/user/"+ID).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}