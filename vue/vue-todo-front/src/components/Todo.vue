<template>
  <div class="main-wrapper">
    <section class="section pb-0 main-section bg-gradient-info">
      <navbar/>
      <main class="container card shadow shadow-lg--hover mt-3" id="todolist">
        <div class="row mb-3">
          <div class="col-6">
            <h1>{{ title }}</h1>
            <p>{{ userData.name }}</p>
          </div>
          <div class="col-6 text-right">
            <div class="user-icon">
              <div class="dropdown">
                <img
                  :src="getUserData.profileImage ? getUserData.profileImage : 'dist/assets/img/user-avatar.png' "
                  id="dropdownMenuButton"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                  class="dropdown-toggle img-fluid"
                />
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                  <a class="dropdown-item" href="#" v-if="userLoggedIn" @click="googleLogout">Logout</a>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click="toggleFullScreen"
                  >{{ isFullScreen ? 'Exit Full Screen' : 'Full Screen' }}</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-4">
            <span class="badge badge-primary">Total : {{ todos.length }}</span>
            <!-- <h6 class="count total">Total : {{todos.length}}</h6> -->
          </div>
          <div class="col-4">
            <span class="badge badge-success">Success : {{ completedTodos }}</span>
            <!-- <h6 class="count completed">Completed : </h6> -->
          </div>
          <div class="col-4">
            <span class="badge badge-warning">Pending : {{ todos.length - completedTodos }}</span>
            <!-- <h6 class="count pending">Pending : {{pendingTodos.length}}</h6> -->
          </div>
          <div class="col-md-12 mt-3">
            <div class="form-group">
              <input
                type="text"
                name="newTodo"
                class="add-todo-field form-control"
                placeholder="Enter New To-Do"
                v-on:keydown.enter="addnewTodo($event)"
                autocomplete="off"
                v-model.trim="newTodoText"
              />
              <i
                class="fa fa-arrow-right submit-icon"
                @click="addnewTodo($event)"
                aria-hidden="true"
              ></i>
            </div>
          </div>
        </div>

        <ul id="todo-list">
          <VuePerfectScrollbar class="scroll-area">
            <draggable handle=".handle-wrapper" ghost-class="ghost" :list="getTodos">
              <transition-group>
                <li
                  class="todo-item"
                  :class="todo.finished ? 'done': 'undone'"
                  v-for="(todo,key) in todos"
                  :key="key"
                  @click="showDetail(todo, $event)">
                  >
                  <div class="handle-wrapper">
                    <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                    <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                  </div>
                  <div class="todo-info">
                    <span class="label todo-title">{{ todo.title }}</span>
                  </div>
                  <div class="todo-priority">
                    <div class="priority-dot" :style="{background:priorityColor[todo.important-1]}"></div>
                    <span>{{ important_level[todo.important - 1] }}</span>
                  </div>
                  <div class="todo-tags">
                    <!--                    <datetime-picker-->
                    <!--                      timeType="second"-->
                    <!--                      :value="todo.deadline"-->
                    <!--                      @input="val=>{changeDate(val,key)}"-->
                    <!--                      min="2018-08-24 02:10:02"-->
                    <!--                      :timeStr="timeStr"-->
                    <!--                    />-->
                    <!-- <div class="dropdown-menu" v-if="todo.tags.length <= 0">
                      <div class="dropdown-header">
                        <i class="fa fa-tag" aria-hidden="true"></i> Tags
                      </div>
                      <div class="no-tags">No tags attached</div>
                    </div>
                    <div class="dropdown-menu" v-if="todo.tags.length > 0">
                      <div class="dropdown-header">
                        <i class="fa fa-tag" aria-hidden="true"></i> Tags
                      </div>
                    </div> -->
                  </div>

                  <span class="todo-date">{{ todo.deadline }}</span>
                  <span class="todo-date"></span>
                  <div class="actions">
                    <button
                      type="button"
                      class="btn-picto"
                      @click.stop="completeTodo(key)"
                      :aria-label="todo.finished ? 'Undone' : 'Done'"
                      :title="todo.finished ? 'Undone' : 'Done'"
                    >
                      <i
                        aria-hidden="true"
                        class="material-icons"
                      >{{ todo.finished ? 'check_box' : 'check_box_outline_blank' }}</i>
                    </button>
                    <button
                      @click.stop="removeTodo(todo.id)"
                      type="button"
                      aria-label="Delete"
                      title="Delete"
                      class="btn-picto"
                    >
                      <i aria-hidden="true" class="material-icons">delete</i>
                    </button>
                  </div>
                </li>
              </transition-group>
            </draggable>
          </VuePerfectScrollbar>
        </ul>
        <div class="todo-footer" v-if="todos.length >0">
          <ul>
            <div class="actions">
              <button
                @click="clearTodos"
                type="button"
                aria-label="Delete"
                title="Delete"
                class="btn-picto"
              >
                Clear All
                <i aria-hidden="true" class="material-icons">delete</i>
              </button>
            </div>
          </ul>
        </div>
      </main>
    </section>
    <notifications group="foo" position="top right" class="my-style" width="400"/>
    <todoDetailModal/>
  </div>
