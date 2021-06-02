import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";
import Home from "@/views/Home";
import Success from "@/views/Success";
import WelCome from "@/views/WelCome";
import UserList from "@/views/UserList";



Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
    {
        path: '/Login',
        name: 'Login',
        component: Login
    },
    {
        path: '/Home',
        name: 'Home',
        component: Home,
        redirect: '/WelCome',
        children:[
            {
                path: '/WelCome',
                name: 'WelCome',
                component: WelCome
            },
            {
                path: '/user',
                name: 'UserList',
                component: UserList
            },
        ]
    },
    {
        path: '/Success',
        name: 'Success',
        component: Success
    },




]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location,onResolve,onReject) {
    if(onReject || onResolve) return originalPush.call(this,location,onResolve,onReject)
    return originalPush.call(this,location).catch(err => err)
}


//挂载路由导航
router.beforeEach((to,from,next)=>{
    // to 将要访问
    // from 从哪里访问
    //next 接着做什么事
    if(to.path == "/") return next();

    //获取user
    const userFlag = window.sessionStorage.getItem("user");
    if (!userFlag) return next("/");
    next();//符合要求
})
export default router
