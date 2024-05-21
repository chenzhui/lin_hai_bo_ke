import {createRouter, createWebHashHistory} from "vue-router";
const routes=[
    {path:'/',redirect:'/Index'},
    {path: '/Index', component: () => import('../Views/Index.vue'),},
    {path: '/Blog', component: () => import('../Views/Blog.vue'),},
    {path: '/Login', component: () => import('../Views/Login.vue'),},
    {path: '/Register', component: () => import('../Views/Register.vue'),},
    {path: '/Self', component: () => import('../Views/Self.vue'),},
    {path:'/Follow',component:()=>import('../Views/Follow.vue')},
    {path: '/Fixing',component:()=>import('../Views/Fixing.vue')},
    {path: '/Release',component:()=>import('../Views/Release.vue')},
    {path: '/Collection',component:()=>import('../Views/Collection.vue')},
    {path: '/Search',component:()=>import('../Views/Search.vue')},
    {path: '/ChangePassword',component:()=>import('../Views/ChangePassword.vue')},
    {path: '/ChangeName',component:()=>import('../Views/ChangeName.vue')},
    {path: '/Master/User',component:()=>import('../Views/Master_user.vue')},
    {path: '/Master/Type',component:()=>import('../Views/Master_type.vue')},
    {path: '/Letter',component:()=>import('../Views/Letter.vue')},
    {path: '/ChatRoom',component:()=>import('../Views/ChatRoom.vue')}
]
const router=createRouter({
    history:createWebHashHistory(),
    routes,
})
export default router
