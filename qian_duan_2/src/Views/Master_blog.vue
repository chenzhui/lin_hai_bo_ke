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
    </div>
    <div  style="display:flex;flex-direction:column;margin-left:100px;margin-top:100px;">
      <div style="display:flex">
        <p style="font-size:20px;margin-left:20px;line-height:40px">博客:</p>
        <input v-model="kind" style="border:solid 1px lightblue;border-radius:10px;height:40px;width:200px;margin-left:20px;font-size:20px;">
        <button  style="background-color:lightblue;color:white;width:120px;height:40px;border-radius:10px;cursor:pointer;margin-left:40px;font-size:20px;">搜索博客</button>
      </div>
      <div style="display:flex;flex-direction:column;margin-top:30px;height:462px;">
        <div style="display:flex;">
          <p style="border:solid 1px grey;width:60px;text-align:center;height:40px;line-height:40px;font-size:20px;">序号</p>
          <p style="border:solid 1px grey;width:120px;text-align:center;height:40px;line-height:40px;font-size:20px;">博客Id</p>
          <p style="border:solid 1px grey;width:400px;text-align:center;height:40px;line-height:40px;font-size:20px;">标题</p>
          <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">类型</p>
          <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">作者</p>
          <p style="border:solid 1px grey;width:180px;text-align:center;height:40px;line-height:40px;font-size:20px;">操作</p>
        </div>
        <div v-for="item in resolvedBlogs.slice(blogPage*10-10,blogPage*10)" :key="item" style="display:flex;">
          <p style="border:solid 1px grey;width:60px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{resolvedBlogs.indexOf(item)}}</p>
          <p style="border:solid 1px grey;width:120px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.blogId}}</p>
          <p style="border:solid 1px grey;width:400px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.title}}</p>
          <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.type}}</p>
          <p style="border:solid 1px grey;width:200px;text-align:center;height:40px;line-height:40px;font-size:20px;">{{item.authorId}}</p>
          <router-link :to="'/Blog?blogId='+item.blogId" style="background-color:lightblue;color:white;border:solid 1px grey;width:90px;cursor:pointer;font-size:20px;text-align:center;line-height:40px;">详情</router-link>
          <button @click="DisAgreeBlog(item.blogId)" style="background-color:lightblue;color:white;border:solid 1px grey;width:90px;cursor:pointer;font-size:20px;line-height:40px;text-align:center;">打回</button>
        </div>
      </div>
      <div style="display:flex;height:30px;margin-top:30px;">
        <button @click="PreviousBlogPage" style="cursor:pointer;background-color:lightblue;width:60px;">上一页</button>
        <div style="display:flex;width:1020px;margin-left:30px;">
          <div v-for="item in tenBlogs" :key="item" style="margin-left:20px;">
            <button @click="SwitchBlogPage(item)" style="cursor:pointer;width:30px;height:30px;" :class="{'focus':item==blogPage}">{{item}}</button>
          </div>
        </div>
        <button @click="NextBlogPage" style="cursor:pointer;background-color:lightblue;width:60px;">下一页</button>
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
      axios.get('http://114.55.38.94/api/GetAllBlogs').then((res)=>{
        gci.data.resolvedBlogs=res.data.data
        gci.data.tenBlogs=Math.ceil(res.data.data.length/10)
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
      tools:[{"name":"用户管理","index":0},{"name":"博客审核","index":1,},{"name":"类型管理","index":2}],
      active:0,
    }
  },
  methods:{
    Go_Master_user(){
      router.push({path:'/Master/user'})
    },
    Go_Master_type(){
      router.push({path:'/Master/type'})
    },
    PreviousBlogPage(){
      if(this.blogPage==1){
        alert("已经是第一页了")
      }else{
        this.blogPage--
      }
    },
    NextBlogPage(){
      if(this.blogPage==this.tenBlogs){
        alert("已经是最后一页了")
      }else{
        this.blogPage++
      }
    },
    SwitchBlogPage(blogPage){
      this.blogPage=blogPage
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