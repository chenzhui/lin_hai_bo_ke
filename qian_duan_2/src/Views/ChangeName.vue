<template style="display:flex;flex-direction:column;">
  <div style="display:flex;flex-direction:column;margin-left:5rem;width:80rem;background-color: white;align-self:center;margin-top:4rem;">
    <div style="display:flex;flex-direction:column;">
      <p style="margin-left:2rem;color:deepskyblue;font-size:2rem;">修改信息</p>
      <p style="margin-left:2rem;font-size:2rem;">现昵称:{{ oldName }}</p>
      <input type="text" placeholder="请输入您的新昵称" v-model="targetName" style="margin-left:2rem;border-radius:5px;margin-top:1rem;border:solid 1px black;height:30px;width:12rem;font-size:1rem;">
      <button @click="ChangeName" style="margin-left:3rem;background-color:deepskyblue;color:white;border-radius:5px;margin-top:2rem;width:8rem;height:2rem;cursor:pointer;font-size:1.5rem;">确认修改</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {getCurrentInstance, onMounted} from "vue";

export default {
setup() {
  const gci = getCurrentInstance()
  onMounted(async () => {
    document.title = "修改信息"
    gci.data.id = gci.appContext.config.globalProperties.$id
    gci.data.mitt1 = gci.appContext.config.globalProperties.$mitt1
    axios.get('http://114.55.38.94/api/GetNameById',{params:{"id":gci.data.id}}).then((res) => {
      gci.data.oldName = res.data.data
    })
  })
},
data(){
  return{
    oldName:"",
    targetName:"",
  }
},
methods:{
  ChangeName() {
    const that = this
    axios.get('http://114.55.38.94/api/ChangeName', {params: {"id": this.id, 'name': this.targetName}}).then(function (res) {
      if(res.data.data=="修改昵称成功"){
        alert("修改昵称成功")
        that.mitt1.emit('Reload')
      }else{
        alert(res.data.data)
      }
    })
  },
}
}
</script>

<style scoped>

</style>