</template>


<script>
import VuePerfectScrollbar from "vue-perfect-scrollbar";
import draggable from "vuedraggable";
import firebase from "firebase";
import navbar from "./Navbar";
import todoDetailModal from "./TodoDetailModal";
import {Bus} from "./utils/bus";
import vueStore from "./store/index";
import {mapActions, mapGetters} from "vuex";

const uuidv4 = require("uuid/v4");
import {logout} from "./utils/API/user";
import {updateTodo} from "./utils/API/Todo";
import {addEveTodo, delEveTodo,geteveTodoById} from "./utils/API/events";


export default {
  components: {
    VuePerfectScrollbar,
    navbar,
    draggable,
    todoDetailModal,
    Bus
  },
  name: "Todo",
  data: function () {
    return {
      title: "",
      todos: [],
      completedTodos: 0,
      pendingTodos: 0,
      checkkAll: false,
      newTodoText: "",
      isFullScreen: false,
      elem: document.documentElement,
      userLoggedIn: false,
      priorityColor: ["#11cdef", "#5e72e4", "#ffbb33", "#f5365c"],
      userData: {},
      userInfo: "",
      important_level: ["不重要不紧急", "紧急不重要", "重要不紧急", "紧急且重要"],
      timeStr: ['hour', 'min', 'sec'],
    };
  },
  mounted() {
  },
  watch: {
    isFullScreen: function (newValue, oldValue) {
    }
  },
  created() {
    this.userLoggedIn = true;
    this.id = this.$route.query.id;
    let that = this;
    //控制全屏开关
    document.onfullscreenchange = function (event) {
      if (!that.isFullScreen) {
        that.isFullScreen = true;
      } else {
        that.isFullScreen = false;
      }
    };
    this.updateTodos();

    //从后台请求todos
    this.getAllTodos();

    console.log("getTodos ", this.getTodos);

  },
  computed: {
    ...mapGetters(["getTodos", "getUserData"])
  },
  methods: {
    //从input框中获取更改的deadline，并利用key定位todo，进行todo的update
    //该功能已移植到tododetails中
    // changeDate: function (val, key) {
    //   this.todos[key].deadline = val;
    //   console.log(this.todos[key].deadline);
    //   updateTodo(this.todos[key]);
    //   this.updateTodos();
    // },
    ...mapActions(["createNewTodo", "markAsComplete", "deleteTodo"]),
    toggleFullScreen() {
      !this.isFullScreen ? this.openFullscreen() : this.closeFullscreen();
    },
    getAllTodos() {
      geteveTodoById(this.id).then((res) => {
        console.log(res);
        //赋值前端数据
        this.todos = res.data.normalTodoList;
        this.userData = {userId: res.data.userId};
        this.title = res.data.title;
        console.log(this.todos);

        //获取todos的完成情况
        if (this.todos.length > 0) {
          let sum = 0;
          this.todos.forEach(item => {
            console.log(sum);
            sum += item.finished ? 1 : 0;
            console.log(sum);

          })
          this.completedTodos = sum;
        }
      })
    },
    stopTheEvent(event) {
      event.stopPropagation();
    },
    openFullscreen() {
      if (this.elem.requestFullscreen) {
        this.elem.requestFullscreen();
      } else if (this.elem.mozRequestFullScreen) {
        /* Firefox */
        this.elem.mozRequestFullScreen();
      } else if (this.elem.webkitRequestFullscreen) {
        /* Chrome, Safari and Opera */
        this.elem.webkitRequestFullscreen();
      } else if (this.elem.msRequestFullscreen) {
        /* IE/Edge */
        this.elem.msRequestFullscreen();
      }
    },
    closeFullscreen() {
      if (document.exitFullscreen) {
        document.exitFullscreen();
      } else if (document.mozCancelFullScreen) {
        /* Firefox */
        document.mozCancelFullScreen();
      } else if (document.webkitExitFullscreen) {
        /* Chrome, Safari and Opera */
        document.webkitExitFullscreen();
      } else if (document.msExitFullscreen) {
        /* IE/Edge */
        document.msExitFullscreen();
      }
    },
    googleLogout() {
      logout().then((res) => {
        console.log(res);
        if (res.code == 1) {
          //退出
          //跳转到登录页面
          this.$router.push("/login");
        }

      })
      //调用接口

    },
    showNotification(msg, alertType) {
      this.$notify({
        group: "foo",
        text: `<div class="alert ${alertType}" role="alert">${msg}</div> `,
        position: "top left",
        duration: 10000
      });
    },
    showDetail(todoItem, e) {
      console.log(
        "$(e.target).hasClass('.fa-tag') ",
        $(e.target).hasClass("fa-tag")
      );

      if ($(e.target).hasClass("fa-tag")) {
        console.log("clicked ", e.target);
        return false;
      } else {
        e.preventDefault();
        // e.stopPropagation();
        Bus.$emit("showDetailedTaskModal", todoItem);
      }
    },
    clearTodos() {
      this.$store.state.todos = [];
      this.updateTodos();
    },
    updateTodos() {
      this.getAllTodos();
      this.completedTodos = this.getTodos.filter(item => item.finished);
      this.pendingTodos = this.getTodos.filter(item => !item.finished);
    },
    removeTodo(key) {
      console.log(key);
      // this.deleteTodo(key);
      // if(!(key instanceof Array)){
      //     key=[].push(key);
      //     console.log("aaaa");
      // }
      delEveTodo(key).then(res => {
        //重新获取todos
        this.getAllTodos();
      }).catch(err => {
        console.log(err);
      })
      //自己写，不用vuex

      this.updateTodos();
    },
    // 标识完成
    completeTodo(key) {
      this.todos[key].finished = !this.todos[key].finished;
      updateTodo(this.todos[key]);
      this.getAllTodos();
      this.updateTodos();
    },
    addnewTodo(e) {
      if (this.newTodoText.length > 0) {
        e.preventDefault();
        let date = this.$moment(new Date()).format("YYYY-MM-DD HH:MM:SS");
        let newTodo = {
          createTime: "",
          //获取events的id
          id: this.$route.query.id,
          normalTodoList: [
            {
              createTime: "",
              deadline: date,
              finished: false,
              id: 0,
              important: 1,
              title: this.newTodoText,
              updateTime: "",
              userId: this.userData.userId
            }
          ],
          title: "",
          updateTime: "",
          //获取当前用户的id
          userId: this.userData.userId,
          workspaceId: 0
        };

        console.log(newTodo);

        //调用后端接口
        addEveTodo(newTodo).then(res => {
          console.log(res);
          // 刷新todo
          this.getAllTodos();
        })

        this.newTodoText = "";
        this.updateTodos();
      }
    },
    checkLogin() {
    }
  }
};
</script>
<style scoped>
section.main-section {
  height: 100%;
}

