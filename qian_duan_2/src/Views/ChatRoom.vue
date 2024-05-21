<template>
  <div style="display:flex;flex-direction:column;width:600px;background-color:white;margin-left:20px;">
        <div style="display:flex;height:30px;background-color:lightblue;align-items:center;">
          <p style="margin-left:300px;color:white;width:200px;font-size:25px;">在线聊天室</p>
        </div>
        <div style="height:550px;border-bottom:solid 1px black;overflow:auto;">
          <div v-for="item in chats" :key="chats.indexOf(item)" style="display:flex;flex-direction:column;margin-top:10px;">
            <div v-if="item.id!=id" style="display:flex;"><p style="align-self:center;">{{item.name}}</p><p style="margin-left:20px;background-color:lightgrey;border-radius:5px;padding:8px;max-width:200px;">{{item.message}}</p></div>
            <div v-else style="display:flex;flex-direction:row-reverse"><p style="align-self:center;">{{item.name}}</p><p style="margin-right:20px;background-color:deepskyblue;border-radius:5px;padding:8px;max-width:200px;">{{item.message}}</p></div>
          </div>
        </div>
        <div style="display:flex;flex-direction:column;">
          <textarea @keydown.enter="SendMessage" v-model="chatInput" style="height:60px;resize:none;"></textarea>
          <button style="cursor:pointer;margin-left:600px;width:100px;height:30px;background-color:lightblue"  @click="SendMessage">发送</button>
        </div>
      </div>
</template>
<script>
import {getCurrentInstance, onMounted} from "vue";

export default {
setup(){
  const gci=getCurrentInstance();
  onMounted(()=>{
    gci.data.Socket=function(){
      gci.data.ws = new WebSocket('ws://localhost:8001/Chat/'+gci.data.id);
      gci.data.ws.onopen =function(){};
      gci.data.ws.onmessage=function(data){gci.data.chats.push(JSON.parse(data.data))}
      gci.data.ws.onerror = function(){console.log("连接已出错...");setTimeout(()=>{gci.data.socket()},1500)}
      //gci.data.ws.onclose = function(){setTimeout(()=>{gci.data.Socket()},2000)}
    }
    gci.data.Socket()
  })
},
data(){
  return{
    id:1,
    chats:[],
    dollarws:"",
    chatInput:"",
    //ws:"",
  }
},
methods:{
  SendMessage(){
  this.ws.send(this.chatInput)
  this.chatInput=""
},
}

}
</script>
<style scoped>

</style>