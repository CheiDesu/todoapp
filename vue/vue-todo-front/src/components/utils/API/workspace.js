import {deleter, get, post, put} from "../http";

//update workspace
export function updateWork(config){
    return put("/workspace",config);
}

//save workspace
export function saveWork(config){
    return post("/workspace",config);
}

//delete workspace
export function delWork(){
    return deleter("/workspace");
}

//get all workspace by list
export function listWork(){
    return get("/workspace/list");
}

//get one workspace
export function getWorkById(ID){
    return get("/workspace/"+ID);
}