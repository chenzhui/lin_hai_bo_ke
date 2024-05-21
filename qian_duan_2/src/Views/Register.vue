<template>
<div style="display:flex;flex-direction:column;margin-top:3rem;background-color:white;width:50rem;margin-left:20rem;border-radius:2rem;">
    <p style="background-color:deepskyblue;color:white;font-size:1.4rem;width:8rem;text-align:center;border-radius:5px;margin-top:10px;height:3rem;line-height:3rem;margin-left:20rem;">注册页面</p>
    <div style="display:flex;flex-direction:column;height:5rem;margin-left:15rem;margin-top:2rem;">
      <input @keydown.enter="GoSecondInput" :class="{'focus':this.judge==1,'wrong':!phoneOk}" style="width:20rem;height:3rem;border-radius:5px;font-size:1rem;border:solid 0.1rem lightblue;" v-model="phone" @blur="Blur" @focus="ShowPhone" @input="JudgePhone" placeholder="输入您的电话(账号)">
      <div v-if="judge==1" style="display:flex;flex-direction:column;height:1.5rem;margin-left:5rem;">
        <div style="display:flex;">
          <img v-if="phoneOk" src="http://114.55.38.94/api/picture/green.png">
          <img v-else src="http://114.55.38.94/api/picture/info.png">
          <p style="margin-left:0.2rem;font-size:1rem;">电话长度应为11</p>
        </div>
      </div>
      <p v-else-if="!phoneOk" style="color:red;font-size:1rem;margin-left:5rem;">电话长度不符</p>
    </div>
    <div style="display:flex;flex-direction:column;height:5rem;margin-left:15rem;">
      <input ref="SecondInput" @keydown.enter="GoThridInput" :class="{'focus':this.judge==2,'wrong':!nameOk}"  style="width:20rem;height:3rem;border-radius:5px;font-size:1rem;border:solid 0.1rem lightblue;" v-model="name" @blur="Blur" @focus="ShowName" @input="JudgeName" placeholder="输入您的昵称">
      <div v-if="judge==2" style="display:flex;flex-direction:column;">
        <div style="display:flex;">
          <img v-if="nameOk" src="http://114.55.38.94/api/picture/green.png">
          <img v-else src="http://114.55.38.94/api/picture/info.png">
          <p style="margin-left:10px;font-size:1rem;">昵称长度应小于等于10且不为空</p>
        </div>
      </div>
      <p v-else-if="!nameOk" style="color:red;font-size:1rem;margin-left:5rem;">昵称长度不符</p>
    </div>
    <div style="display:flex;flex-direction:column;height:5rem;margin-left:15rem;">
      <input ref="ThridInput" @keydown.enter="GoFourthInput" :class="{'focus':this.judge==3,'wrong':!passwordOk}" style="width:20rem;height:3rem;border-radius:5px;font-size:1rem;border:solid 0.1rem lightblue;" v-model="password" @blur="Blur" @focus="ShowPassword" @input="JudgePassword" placeholder="输入您的密码" type="password">
      <div v-if="judge==3" style="display:flex;">
        <div style="display:flex;">
          <img v-if="!hasSpace" src="http://114.55.38.94/api/picture/green.png">
          <img v-else src="http://114.55.38.94/api/picture/info.png">
          <p style="margin-left:10px;font-size:1rem;">不能包括空格</p>
        </div>
        <div style="display:flex;margin-left:1rem;">
          <img v-if="lengthOk" src="http://114.55.38.94/api/picture/green.png">
          <img v-else src="http://114.55.38.94/api/picture/info.png">
          <p style="margin-left:10px;font-size:1rem;">长度为6-12个字符</p>
        </div>
      </div>
      <p v-else-if="!passwordOk" style="color:red;font-size:1rem;margin-left:5rem;">密码不符合条件</p>
    </div>
    <div style="display:flex;flex-direction:column;height:5rem;margin-left:15rem;">
      <div style="display:flex;">
        <input ref="FourthInput" @keydown.enter="RegisterUser" :class="{'focus':this.judge==4,'wrong':!keysOk}" style="width:10rem;height:3rem;border-radius:5px;font-size:1rem;border:solid 0.1rem lightblue;" v-model="keys" @blur="Blur" @focus="ShowKeys" @input="JudgeKeys" placeholder="输入短信验证码" type="text">
        <button :disabled="isSixty" @click="Send($event)" style="margin-left:2rem;width:5rem;height:3rem;cursor:pointer;background-color:deepskyblue;border-radius:5px;color:white;font-size:1rem;" >验证码为9999</button><!--v-text="yanZhengMa"-->
      </div>
      <div v-if="judge==4" style="display:flex;margin-left:5rem;">
        <img v-if="keysOk" src="http://114.55.38.94/api/picture/green.png">
        <img v-else src="http://114.55.38.94/api/picture/info.png">
        <p style="margin-left:10px;font-size:1rem;">验证码长度为4位</p>
      </div>
      <p v-else-if="!keysOk" style="color:red;font-size:1rem;margin-left:5rem;">验证码不能为空</p>
    </div>
    <button style="width:6rem;height:3rem;cursor:pointer;background-color:deepskyblue;border-radius:5px;color:white;font-size:1rem;margin-left:20rem;" v-on:click="RegisterUser">注册</button>
  </div>
