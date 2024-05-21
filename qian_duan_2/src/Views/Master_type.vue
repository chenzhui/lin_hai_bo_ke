<template>
  <div style="display:flex;background-color:#FFFAFA;width:100%;height:900px;">
    <div style="display:flex;flex-direction:column;background-color:grey;width:250px;border-right:solid 1px black;">
      <div style="background-color:lightblue;height:70px;border-bottom:solid 1px grey;">
        <button @click="SwitchActive(-1)" style="color:white;font-size:25px;margin-left:25px;margin-top:20px;background-color: lightblue;cursor:pointer;">林海博客管理后台</button>
      </div>
      <div style="height:60px;border-bottom:solid 1px grey;">
        <button @click="Go_Master_user" class="button0">用户管理</button>
        <button @click="Go_Master_blog" class="button0">博客管理</button>
        <button @click="Go_Master_type" class="button0">类型管理</button>
      </div>
      <div v-for="item in tools" :key="item.index" style="height:60px;border-bottom:solid 1px grey;">
        <button @click="SwitchActive(item.index)" :class="{'button0':true}">{{item.name}}</button>
      </div>
    </div>
    <div  style="display:flex;flex-direction:column;margin-left:50px;margin-top:50px;width:470px;">
      <div style="display:flex;">
        <p style="font-size:20px;margin-left:20px;line-height:40px">类型:</p>
        <input v-model="kind" style="border:solid 1px lightblue;border-radius:10px;height:40px;width:200px;margin-left:20px;font-size:20px;">
        <button  @click="InsertKind" style="background-color:lightblue;color:white;width:120px;height:40px;border-radius:10px;cursor:pointer;margin-left:40px;font-size:20px;">增加类型</button>
      </div>
      <div style="display:flex;margin-top:30px;">
        <p style="border:solid 1px grey;width:60px;text-align:center;height:40px;line-height:40px;font-size:20px;">序号</p>
        <p style="border:solid 1px grey;width:120px;text-align:center;height:40px;line-height:40px;font-size:20px;">类型</p>
        <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">创建时间</p>
        <p style="border:solid 1px grey;width:88px;cursor:pointer;font-size:20px;text-align:center;line-height:40px;">操作</p>
      </div>
      <div style="display:flex;flex-direction:column;height:420px;">
        <div v-for="item in kinds.slice(kindPage*10-10,kindPage*10)" :key="item" style="display:flex;">
          <p style="border:solid 1px grey;width:60px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{kinds.indexOf(item)}}</p>
          <p style="border:solid 1px grey;width:120px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.kind}}</p>
          <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.birthTime}}</p>
          <button @click="DeleteKind(item.kind)" style="background-color:lightblue;color:white;border:solid 1px grey;width:90px;cursor:pointer;font-size:20px">删除</button>
        </div>
      </div>
      <div style="display:flex;height:30px;margin-top:30px;">
        <button @click="PreviousKindPage" style="cursor:pointer;background-color:lightblue;width:60px;">上一页</button>
        <div style="display:flex;width:320px;margin-left:30px;">
          <div v-for="item in tenKinds" :key="item" style="margin-left:20px;">
            <button @click="SwitchKindPage(item)" style="cursor:pointer;width:30px;height:30px;" :class="{'focus':item==kindPage}">{{item}}</button>
          </div>
        </div>
        <button @click="NextKindPage" style="cursor:pointer;background-color:lightblue;width:60px;">下一页</button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import {getCurrentInstance, onMounted} from "vue";
import router from "../Router/First";
export default {
  setup(){
    const gci=getCurrentInstance()
    onMounted(()=>{
      document.title="后台管理"
      gci.data.id = gci.appContext.config.globalProperties.$id
      gci.data.mitt1=gci.appContext.config.globalProperties.$mitt1
      axios.get("http://114.55.38.94/api/SelectKinds").then((res)=>{
        gci.data.kinds=res.data.data
        gci.data.tenKinds=Math.ceil(res.data.data.length/8)
      })
    })
  },
  data(){
    return{
      resolvedBlogs:[],
      users:[{}],
      blogPage:1,
      kindPage:1,
      tenKinds:0,
      tenBlogs:0,
      kinds:[],
      kind:"",
    }
  },
  methods:{
    Go_Master_user(){
      router.push({path:'/Master/User'})
    },
    Go_Master_blog(){
      router.push({path:'/Master/Blog'})
    },
    InsertKind(){
      axios.get('http://114.55.38.94/api/InsertKind',{params:{"kind":this.kind}}).then(function (res){
        alert(res.data.data)
        this.mitt1.emit("Reload")
      })
    },
    DeleteKind(kind){
      axios.get('http://114.55.38.94/api/DeleteKind',{params:{"kind":kind}}).then(function(res){
        alert(res.data.data)
        this.mitt1.emit("Reload")
      })
    },
    PreviousKindPage(){
      if(this.kindPage==1){
        alert("已经是第一页了")
      }else{
        this.kindPage--
      }
    },
    NextKindPage(){
      if(this.kindPage==this.tenKinds){
        alert("已经是最后一页了")
      }else{
        this.kindPage++
      }
    },
    SwitchKindPage(kindPage){
      this.kindPage=kindPage
    },
    ToFollow(id){
      router.push({path:'/Follow',query:{'authorId':id}})
    },
  },
}
</script>
<style scoped>
.button0{background-color:lightblue;width:250px;height:60px;cursor:pointer;font-size:22px;color:white;}
.focus{background-color:lightblue;}
</style>