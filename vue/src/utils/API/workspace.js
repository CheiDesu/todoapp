import {deleter, get, post, put} from "@/utils/http";

//update workspace
export function updateWork(config){
    put("/workspace",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//save workspace
export function saveWork(config){
    post("/workspace",config).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//delete workspace
export function delWork(){
    deleter("/workspace").then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//get all workspace by list
export function listWork(){
    get("/workspace/list").then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}

//get one workspace
export function getWorkById(ID){
    get("/workspace/"+ID).then(res=>{
        console.log(res);
    }).catch(err=>{
        console.log(err);
    })
}