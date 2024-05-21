<template>
  <div v-if="Alive" style="display:flex;flex-direction:column;">
    <div style="display:flex;flex-direction:row;align-items:center;background-color:grey;height:50px;width:100%;">
      <p style="margin-left:2%;width:82%;background-color:grey;font-size:1.4rem;">林海博客</p>
      <p @click="GoIndex" style="color:black;width:4%;background-color:grey;cursor:pointer;font-size:1.2rem;">首页</p>
      <router-link to="/Login" style="color:black;;width:4%;background-color:grey;font-size:1.2rem;">登录</router-link>
      <router-link to="/Register" style="color:black;width:4%;background-color:grey;font-size:1.2rem;">注册</router-link>
      <router-link to="/Self?page=1" v-slot:default="{navigate}" >
        <img v-if="avatar==null" src="http://114.55.38.94/api/api/avatar/default.png" @click="navigate" style="height:40px;width:40px;border-radius:50%;cursor:pointer;">
        <img v-else :src="'http://114.55.38.94/api/avatar/'+avatar" @click="navigate" style="height:40px;width:40px;border-radius:50%;cursor:pointer;">
      </router-link>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
import {getCurrentInstance,nextTick,onMounted} from "vue";
import VueCookies from "vue-cookies";
import router from "./Router/First";
export default {
  data(){
    return{
      id:"",
      avatar:null,
      Alive:true,
    }
  },
  setup(){
    const gci=getCurrentInstance()
    onMounted(()=> {
      document.documentElement.style.fontSize = document.documentElement.clientWidth / 100 + 'px';
      gci.appContext.config.globalProperties.$id=VueCookies.get("id")
      gci.appContext.config.globalProperties.$avatar=VueCookies.get("avatar")
      gci.data.id = gci.appContext.config.globalProperties.$id
      gci.data.avatar = gci.appContext.config.globalProperties.$avatar
      gci.appContext.config.globalProperties.$mitt1.on('Reload',()=>{
        gci.data.Alive=false
        gci.appContext.config.globalProperties.$id=VueCookies.get("id")
        gci.appContext.config.globalProperties.$avatar=VueCookies.get("avatar")
        gci.data.id = gci.appContext.config.globalProperties.$id
        gci.data.avatar = gci.appContext.config.globalProperties.$avatar
        nextTick(()=>{gci.data.Alive=true})
      })
    })
  },
  methods:{
    GoIndex(){router.push({path:'/Index',query:{'type':'all','page':1}})}
  }
}
</script>
<style>#app{font-family: Avenir, Helvetica, Arial, sans-serif;-webkit-font-smoothing: antialiased;-moz-osx-font-smoothing: grayscale;color: #2c3e50;}*{margin:0;padding:0;list-style: none;text-decoration:none;border:0;outline:none;}body{background:url('http://114.55.38.94/api/picture/back.png');background-attachment: fixed;}</style>