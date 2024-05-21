<template>
  <div style="display:flex;flex-direction:column;margin-left:200px;">
    <div style="display:flex;flex-direction:column">
      <p>回复我的</p>
      <p>收到的赞</p>
      <p>系统通知</p>
    </div>
    <div style="display:flex;flex-direction:column;">
      <div v-for="item in letters" :key="item" style="display:flex;flex-direction:row;">
        <img src="">
        <div style="display:flex;flex-direction:column;">
          <p>name</p>
          <p>content</p>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import {getCurrentInstance, onMounted} from "vue";
import axios from "axios";

export default {
  setup(){
    const gci=getCurrentInstance()
    onMounted(()=>{
      document.title='信息'
      gci.data.mitt1=gci.appContext.config.globalProperties.$mitt
      gci.data.id=gci.appContext.config.globalProperties.$id
      gci.data.id=1
      axios.get("http://localhost:8001/SelectAllLetters",{params:{"towardId":gci.data.id}}).then(function (res){
        gci.data.letters=res.data.data
      })
    })
  },
  data(){
    return{
      letters:[],
    }
  }
}
</script>
<style scoped>
</style>