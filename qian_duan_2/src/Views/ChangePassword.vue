<template>
  <div style="margin-left:10rem;display:flex;flex-direction:column;width:20rem;">
    <p style="align-self:center;color:deepskyblue;font-size:2rem;">修改密码</p>
    <input v-model="password" placeholder="请输入您的原密码" style="border-radius:5px;margin-top:1rem;border:solid 1px black;height:3rem;width:20rem;font-size:1rem;">
    <input v-model="password1" placeholder="请输入您的新密码" style="border-radius:5px;margin-top:10px;border:solid 1px black;height:3rem;width:20rem;font-size:1rem;" type="password">
    <input v-model="password2" placeholder="确认您的新密码" style="border-radius:5px;margin-top:10px;border:solid 1px black;height:3rem;width:20rem;font-size:1rem;" type="password">
    <button @click="ChangePassword" style="align-self:center;background-color:deepskyblue;color:white;border-radius:5px;margin-top:1rem;width:10rem;height:3rem;cursor:pointer;font-size:1.5rem;">确认修改</button>
  </div>
</template>

<script>
import axios from "axios";
import router from "../Router/First";
import {getCurrentInstance, onMounted} from "vue";

export default{
  setup() {
  const gci = getCurrentInstance()
  onMounted(async () => {
    document.title = "修改昵称"
    gci.data.id = gci.appContext.config.globalProperties.$id
    gci.data.mitt1 = gci.appContext.config.globalProperties.$mitt1
  })
},
data(){
  return{
    id:"",
    password: "",
    password1: "",
    password2: "",
  }
},
methods:{
  ChangePassword(){
    if(this.password1 == this.password2){
      const that = this
      axios.get('http://114.55.38.94/api/ChangePassword', {params:{"id":this.id,"oldPassword":this.password,"password":this.password1}}).then(function (res) {
        if (res.data.data=="修改密码成功"){
          router.push('/Login')
          that.mitt1.emit("Reload")
          alert("修改成功,请重新登录")
        }else{
          alert(res.data.data)
        }
      })
    } else {
      alert("两次输入的密码不同")
    }
  },
}
}
</script>

<style scoped>

</style>