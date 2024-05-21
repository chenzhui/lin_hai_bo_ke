<template>
  <div style="display:flex;background-color:#FFFAFA;width:100%;height:900px;">
    <div style="display:flex;flex-direction:column;background-color:grey;width:250px;border-right:solid 1px black;">
      <div style="background-color:lightblue;height:70px;border-bottom:solid 1px grey;">
        <p style="color:white;font-size:25px;margin-left:25px;margin-top:20px;background-color: lightblue;cursor:pointer;">林海博客管理后台</p>
      </div>
      <div style="height:60px;border-bottom:solid 1px grey;">
        <button @click="Go_Master_user" class="button0">用户管理</button>
        <button @click="Go_Master_blog" class="button0">博客管理</button>
        <button @click="Go_Master_type" class="button0">类型管理</button>
      </div>
    </div>
    <div style="display:flex;flex-direction:column;margin-left:100px;margin-top:100px;">
      <div style="display:flex">
        <p style="border:solid 1px grey;width:60px;text-align:center;height:40px;line-height:40px;font-size:20px;">ID</p>
        <p style="border:solid 1px grey;width:100px;text-align:center;height:40px;line-height:40px;font-size:20px;">账号</p>
        <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">昵称</p>
        <p style="border:solid 1px grey;width:180px;text-align:center;height:40px;line-height:40px;font-size:20px;">操作</p>
      </div>
      <div v-for="item in users" :key="item.id" style="display:flex;">
        <p style="border:solid 1px grey;width:60px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.id}}</p>
        <p style="border:solid 1px grey;width:100px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.name}}</p>
        <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.phone}}</p>
        <button @click="ToFollow(item.id)" style="border:solid 1px grey;width:60px;text-align:center;height:42px;line-height:40px;font-size:20px;background-color:lightblue;cursor:pointer;">主页</button>
        <button style="border:solid 1px grey;width:60px;text-align:center;height:42px;line-height:40px;font-size:20px;background-color:lightblue;cursor:pointer;">封号</button>
        <button style="border:solid 1px grey;width:60px;text-align:center;height:42px;line-height:40px;font-size:20px;background-color:lightblue;cursor:pointer;">删除</button>
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
      //axios.get("http://114.55.38.94/api/GetAllUsers",{params:{id:gci.data.id}}).then((res)=>{gci.data.users=res.data.data})
    })
  },
  data(){
    return{
      resolvedBlogs:[],
      users:[{"id":1,"name":"wzh","phone":"17857871329"}],
    }
  },
  methods:{
    ToFollow(id){
      router.push({path:'/Follow',query:{'authorId':id}})
    },
    Go_Master_blog(){
      router.push({path:'/Master/blog'})
    },
    Go_Master_type(){
      router.push({path:'/Master/type'})
    },
  },
}
</script>
<style scoped>
.button0{background-color:lightblue;width:250px;height:60px;cursor:pointer;font-size:22px;color:white;}
.focus{background-color:lightblue;}
</style>