.card-body {
  text-align: left;
}

@keyframes strikeitem {
  to {
    width: calc(100% + 1rem);
  }
}

.count {
  font-size: 16px;
}

.count.finished {
  text-align: center;
}

.count.pending {
  text-align: right;
}

#todolist {
  margin: 4rem auto;
  padding: 2rem 3rem 3rem;
  /* max-width: 750px; */
  background: #fff;
  color: #32325d;
  box-shadow: 0 0 19px 10px rgba(100, 100, 100, 0.2);
  overflow: visible;
}

#todolist .row {
  text-align: left;
}

#todolist h1 {
  /*text-align:center;*/
  font-weight: normal;
  font-size: 2.6rem;
  letter-spacing: 0.05em;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
}

#todolist h1 span {
  display: block;
  font-size: 0.8rem;
  margin-bottom: 0.7rem;
  margin-left: 3px;
  margin-top: 0.2rem;
}

#todolist .emptylist {
  margin-top: 2.6rem;
  text-align: center;
  letter-spacing: 0.05em;
  font-style: italic;
  opacity: 0.8;
}

#todolist ul {
  margin-top: 1rem;
  list-style: none;
  padding: 0;
}

#todolist .todolist-move {
  transition: transform 1s;
}

#todolist li {
  display: flex;
  margin-top: 5px;
  padding: 0.5rem 1rem;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.1);
  text-align: left;
  padding-left: 5px;
}

