<template>
  <div v-if="isRouterAlive" style="display:flex;border:solid 1px grey;margin-top:50px;margin-left:10%;width:80%;height:50rem;background-color:white;">
    <div style="display:flex;flex-direction:column;width:15%;border-right:solid 1px grey;">
      <button @click="GoOwner" class="leftButton">创作空间</button>
      <button @click="GoChangeAvater" class="leftButton">更改头像</button>
      <button @click="GoRelease" class="leftButton">发布博客</button>
      <button @click="GoCollection" class="leftButton">我的收藏</button>
      <button @click="GoChangeName" class="leftButton">修改昵称</button>
      <button @click="GoChangePassword" class="leftButton">修改密码</button>
      <button @click="Quit" class="leftButton">退出登录</button>
    </div>
    <div v-if="index==0" style="display:flex;flex-direction:column;margin-left:1rem">
      <div v-if="id==undefined" style="margin-top:3rem;font-size:2rem;">暂未登录</div>
      <div v-else style="display:flex;flex-direction:row;margin-top:5rem;">
        <div style="display:flex;flex-direction:column;">
          <button style="background-color:lightgrey;height:3rem;border:solid 0.1rem lightblue;font-size:1rem;">博客分类</button>
          <button @click="GetTypeBlogs()" :class="{'focus1':type=='all','nofocus':type!='all'}">全部</button>
          <button v-for="item in types" :key="item" @click="GetTypeBlogs(item.kind)" :class="{'focus1':type==item.kind,'nofocus':type!=item.kind}">{{item.kind}}</button>
        </div>
        <div v-if="blogs.length!=0" >
          <div style="display:flex;flex-direction:column;height:34rem;">
            <div v-for="item in blogs.slice(page*8-8,page*8)" :key="item.blogId" style="display:flex;flex-direction:row;background-color:#FFFAFA;border-bottom:solid 1px lightgrey;font-size:1rem;width:55rem;height:5rem;">
              <div style="display:flex;">
                <div style="display:flex;flex-direction:column;">
                  <p style="font-size:1.2rem;width:30rem">{{ item.title }}</p>
                  <p style="margin-top:1rem;font-size:1rem;">{{item.birthTime}}</p>
                </div>
                <p style="background-color:lightgreen;margin-top:1rem;height:2rem;width:4rem;margin-left:5rem;color:white;border-radius:5px;font-size:1rem;text-align:center;">{{ item.type }}</p>
              </div>
              <button @click="ToBlog(item.blogId)" style="margin-left:5rem;width:4rem;cursor:pointer;background-color:deepskyblue;border-left:solid 1px lightgrey;font-size:1rem;">详情</button>
              <button @click="FixBlog(item.blogId)" style="width:4rem;cursor:pointer;background-color:deepskyblue;border-left:solid 1px lightgrey;font-size:1rem;">修改</button>
              <button @click="DeleteBlog(item.blogId)" style="width:4rem;cursor:pointer;background-color:deepskyblue;border-left:solid 1px lightgrey;font-size:1rem;">删除</button>
            </div>
          </div>
          <div style="display:flex;margin-top:2rem;">
            <button @click="PreviousPage" style="cursor:pointer;width:3rem;height:3rem;margin-left:2rem;font-size:1rem;">上一页</button>
            <div style="width:35rem;display:flex;flex-direction: row">
              <div v-if="eightBlogCount<9" style="display:flex;flex-direction:row;" >
                <button v-for="item in eightBlogCount" :key="item.blogId" @click="SwitchPage(item)" class="fen_ye" :class="{'focus':item==page}">{{item}}</button>
              </div>
              <div v-else style="display:flex;width:35rem;">
                <div v-if="page>5" style="display:flex;width:35rem;">
                  <button @click="SwitchPage(1)" class="fen_ye">1</button>
                  <button @click="SwitchPage(3)" class="fen_ye">···</button>
                  <div v-if="page==eightBlogCount">
                    <button @click="SwitchPage(page-4)" class="fen_ye">{{page-4}}</button>
                    <button @click="SwitchPage(page-3)" class="fen_ye">{{page-3}}</button>
                    <button @click="SwitchPage(page-2)" class="fen_ye">{{page-2}}</button>
                    <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
                    <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat">{{page-0}}</button>
                  </div>
                  <div v-else-if="page==eightBlogCount-1">
                    <button @click="SwitchPage(page-3)" class="fen_ye" >{{page-3}}</button>
                    <button @click="SwitchPage(page-2)" class="fen_ye" >{{page-2}}</button>
                    <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
                    <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat;">{{page-0}}</button>
                    <button @click="SwitchPage(page+1)" class="fen_ye">{{page+1}}</button>
                  </div>
                  <div v-else>
                    <button @click="SwitchPage(page-2)" class="fen_ye">{{page-2}}</button>
                    <button @click="SwitchPage(page-1)" class="fen_ye">{{page-1}}</button>
                    <button @click="SwitchPage(page)" class="fen_ye" style="background-color:wheat">{{page-0}}</button>
                    <button @click="SwitchPage(page+1)" class="fen_ye">{{page+1}}</button>
                    <button @click="SwitchPage(page+2)" class="fen_ye">{{page+2}}</button>
                  </div>
                </div>
                <div v-else style="width:35rem;">
                  <button @click="SwitchPage(1)" class="fen_ye" :class="{'focus':1==page}">1</button>
                  <button @click="SwitchPage(2)" class="fen_ye" :class="{'focus':2==page}">2</button>
                  <button @click="SwitchPage(3)" class="fen_ye" :class="{'focus':3==page}">3</button>
                  <button @click="SwitchPage(4)" class="fen_ye" :class="{'focus':4==page}">4</button>
                  <button @click="SwitchPage(5)" class="fen_ye" :class="{'focus':5==page}">5</button>
                  <button @click="SwitchPage(6)" class="fen_ye" :class="{'focus':6==page}">6</button>
                  <button @click="SwitchPage(7)" class="fen_ye" :class="{'focus':7==page}">7</button>
                </div>
              </div>
            </div>
            <button @click="NextPage" style="cursor:pointer;width:3rem;height:3rem;margin-left:1rem;font-size:1rem;">下一页</button>
            <button @click="LastPage" style="cursor:pointer;width:4rem;height:3rem;margin-left:1rem;font-size:1rem;">最后一页</button>
            <p style="margin-left:1rem;margin-top:1rem;">共{{eightBlogCount}}页</p>
          </div>
        </div>
      </div>
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
    let file = ref(null)
    let typeRef = ref(null)
    let blogRef = ref(null)
    onMounted(async () => {
      document.title = "个人主页"
      gci.data.type=router.currentRoute.value.query.type
      gci.data.page=router.currentRoute.value.query.page
      if(gci.data.type==undefined){gci.data.type='all'}
      if(gci.data.page==undefined){gci.data.page=1}
      gci.data.id=gci.appContext.config.globalProperties.$id
      gci.data.avatar = gci.appContext.config.globalProperties.$avatar
      gci.data.mitt1 = gci.appContext.config.globalProperties.$mitt1
      axios.get('http://114.55.38.94/api/SelectKinds').then((res)=>{gci.data.types=res.data.data})
      axios.get('http://114.55.38.94/api/GetOwnerBlogs',{params: {"id": gci.data.id,"type":gci.data.type}}).then(function (res){
        gci.data.blogs = res.data.data
        gci.data.eightBlogCount = Math.ceil(gci.data.blogs.length/8)
      })
    })
    return {file,blogRef,typeRef,}
  },
  data(){
    return {
      id: "",
      index:0,
      title: "",
      titleOk: true,
      couldTitle: 30,
      type: "all",
      typeOk: true,
      couldType: 10,
      blogs: [],
      eightBlogCount: 0,
      page: 1,
      isRouterAlive: true,
      collections:[],
      types:"",
    }
  },
  methods:{
    GoOwner(){this.index=0},
    GoChangeAvater(){this.index=1},
    GetTypeBlogs(type){
      router.push({path:'/Self',query:{'type':type,'page':1}})
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
    FixBlog(blogId) {
      router.push({path:'/Fixing',query: {blogId: blogId}})
      //window.open(destinction.href,"_blank")
    },
    DeleteBlog(blogId) {
      let sureDelete = confirm("点击’确认‘删除,点击’取消‘停止")
      if(sureDelete) {
        const that = this
        axios.get('http://114.55.38.94/api/DeleteBlog', {params: {"id": this.id, "blogId": blogId}}).then(function (res) {
          if(res.data.data=="删除博客成功"){
            alert("删除博客成功")
            that.mitt1.emit('Reload')
          }else{
            alert("删除博客失败")
          }
        })
      }
    },
    GoRelease() {
      const destinction = router.resolve({path: '/Release'})
      window.open(destinction.href, "_blank")
    },
    GoCollection(){
      const destinction = router.resolve({path: '/Collection'})
      window.open(destinction.href, "_blank")
    },
    GoChangeName(){
      router.push('/ChangeName')
    },
    GoChangePassword(){
      router.push('/ChangePassword')
    },
    UpAvatar() {
      let formdata = new FormData
      formdata.append("id", this.id)
      formdata.append("file", this.file.files[0])
      axios.post("http://114.55.38.94/api/UploadAvatar",formdata,{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(function (res) {
        if (res.data.data =="上传成功"){
          router.push('/Index')
          alert(res.data.data)
        }else{
          alert("上传失败")
        }
      })
    },
    Quit(){
      if(this.id==undefined){alert("暂未登录")}else{
        let sureLogout = confirm("点击“确定”退出,点击”取消“停止")
        if(sureLogout == true){
          const that = this
          axios.get("http://114.55.38.94/api/Logout", {params: {"id": this.id}}).then(function (res) {
            if (res.data.data=="退出成功") {
              alert("退出成功")
              router.push('/Index')
              that.mitt1.emit('Reload')
            }else{
              alert("您还未登录")
            }
          })
        }
      }
    },
  }
}
</script>
<style scoped>*{margin:0;padding:0;list-style:none;text-decoration:none;border:0;outline:none;}
.focus{background-color:wheat;}
.focus1{font-size:1rem;border:solid 1px lightblue;height:3rem;border:solid 2px lightblue;cursor:pointer;background-color:lightgreen;}
.nofocus{font-size:1rem;border:solid 1px lightblue;background-color:lightgrey;height:3rem;border:solid 2px lightblue;cursor:pointer;}
.fen_ye{font-size:1rem;width:3rem;height:3rem;margin-left:1.5rem;cursor:pointer;}
.leftButton{background-color:lightblue;height:8rem;border-bottom:solid 1px grey;cursor:pointer;font-size:1rem;}
</style>