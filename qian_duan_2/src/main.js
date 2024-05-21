import {createApp} from 'vue';
import App from './App.vue';
import router from "./Router/First";
import axios from "axios";
import mitt from "mitt";
import VueCookies from "vue-cookies";
import Lazy from "@/JS/Lazy";
//import videoPlay from 'vue3-video-play'
//import 'vue3-video-play/dist/style.css'
const app = createApp(App)
axios.defaults.baseUrl=""
axios.interceptors.request.use(config=>{
    const token=VueCookies.get("token")
    config.headers.token=token
    return config
})
axios.interceptors.response.use(
    response=>{
        if(response.status==200){
            return Promise.resolve(response);
        }
    },error=>{
        if(error.response.status==401){
            alert("请先登录")
            router.push("/Login")
        }
    })
axios.defaults.withCredentials=true
app.config.globalProperties.$mitt1 = new mitt()//全局变量一定要放在这
app.use(router)
//app.directive('console',{mounted(){console.log("A")}})
const lazy = new Lazy()
app.directive('lazy',{mounted(el,binding){lazy.add(el,binding)}})
//app.use(videoPlay)
app.mount('#app')