#todolist .actions {
  flex-shrink: 0;
}

#todolist .label {
  position: relative;
  transition: opacity 0.2s linear;
}

#todolist .label.todo-title {
  /* display: block; */
  color: #7a797e;
}

#todolist .done .label,
#todolist .done .todo-priority,
#todolist .done .todo-tags,
#todolist .done .todo-date,
#todolist .done .actions {
  opacity: 0.6;
}

#todolist .done .label:before {
  content: "";
  position: absolute;
  top: 50%;
  left: -0.5rem;
  display: block;
  width: 0%;
  height: 1px;
  background: #fff;
  animation: strikeitem 0.3s ease-out 0s forwards;
}

#todolist .btn-picto {
  border: none;
  background: none;
  -webkit-appearance: none;
  cursor: pointer;
  color: #11cdef;
}

/* FORM */
form {
  margin-top: 3rem;
  display: flex;
  flex-wrap: wrap;
}

form label {
  min-width: 100%;
  margin-bottom: 0.5rem;
  font-size: 1.3rem;
}

.add-todo-field {
  border-radius: 0;
  border: none;
  background: transparent;
  border-bottom: 1px solid #11cdef;
  color: #32325d;
  padding-right: 50px;
}

.add-todo-field:focus {
  box-shadow: none;
  background: transparent;
  border: none;
  border-bottom: 1px solid #11cdef;
}

form input {
  flex-grow: 1;
  border: none;
  background: #f7f1f1;
  padding: 0 1.5em;
  font-size: initial;
}

.get-btn {
  padding: 0 1.3rem;
  border: none;
  background: #11cdef;
  color: #fff;
  text-transform: uppercase;
  font-weight: bold;
  border: 1px solid rgba(255, 255, 255, 0.3);
  margin-left: 5px;
  cursor: pointer;
  transition: background 0.2s ease-out;
}

.get-btn:hover {
  background: #11cdef;
}

form input,
.get-btn {
  font-family: "Quicksand", sans-serif;
  height: 3rem;
}

/* TOOGLE COMPONENT */
.togglebutton-wrapper {
  margin-top: 1em;
}

.togglebutton-wrapper label {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.togglebutton-wrapper input {
  position: absolute;
  left: -9999px;
}

.togglebutton-wrapper .togglebutton-label {
  font-size: 0.8rem;
  letter-spacing: 0.1em;
}

.togglebutton-wrapper .tooglebutton-box {
  position: relative;
  display: block;
  margin-left: 0.6em;
  width: 38px;
  height: 22px;
  background: white;
  /*border:1px solid black;*/
  border-radius: 999px;
  cursor: pointer;
}

.togglebutton-wrapper .tooglebutton-box:before {
  content: "";
  position: absolute;
  top: 2px;
  left: 2px;
  display: block;
  width: 18px;
  height: 18px;
  /*border:1px solid #11cdef;*/
  border-radius: 50%;
  background: #11cdef;
  opacity: 0.7;
  transition: all 0.2s ease-in-out;
}

.togglebutton-wrapper.togglebutton-focus .tooglebutton-box {
  box-shadow: 0px 0px 0px 3px rgba(0, 0, 0, 0.1);
}

.togglebutton-wrapper.togglebutton-checked .tooglebutton-box:before {
  left: calc(100% - 4px - 16px);
  opacity: 1;
}

.scroll-area {
  position: relative;
  margin: auto;
  width: 100%;
  max-height: 450px;
  min-height: 350px;
}

.scroll-area .ps__scrollbar-y-rail {
  background: rgb(232, 232, 232);
  opacity: 1;
  width: 10px;
  border-radius: 8px;
}

.todo-footer {
  position: absolute;
  bottom: 0;
}

.todo-footer ul {
  display: flex;
}

.todo-footer .actions {
  display: flex;
}

.todo-footer .actions button {
  display: flex;
  justify-content: center;
  align-items: center;
}

.main-wrapper {
  height: 100%;
}

@media screen and (min-width: 370px) {
  section.main-section {
    padding: 0;
  }

  #todolist {
    padding: 1.5rem;
    margin: 1rem auto;
  }

  #todolist h1 {
    font-size: 1.5rem;
  }

  .count.finished {
    text-align: left;
  }

  .count.pending {
    text-align: left;
  }
}

