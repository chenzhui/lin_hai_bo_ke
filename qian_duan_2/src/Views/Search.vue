<template>
  <div style="display:flex;flex-direction:column;">
    <div style="display:flex;margin-top:30px;margin-left:100px;">
      <input @keydown.enter="SearchBlogs(search)" v-model="search" type="text" style="background-color:lightblue;height:40px;width:600px;margin-left:5px;" placeholder="请输入你想要查询的博客标题">
      <button @click="SearchBlogs(search)" style="margin-left:10px;border-radius:10px;cursor:pointer;width:60px;color:white;background-color:lightblue;font-size:20px;">搜索</button>
    </div>
    <div>
      <div v-if="hasResult==true" style="display:flex;flex-direction:column;width:500px;margin-left:200px;margin-top:50px;">
        <div style="background-color:white;height:656px;display:flex;flex-direction:column;">
          <div v-for="item in blogs.slice(page*8-8,page*8)" :key="item.blogId" @click="ToBlog(item.blogId)" style="display:flex;flex-direction:column;cursor:pointer;font-size:18px;margin-top:10px;margin-left:10px;">
            <div style="display:flex;align-items:start;">
              <p style="width:100px">博客ID:{{item.blogId}}</p>
              <p style="width:300px;height:40px;">标题:{{item.title}}</p>
              <p style="margin-left:30px;border-radius:5px;background-color:lightgreen;height:25px;">{{item.type}}</p>
            </div>
            <div style="display:flex;">
              <p style="margin-top:10px;width:160px;">作者:{{item.authorName}}</p>
              <p style="margin-top:10px;">{{item.name}}发布于{{item.birthTime}}</p>
            </div>
            <hr style="height:1px;width:480px;background-color:lightgrey;margin-top:10px;">
          </div>
        </div>
        <div style="display:flex;height:30px;background-color:white;">
          <button @click="PreviousPage" style="cursor:pointer;width:60px;">上一页</button>
          <div v-if="eightBlogCount<8" style="display:flex;width:370px;margin-left:7px;">
            <div v-for="item in eightBlogCount" :key="item" style="margin-left:20px;">
              <button @click="SwitchPage(item)" style="cursor:pointer;width:30px;height:30px;" :class="{'focus':item==page}">{{item}}</button>
            </div>
          </div>
          <div v-else style="display:flex;width:370px;margin-left:7px;">
            <button @click="SwitchPage(1)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':1==page}">1</button>
            <div v-if="page>5" style="display:flex">
              <button @click="SwitchPage(3)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':1==page}">···</button>
              <div v-if="page==eightBlogCount">
                <button @click="SwitchPage(page-4)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-4}}</button>
                <button @click="SwitchPage(page-3)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-3}}</button>
                <button @click="SwitchPage(page-2)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-2}}</button>
                <button @click="SwitchPage(page-1)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-1}}</button>
                <button @click="SwitchPage(page)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;background-color:wheat;">{{page}}</button>
              </div>
              <div v-else-if="page==eightBlogCount-1">
                <button @click="SwitchPage(page-3)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':2==page}">{{page-3}}</button>
                <button @click="SwitchPage(page-2)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" >{{page-2}}</button>
                <button @click="SwitchPage(page-1)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-1}}</button>
                <button @click="SwitchPage(page)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;background-color:wheat;">{{page}}</button>
                <button @click="SwitchPage(page+1)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page+1}}</button>
              </div>
              <div v-else>
                <button @click="SwitchPage(page-2)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-2}}</button>
                <button @click="SwitchPage(page-1)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page-1}}</button>
                <button @click="SwitchPage(page)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;background-color:wheat;">{{page}}</button>
                <button @click="SwitchPage(page+1)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page+1}}</button>
                <button @click="SwitchPage(page+2)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;">{{page+2}}</button>
              </div>
            </div>
            <div v-else>
              <button @click="SwitchPage(2)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':2==page}">2</button>
              <button @click="SwitchPage(3)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':3==page}">3</button>
              <button @click="SwitchPage(4)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':4==page}">4</button>
              <button @click="SwitchPage(5)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':5==page}">5</button>
              <button @click="SwitchPage(6)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':6==page}">6</button>
              <button @click="SwitchPage(7)" style="cursor:pointer;width:30px;height:30px;margin-left:20px;" :class="{'focus':7==page}">7</button>
            </div>
          </div>
          <button @click="NextPage" style="cursor:pointer;width:60px;">下一页</button>
        </div>
      </div>
      <div v-else style="display:flex;flex-direction:column;width:500px;margin-left:200px;margin-top:50px;background-color:white;height:656px;display:flex;flex-direction:column;">
        <p style="margin-left:1rem;margin-top:1rem;">未查询到相关博客</p>
      </div>
    </div>
  </div>
</template>

<script>
import {getCurrentInstance,onMounted,nextTick} from "vue";
import axios from "axios";
import router from "../Router/First";
export default {
setup(){
  const gci=getCurrentInstance()
  onMounted(()=>{
    document.title='搜索结果'
    gci.data.search=router.currentRoute.value.query.search
    axios.get('http://114.55.38.94/api/SearchBlogs',{params: {"search":router.currentRoute.value.query.search}}).then((res)=>{
      console.log(res.data.data)
      if(res.data.data[0]==null){
        gci.data.hasResult=false
      }else{
        gci.data.blogs=res.data.data
        gci.data.eightBlogCount = Math.ceil(gci.data.blogs.length /8)
      }
    })
  })
},
data(){
  return{
    blogs:[],
    eightBlogCount:0,
    search:null,
    page:1,
    hasResult:true,
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
      router.push({path:'/Index',query:{'page':this.page}})
      router.currentRoute.value.query.page=this.page
    }
  },
  NextPage(){
    if(this.page==this.eightBlogCount){
      alert("已经是最后一页了")
    }else{
      this.page++
      router.push({path:'/Index',query:{'page':this.page}})
      router.currentRoute.value.query.page=this.page
    }
  },
  SwitchPage(page){
    router.push({path:'/Index',query:{'page':page}})
    router.currentRoute.value.query.page=page
    nextTick((()=>{
      this.mitt1.emit('Reload')
    }))
  },
  SearchBlogs(search){
    router.push({path:'/Search',query:{'search':search}})
  },
}
}
</script>

<style scoped></style>