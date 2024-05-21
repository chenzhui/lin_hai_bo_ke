<template>
  <div style="display:flex;flex-direction:column;width:100%;">
    <div style="display:flex;align-self:start;margin-top:3rem;margin-left:20%;width:50%;">
      <input @keydown.enter="SearchBlogs(search)" v-model="search" type="text" style="background-color:lightblue;height:3rem;width:70%;border-radius:5px;font-size:1rem;" placeholder="请输入博客标题">
      <button @click="SearchBlogs(search)" style="margin-left:6%;border-radius:10px;cursor:pointer;width:20%;color:white;background-color:lightblue;font-size:1rem;">搜索</button>
    </div>
    <div style="display:flex;margin-top:3rem;">
        <div style="display:flex;flex-direction:column;overflow-y:auto;overflow-x:hidden;width:10%;">
          <button style="border:solid 1px;background-color:lightgrey;height:4rem;border:solid 2px lightblue;font-size:1rem;">博客分类</button>
          <button @click="GetTypeBlogs()" :class="{'focus1':type=='all','nofocus':type!='all'}">全部</button>
          <button v-for="item in types" :key="item" @click="GetTypeBlogs(item.kind)" :class="{'focus1':type==item.kind,'nofocus':type!=item.kind}">{{item.kind}}</button>
        </div>
        <div style="display:flex;flex-direction:column;width:70%;margin-left:1%;">
          <div style="background-color:white;display:flex;flex-direction:column;height:52rem;">
            <div v-for="item in blogs.slice(page*7-7,page*7)" :key="item.blogId" @click="ToBlog(item.blogId)" style="display:flex;flex-direction:column;cursor:pointer;font-size:1.4rem;margin-left:0.1rem;height:6rem;margin-top:1rem;">
              <div style="display:flex;align-items:start;height:2rem;">
                <p style="width:10rem;font-size:1.8rem;">博客ID:{{item.blogId}}</p>
                <p style="width:50rem;height:40px;font-size:1.2rem;">标题:{{item.title}}</p>
                <p style="max-width:20%;border-radius:5px;background-color:lightgreen;font-size:1.8rem;">{{item.type}}</p>
              </div>
              <div style="display:flex;">
                <p style="margin-top:1rem;width:40%">作者:{{item.authorName}}</p>
                <p style="margin-top:1rem;">{{item.name}}发布于{{item.birthTime}}</p>
              </div>
              <hr style="height:0.1rem;width:99%;background-color:lightgrey;margin-top:1rem;">
            </div>
          </div>
          <div style="display:flex;background-color:white;">
            <button @click="PreviousPage" style="cursor:pointer;width:6rem;height:5rem;font-size:1rem;margin-left:1%;">上一页</button>
            <div v-if="sevenBlogCount<9" style="margin-top:0.5rem;width:45rem;display:flex;flex-direction:row;" >
              <button v-for="item in sevenBlogCount" :key="item.blogId" @click="SwitchPage(item)" style="cursor:pointer;width:5rem;height:5rem;line-height:4rem;margin-left:1rem;" :class="{'focus':item==page}">{{item}}</button>
            </div>
            <div v-else style="display:flex;">
              <div v-if="page>5" style="display:flex;width:100%;">
                <button @click="SwitchPage(1)" class="fen_ye" :class="{'focus':1==page}">1</button>
                <button @click="SwitchPage(3)" class="fen_ye" :class="{'focus':1==page}">···</button>
                <div v-if="page==sevenBlogCount" style="width:80%">
                  <button @click="SwitchPage(page-4)" class="fen_ye">{{page-4}}</button>
                  <button @click="SwitchPage(page-3)" class="fen_ye">{{page-3}}</button>
                  <button @click="SwitchPage(page-2)" class="fen_ye">{{page-2}}</button>
                  <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
                  <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat;">{{page-0}}</button>
                </div>
                <div v-else-if="page==sevenBlogCount-1" style="width:80%">
                  <button @click="SwitchPage(page-3)" class="fen_ye">{{page-3}}</button>
                  <button @click="SwitchPage(page-2)" class="fen_ye" >{{page-2}}</button>
                  <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
                  <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat;">{{page-0}}</button>
                  <button @click="SwitchPage(page-(-1))" class="fen_ye">{{page-(-1)}}</button>
                </div>
                <div v-else style="width:80%">
                  <button @click="SwitchPage(page-2)" class="fen_ye">{{page-2}}</button>
                  <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
                  <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat;">{{page-0}}</button>
                  <button @click="SwitchPage(page-(-1))" class="fen_ye">{{page-(-1)}}</button>
                  <button @click="SwitchPage(page-(-2))" class="fen_ye">{{(page-(-2))}}</button>
                </div>
              </div>
              <div v-else style="display:flex;width:100%;">
                <button @click="SwitchPage(1)" class="fen_ye" :class="{'focus':1==page}">1</button>
                <button @click="SwitchPage(2)" class="fen_ye" :class="{'focus':2==page}">2</button>
                <button @click="SwitchPage(3)" class="fen_ye" :class="{'focus':3==page}">3</button>
                <button @click="SwitchPage(4)" class="fen_ye" :class="{'focus':4==page}">4</button>
                <button @click="SwitchPage(5)" class="fen_ye" :class="{'focus':5==page}">5</button>
                <button @click="SwitchPage(6)" class="fen_ye" :class="{'focus':6==page}">6</button>
                <button @click="SwitchPage(7)" class="fen_ye" :class="{'focus':7==page}">7</button>
              </div>
            </div>
            <button @click="NextPage" style="cursor:pointer;width:6rem;height:5rem;margin-left:1rem;font-size:1rem;">下一页</button>
            <button @click="LastPage" style="cursor:pointer;width:6rem;height:5rem;margin-left:1rem;font-size:1rem;">最后一页</button>
            <p style="margin-left:0.5rem;align-self:center;font-size:1rem;">共{{sevenBlogCount}}页</p>
          </div>
        </div>

      <div style="display:flex;flex-direction:column;margin-left:2% ;width:15%;">
        <p style="background-color:lightblue;color:white;font-size:1.2rem;border-bottom:solid 1px white;">如有问题请联系站主,请备注来意</p>
        <p style="background-color:lightblue;color:white;font-size:1rem;border-bottom:solid 1px white;">微信</p>
        <img src="http://114.55.38.94/api/picture/wx.png">
        <p style="background-color:lightblue;color:white;font-size:1rem;border-bottom:solid 1px white;">QQ</p>
        <img src="http://114.55.38.94/api/picture/qq.png">
        <p style="background-color:lightblue;color:white;font-size:1rem;border-bottom:solid 1px white;">打赏码</p>
        <img src="http://114.55.38.94/api/picture/dsm.png">
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import {getCurrentInstance,nextTick,onMounted} from "vue";
import router from "../Router/First";
//import ButtonCounter from "@/component/ButtonCounter";
export default {
  //components: {Video_play, ButtonCounter},
  setup(){
  const gci=getCurrentInstance()
  onMounted(()=>{
    document.title='首页'
    gci.data.mitt1 = gci.appContext.config.globalProperties.$mitt1
    gci.data.type=router.currentRoute.value.query.type
    gci.data.page=router.currentRoute.value.query.page
    if(router.currentRoute.value.query.type==undefined){gci.data.type='all'}
    if(router.currentRoute.value.query.page==undefined){gci.data.page=1}
    gci.data.id = gci.appContext.config.globalProperties.$id
    gci.data.avatar = gci.appContext.config.globalProperties.$avatar
    axios.get('http://114.55.38.94/api/SelectKinds').then((res)=>{gci.data.types=res.data.data})
    if(gci.data.type=='all'){
      axios.get('http://114.55.38.94/api/GetAllBlogs').then(function (res){
        gci.data.blogs=res.data.data
        gci.data.sevenBlogCount=Math.ceil(gci.data.blogs.length/7)
      })
    }else{
      axios.get('http://114.55.38.94/api/GetTypeBlogs', {params: {"type":gci.data.type}}).then(function (res) {
        gci.data.blogs = res.data.data
        gci.data.sevenBlogCount = Math.ceil(gci.data.blogs.length /7)
      })
    }
  })
  return{
  }
},
data(){
  return{
    chatInput:"",
    blogs:[],
    sevenBlogCount:0,
    page:1,
    chats:[],
    blogFlush:true,
    types:[{}],
    type:'all',
    search:''
  }
},
methods:{
  ToBlog(blogId){
    const destinction = router.resolve({
      path: '/Blog',
      query: {blogId: blogId}
    })
    window.open(destinction.href, "_blank")
  },
  PreviousPage(){
    if(this.page==1){
      alert("已经是第一页了")
    }else{
      this.page--
      router.push({path:'/Index',query:{'type':this.type,'page':this.page}})
      router.currentRoute.value.query.type=this.type
      router.currentRoute.value.query.page=this.page
      nextTick((()=>{this.mitt1.emit('Reload')}))
    }
  },
  NextPage(){
    if(this.page==this.sevenBlogCount){
      alert("已经是最后一页了")
    }else{
      this.page++
      router.push({path:'/Index',query:{'type':this.type,'page':this.page}})
      router.currentRoute.value.query.type=this.type
      router.currentRoute.value.query.page=this.page
      nextTick((()=>{
        this.mitt1.emit('Reload')
      }))
    }
  },
  LastPage(){
    if(this.page==this.sevenBlogCount){
      alert("已经是最后一页了")
    }else{
      this.page=this.sevenBlogCount
      router.push({path:'/Index',query:{'type':this.type,'page':this.page}})
      router.currentRoute.value.query.type=this.type
      router.currentRoute.value.query.page=this.page
      nextTick((()=>{
        this.mitt1.emit('Reload')
      }))
    }
  },
  SwitchPage(page){
    router.push({path:'/Index',query:{'type':this.type,'page':page}})
    router.currentRoute.value.query.type=this.type
    router.currentRoute.value.query.page=page
    nextTick((()=>{this.mitt1.emit('Reload')}))
  },
  GetTypeBlogs(type){
    router.push({path:'/Index',query:{'type':type,'page':1}})
    router.currentRoute.value.query.type=type
    router.currentRoute.value.query.page=1
    nextTick((()=>{
      this.mitt1.emit('Reload')
    }))
  },
  SearchBlogs(search){
    const destinction = router.resolve({
      path: '/Search',
      query: {search:search}
    })
    window.open(destinction.href, "_blank")
  },
}
}
</script>
<style scoped>
.focus{background-color:wheat;}
.focus1{font-size:1rem;border:solid 0.1rem lightblue;height:4rem;border:solid 0.2rem lightblue;cursor:pointer;background-color:lightgreen;}
.nofocus{font-size:1rem;border:solid 0.1rem lightblue;background-color:lightgrey;height:4rem;border:solid 0.2rem lightblue;cursor:pointer;}
.fen_ye{font-size:1rem;width:5rem;height:5rem;margin-left:1rem;cursor: pointer;}
</style>