.fa.submit-icon {
  position: absolute;
  right: 30px;
  top: 12px;
  font-size: 25px;
  cursor: pointer;
}

.badge {
  font-size: 80%;
}

.navbar-dark .navbar-brand {
  font-size: 20px;
}

.todo-info {
  flex: 1 70%;
}

.todo-date {
  font-size: 12px;
  color: #8898aa;
  flex: 1 10%;
}

.my-style .vue-notification .notification-title {
  color: red !important;
}

#todolist li.todo-item:hover {
  background-color: #f4f5f7;
  cursor: pointer;
}

.todo-item .handle-wrapper {
  width: 20px;
  color: #b5b5b5;
  opacity: 0;
}

#todolist li.todo-item:hover .handle-wrapper {
  opacity: 1;
}

.handle-wrapper:hover {
  cursor: move;
}

.ghost {
  border-bottom: 1px solid #11cdef;
}

.user-icon img {
  width: 40px;
  cursor: pointer;
  border-radius: 50%;
}

.label.todo-description {
  font-size: 16px;
  background: #dedede;
  padding: 5px 8px;
  border-radius: 6px;
  color: #000;
  line-height: normal;
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.badge.badge-pill.badge-info {
  font-size: 11px;
  margin-right: 5px;
  text-transform: capitalize;
  line-height: normal;
  padding: 3px 10px;
}

@media screen and (max-width: 370px) {
  #todolist {
    max-width: 400px;
    padding: 1rem;
  }
}

@media screen and (max-width: 768px) {
  #todolist {
    max-width: 650px;
    padding: 1.25rem;
  }
}

.todo-priority {
  flex: 1 20%;
  display: flex;
  align-items: center;
}

.todo-tags {
  flex: 1 10%;
  text-align: center;
}

.todo-tags .fa-tag[data-toggle="dropdown"] {
  color: #7a797e;
  height: 30px;
  display: flex;
  width: 30px;
  align-items: center;
  justify-content: center;
  margin: auto;
  transition: all 0.2s;
  border-radius: 50%;
}

.todo-tags .fa-tag[data-toggle="dropdown"]:hover {
  background: rgba(17, 205, 239, 0.5);
  color: #fff;
  transform: scale(1.2);
}

.todo-desc-icon {
  width: 20px;
}

.priority-dot {
  height: 10px;
  width: 10px;
  background: #333;
  border-radius: 50%;
  margin-right: 10px;
}

.todo-tags .dropdown-menu.show .badge-pill.badge {
  font-size: 12px;
  text-transform: capitalize;
  padding: 8px 8px;
  display: block;
  margin: 10px;
  width: 120px;
  margin: 8px auto;
}

.dropdown-header {
  color: #7a797e;
  text-align: center;
  font-size: 16px;
  padding: 4px;
  text-transform: none;
  font-weight: 600;
  border-bottom: 1px solid #c5c5c5;
  margin: 0 20px;
}

.no-tags {
  text-align: center;
  font-size: 14px;
  margin: 10px 0;
}

@media (max-width: 767px) {
  #todolist li {
    flex-wrap: wrap;
  }

  .todo-info {
    flex: 1 70%;
  }

  .todo-priority {
    flex: 1 25%;
    display: flex;
    align-items: center;
    order: 2;
    flex-shrink: 0;
    flex-grow: 0;
  }

  .todo-tags {
    flex: 1 10%;
    text-align: center;
    order: 3;
    flex-grow: 0;
  }

  .todo-date {
    flex: 1 10%;
    align-items: center;
  }

  #todolist .actions {
    flex-shrink: 0;
  }
}
</style>
