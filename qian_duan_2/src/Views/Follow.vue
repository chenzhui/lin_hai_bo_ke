<template>
  <div style="display:flex;flex-direction:column;">
    <div style="display:flex;background-color:white;height:200px;">
      <div style="display:flex;margin-top:50px;margin-left:100px;">
        <img :src="'http://114.55.38.94/api/avatar/'+avatar" style="width:100px;height:100px;border-radius:50%;"/>
        <p style="font-size:25px;margin-top:20px;margin-left:20px;width:200px;">{{name}}</p>
      </div>
    </div>
    <div style="display:flex;flex-direction:column;align-self:center;width:1000px;height:800px;background-color:white;">
      <div style="display:flex;flex-direction:column;height:700px;">
        <div v-for="item in blogData.slice(page*8-8,page*8)" :key="item.blogId" @click="ToBlog(item.blogId)" style="display:flex;flex-direction:column;cursor:pointer;font-size:18px;margin-top:20px;margin-left:10px;width:1000px;">
          <div style="display:flex;align-items:center;"><p>标题:{{item.title}}</p><p style="margin-left:30px;border-radius:5px;background-color:lightgreen;">{{item.type}}</p></div>
          <p style="margin-top:10px;">{{item.name}}发布于{{item.birthTime}}</p>
          <hr style="height:1px;width:980px;background-color:lightgrey;margin-top:10px;">
        </div>
      </div>
      <div style="display:flex;height:30px;margin-top:50px;margin-left:10px;">
        <button @click="PreviousPage" style="cursor:pointer;width:60px;">上一页</button>
        <div style="display:flex;width:830px;margin-left:30px;">
          <div v-for="item in eightBlogCount" :key="item" style="margin-left:20px;">
            <button @click="SwitchPage(item)" style="cursor:pointer;width:30px;height:30px;" :class="{'focus':item==page}">{{item}}</button>
          </div>
        </div>
        <button @click="NextPage" style="cursor:pointer;width:60px;">下一页</button>
      </div>
    </div>
  </div>
</template>

<script>
import {getCurrentInstance, onMounted} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import router from "../Router/First";
export default {
setup(){
  const router=useRouter()
  const gci=getCurrentInstance()
  onMounted(()=>{
    gci.data.authorId=router.currentRoute.value.query.authorId
    axios.get('http://114.55.38.94/api/GetNameById',{params:{"id":gci.data.authorId}}).then((res)=>{gci.data.name=res.data.data})
    axios.get('http://114.55.38.94/api/GetAvatarById',{params:{"id":gci.data.authorId}}).then((res)=>{gci.data.avatar=res.data.data})
    axios.get("http://114.55.38.94/api/GetOwnerBlogs",{params:{"id":gci.data.authorId}}).then((res)=>{gci.data.blogData=res.data.data;gci.data.eightBlogCount=Math.ceil(res.data.data.length/8);})
  })
},
data(){
  return{
    name:"",
    avatar:'default.png',
    blogData:[{}],
    eightBlogCount:0,
    page:1,
  }
},
methods:{
  ToBlog(blogId){
    router.push({path:'/Blog',query:{'blogId':blogId}})
  },
  PreviousPage(){
    if(this.page==1){
      alert("已经是第一页了")
    }else{
      this.page--
    }
  },
  NextPage(){
    if(this.page==this.eightBlogCount){
      alert("已经是最后一页了")
    }else{
      this.page++
    }
  },
  SwitchPage(page){
    this.page=page
  },
}
}
</script>

<style scoped>
.focus{background-color:wheat;}
</style>