</template>
<script>
import axios from 'axios'
import router from "../Router/First";
import { onMounted, ref} from "vue";
export default {
setup(){
  let SecondInput=ref(null)
  let ThridInput=ref(null)
  let FourthInput=ref(null)
  onMounted(()=>{
    document.title='注册'
  })
  return{
    SecondInput,ThridInput,FourthInput
  }
},
data(){
  return{
    phone:"",
    password:"",
    name:"",
    keys:"",
    judge:0,
    hasSpace:false,
    lengthOk:true,
    phoneOk:true,
    nameOk:true,
    passwordOk:true,
    keysOk:true,
    yanZhengMa:"点击获取验证码",
    isSixty:false,
  }
},
methods:{
  Send(){
    let that=this
    this.isSixty=true
    let sixtyStart=Date.now()
    axios.get("http://114.55.38.94/api/Send",{params:{"phone":this.phone}}).then(function (res){
      if(res.data.data=="发送验证码成功"){
        const interval=setInterval(()=>{
          that.yanZhengMa=60-Math.trunc((Date.now()-sixtyStart)/1000)
          if(that.yanZhengMa<=0){
            clearInterval(interval)
            that.yanZhengMa="点击获取验证码"
            that.isSixty=false
          }
        },1000)
      }else{
        alert(res.data.data)
      }
    })
  },
  RegisterUser(){
    let register=new URLSearchParams
    register.append("phone",this.phone)
    register.append("name",this.name)
    register.append("password",this.password)
    register.append("keys",this.keys)
    axios.post("http://114.55.38.94/api/Register",register,{headers:{'Content-Type': 'application/x-www-form-urlencoded'}}).then(function (res){
      if(res.data.data=="注册成功"){
        router.push("/Login")
        alert("注册成功，请登录")
      }else{
        alert(res.data.data)
      }
    })
  },
  Blur(){
    this.judge=0
  },
  ShowPhone(){
    this.judge=1
    this.JudgePhone()
  },
  ShowName(){
    this.judge=2
    this.JudgeName()
  },
  ShowPassword(){
    this.judge=3
    this.JudgePassword()
  },
  ShowKeys(){
    this.judge=4
    this.JudgeKeys()
  },
  JudgePhone(){
    if(this.phone.length==11){
      this.phoneOk=true
    }else{
      this.phoneOk=false
    }
  },
  JudgeName(){
    if(this.name.length>0&&this.name.length<=10){
      this.nameOk=true
    }else{
      this.nameOk=false
    }
  },
  JudgePassword(){
    if(this.password.indexOf(" ")!=-1){
      this.hasSpace=true
    }else{
      this.hasSpace=false
    }
    if(5<this.password.length&&this.password.length<13){
      this.lengthOk=true
    }else{
      this.lengthOk=false
    }
    if(this.lengthOk==true&&!this.hasSpace){
      this.passwordOk=true
    }else{
      this.passwordOk=false
    }
  },
  JudgeKeys(){
    if(this.keys.length==4){
      this.keysOk=true
    }else{
      this.keysOk=false
    }
  },
  GoSecondInput(){
    this.SecondInput.focus()
  },
  GoThridInput(){
    this.ThridInput.focus()
  },
  GoFourthInput(){
    this.FourthInput.focus()
  },
}
}
</script>
<style scoped>*{margin:0;padding:0;list-style:none;text-decoration:none;border:0;outline:none;}
.focus{border:1px solid deepskyblue;}
.wrong{border:1px solid red;}</style>