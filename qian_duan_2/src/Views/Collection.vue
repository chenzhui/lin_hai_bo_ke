<template>
  <div style="background-color:white;display:flex;flex-direction:column;width:80rem;margin-left:10rem;margin-top:5rem; ">
      <div v-for="item in blogs.slice(page*7-7,page*7)" :key="item.blogId" @click="ToBlog(item.blogId)" style="display:flex;flex-direction:column;width:80rem;cursor:pointer;font-size:1.4rem;margin-top:1rem;margin-left:1rem;">
        <div style="display:flex;align-items:start;">
          <p style="width:20%;font-size:1.8rem;">博客ID:{{item.blogId}}</p>
          <p style="width:60%;height:40px;font-size:1.2rem;">标题:{{item.title}}</p>
          <p style="max-width:20%;border-radius:5px;background-color:lightgreen;font-size:1.8rem;">{{item.type}}</p>
        </div>
        <div style="display:flex;">
          <p style="margin-top:1rem;width:20rem;">作者:{{item.authorName}}</p>
          <p style="margin-top:10px;">{{item.name}}发布于{{item.birthTime}}</p>
        </div>
        <hr style="height:0.1rem;width:78rem;background-color:lightgrey;margin-top:1rem;">
      </div>
    <div style="display:flex;height:40px;margin-top:20px;">
      <button @click="PreviousPage" style="cursor:pointer;width:2rem;height:2rem;">上一页</button>
      <div v-if="eightBlogCount<9" style="width:78%;display:flex;flex-direction:row;" >
        <button v-for="item in eightBlogCount" :key="item.blogId" @click="SwitchPage(item)" style="cursor:pointer;width:3rem;height:3rem;line-height:3rem;margin-left:1rem;" :class="{'focus':item==page}">{{item}}</button>
      </div>
      <div v-else style="display:flex;">
        <div v-if="page>5" style="display:flex">
          <button @click="SwitchPage(1)" class="fen_ye">1</button>
          <button @click="SwitchPage(3)" class="fen_ye">···</button>
          <div v-if="page==eightBlogCount">
            <button @click="SwitchPage(page-4)" class="fen_ye">{{page-4}}</button>
            <button @click="SwitchPage(page-3)" class="fen_ye">{{page-3}}</button>
            <button @click="SwitchPage(page-2)" class="fen_ye">{{page-2}}</button>
            <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
            <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat">{{page}}</button>
          </div>
          <div v-else-if="page==eightBlogCount-1">
            <button @click="SwitchPage(page-3)" class="fen_ye" >{{page-3}}</button>
            <button @click="SwitchPage(page-2)" class="fen_ye" >{{page-2}}</button>
            <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
            <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat;">{{page}}</button>
            <button @click="SwitchPage(page+1)" class="fen_ye">{{page+1}}</button>
          </div>
          <div v-else>
            <button @click="SwitchPage(page-2)" class="fen_ye">{{page-2}}</button>
            <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
            <button @click="SwitchPage(page)" class="fen_ye">{{page}}</button>
            <button @click="SwitchPage(page+1)" class="fen_ye">{{page+1}}</button>
            <button @click="SwitchPage(page+2)" class="fen_ye">{{page+2}}</button>
          </div>
        </div>
        <div v-else>
          <button @click="SwitchPage(2)" class="fen_ye" :class="{'focus':2==page}">2</button>
          <button @click="SwitchPage(2)" class="fen_ye" :class="{'focus':2==page}">2</button>
          <button @click="SwitchPage(3)" class="fen_ye" :class="{'focus':3==page}">3</button>
          <button @click="SwitchPage(4)" class="fen_ye" :class="{'focus':4==page}">4</button>
          <button @click="SwitchPage(5)" class="fen_ye" :class="{'focus':5==page}">5</button>
          <button @click="SwitchPage(6)" class="fen_ye" :class="{'focus':6==page}">6</button>
          <button @click="SwitchPage(7)" class="fen_ye" :class="{'focus':7==page}">7</button>
        </div>
      </div>
      <button @click="NextPage" style="cursor:pointer;width:60px;margin-left:20px;">下一页</button>
      <button @click="LastPage" style="cursor:pointer;width:60px;margin-left:20px;">最后一页</button>
      <p style="margin-left:1rem;align-self:center">共{{eightBlogCount}}页</p>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import {getCurrentInstance, nextTick, onMounted, ref} from "vue";
import router from "../Router/First";
export default {
  setup(){
    const gci = getCurrentInstance()
    onMounted(async () => {
      document.title = "我的收藏"
      gci.data.page=router.currentRoute.value.query.page
      gci.data.type=router.currentRoute.value.query.type
      if(gci.data.page==undefined){gci.data.page=1}
      gci.data.id=gci.appContext.config.globalProperties.$id
      gci.data.mitt1 = gci.appContext.config.globalProperties.$mitt1
      axios.get('http://114.55.38.94/api/SelectCollectionsById',{params:{"id":gci.data.id}}).then((res)=>{
        gci.data.blogs = res.data.data
        gci.data.eightBlogCount=Math.ceil(gci.data.blogs.length/7)
      })
    })
  },
  data() {
    return {
      id: "",
      index:0,
      type: "",
      blogs: [],
      eightBlogCount: 0,
      page: 1,
      collections:[],
    }
  },
  methods: {
    GetTypeBlogs(type){
      router.push({path:'/Collection',query:{'type':type,'page':1}})
      router.currentRoute.value.query.type=type
      router.currentRoute.value.query.page=1
      nextTick((()=>{this.mitt1.emit('Reload')}))
    },
    PreviousPage() {
      if(this.page==1){
        alert("已经是第一页了")
      }else{
        this.page--
        router.push({path:'/Self',query:{'page':this.page}})
        router.currentRoute.value.query.page=this.page
        nextTick((()=>{
          this.mitt1.emit('Reload')
        }))
      }
    },
    LastPage(){
      if(this.page==this.eightBlogCount){alert("已经是最后一页了")}else{
        this.page=this.eightBlogCount
        router.push({path:'/Self',query:{'page':this.page}})
        router.currentRoute.value.query.page=this.page
        nextTick((()=>{this.mitt1.emit('Reload')}))
      }
    },
    NextPage(){
      if (this.page==this.eightBlogCount){
        alert("已经是最后一页了")
      }else{
        this.page++
        router.push({path:'/Self',query:{'page':this.page}})
        router.currentRoute.value.query.page=this.page
        nextTick((()=>{
          this.mitt1.emit('Reload')
        }))
      }
    },
    SwitchPage(page) {
      router.push({path:'/Self',query:{"type":this.type,'page':page}})
      router.currentRoute.value.query.page=page
      nextTick((()=>{
        this.mitt1.emit('Reload')
      }))
    },
    ToBlog(blogId) {
      const destinction = router.resolve({path: '/Blog', query: {blogId: blogId}})
      window.open(destinction.href, "_blank")
    },
  }
}
</script>
<style scoped>
.focus{background-color:wheat;}
.focus1{font-size:1rem;border:solid 1px lightblue;min-height:40px;border:solid 2px lightblue;cursor:pointer;background-color:lightgreen;}
.nofocus{font-size:1rem;border:solid 1px lightblue;background-color:lightgrey;min-height:40px;border:solid 2px lightblue;cursor:pointer;}
.fen_ye{font-size:1rem;width:5rem;height:5rem;margin-left:1rem;}
</style>