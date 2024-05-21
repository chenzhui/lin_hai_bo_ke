<template>
  <div style="display:flex;flex-direction:column;margin-top:3rem;background-color:white;width:50rem;margin-left:25rem;">
    <button style="width:8rem;height:2rem;border-radius:0.2rem;background-color:deepskyblue;color:white;font-size:1.5rem;margin-left:20rem;margin-top:1rem;">登录界面</button>
    <div style="display:flex;flex-direction:column;height:5rem;margin-top:2rem;">
      <input @keydown.enter="GoSecondInput" placeholder="输入您的账号" type="text" style="height:3rem;border-radius:5px;border:solid 0.1rem lightblue;margin-left:15rem;width:20rem;" v-model="phone" @blur="ExamePhone()" @focus="ShowPhone()" :class="{'focus':index==1,'wrong':phoneNull}" >
      <p v-if="phoneNull" style="color:red;font-size:1rem;">账号不能为空</p>
    </div>
    <div style="display:flex;flex-direction:column;height:5rem;">
      <input type="password" @keydown.enter="LoginUser" ref="SecondInput" placeholder="输入您的密码"  style="height:3rem;border-radius:5px;border:solid 0.1rem lightblue;margin-left:15rem;width:20rem;" v-model="password" @blur="ExamePassword()"  @focus="ShowPassword()" :class="{'focus':index==2,'wrong':passwordNull}">
      <p v-if="passwordNull" style="color:red;font-size:1rem;">密码不能为空</p>
    </div>
      <button style="width:4rem;height:2rem;cursor:pointer;border-radius:0.2rem;background-color:deepskyblue;color:white;font-size:1rem;margin-left:22rem;" v-on:click="LoginUser">登录</button>
  </div>
</template>
<script>
import axios from 'axios'
import router from "../Router/First";
import {getCurrentInstance, onMounted, ref} from "vue";
export default {
setup(){
  let SecondInput=ref(null)
  const gci=getCurrentInstance()
  onMounted(()=>{
    document.title='登录'
    document.documentElement.style.fontSize = document.documentElement.clientWidth/100 + 'px';
    gci.data.mitt1=gci.appContext.config.globalProperties.$mitt1
  })
  return{
    SecondInput
  }
},
data(){
  return {
    noClick: true,
    phone:"",
    password:"",
    phoneNull:false,
    passwordNull:false,
    index:0,
  }
},
methods:{
  LoginUser(){
      if(this.phone==null){
        alert("账号不能为空")
        return false
      }
      if(this.password==null){
        alert("密码不能为空")
        return false
      }
      let that=this
      let login=new URLSearchParams;login.append("phone",this.phone);login.append("password",this.password);
      axios.post("http://114.55.38.94/api/Login",login).then(function (res){
        if(res.data.data=="登录成功"){
          router.push('/Index')
          that.mitt1.emit('Reload')
          alert("登录成功")
        }else{
          alert(res.data.data)
        }
      })
  },
  ExamePhone(){
    this.index=0
    if(this.phone==undefined){
      this.phoneNull=true
    }else{
      this.phoneNull=false
    }
  },
  ExamePassword(){
    this.index=0
    if(this.password==undefined){
      this.passwordNull=true
    }else{
      this.passwordNull=false
    }
  },
  ShowPhone(){
    this.index=1
    this.phoneNull=false
  },
  ShowPassword(){
    this.index=2
    this.passwordNull=false
  },
  GoSecondInput(){
    this.SecondInput.focus()
  }
},
}
</script>
<style scoped>*{margin:0;padding:0;list-style: none;text-decoration: none;border:0;outline: none;font-size:1rem;}
.focus{border:solid 1px deepskyblue;font-size:1rem;}.wrong{border:solid 1px red;}</style>