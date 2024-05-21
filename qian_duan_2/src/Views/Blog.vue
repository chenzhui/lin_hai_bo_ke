<template>
<div v-if="isRouterAlive" style="display:flex;flex-direction:column;align-items:center;">
  <div style="display:flex;flex-direction:column;border:2px solid grey;width:80rem;margin-top:4rem;background-color:white;">
    <div style="display:flex;flex-direction:row;height:5rem;border-bottom:solid 1px black;">
      <p style="width:8rem;font-size:1.5rem;">{{blogData.title}}</p>
      <img @click="GoAuthor(blogData.authorId)" :src="'http://114.55.38.94/api/avatar/'+authorAvatar" style="width:5rem;height:5rem;border-radius:50%;cursor:pointer;">
      <div style="display:flex;flex-direction:column;">
        <div style="display:flex;">
          <p style="width:60rem;">作者:{{authorName}}</p>
          <img v-if="like==0" @click="LikeBlog" src="http://114.55.38.94/api/picture/Like.png" style="width:3rem;height:3rem;cursor:pointer;">
          <img v-else @click="LikeBlog" src="http://114.55.38.94/api/picture/Liked.png" style="width:3rem;height:3rem;cursor:pointer;">
          <p>{{blogData.liked}}</p>
          <img v-if="collection==1" @click="CollectBlog(blogData.blogId)" src="http://114.55.38.94/api/picture/collected.png" style="width:2rem;height:2rem;cursor:pointer;margin-left:2rem;">
          <img v-else @click="CollectBlog(blogData.blogId)" src="http://114.55.38.94/api/picture/collect.png" style="width:2rem;height:2rem;cursor:pointer;margin-left:2rem;">
        </div>
        <p style="margin-top:0.8rem;">发布时间{{blogData.birthTime}}</p>
      </div>
    </div>
    <div style="margin-left:2rem;" v-html="blogData.content"></div>
  </div>
  <div style="display:flex;flex-direction:row;border:1px solid grey;width:60rem;min-height:10rem;align-self:center;background-color:white;">
    <img :src="'http://114.55.38.94/api/avatar/'+avatar" style="width:3rem;height:3rem;margin:1rem;border-radius:50%;">
    <div style="display:flex;flex-direction:column;margin-top:1rem;">
      <textarea ref="content" @input="DescComment" style="resize:none;font-size:1rem;border:solid 1px black;width:40rem;height:5rem;" maxlength="100" v-model="comment"></textarea>
      <p style="font-size:1rem;">还可输{{couldComment}}字</p>
    </div>
    <input type="button" value="评论" style="background-color:deepskyblue;width:4rem;height:3rem;border-radius:0.5rem;color:white;margin-left:2rem;margin-top:2rem;cursor:pointer;font-size:1rem;" @click="Comment">
  </div>
  <div v-for="item in commentData" :key="item.cmtId" style="background-color:white;display:flex;flex-direction:column;flex-wrap:nowrap;border:0.1rem solid black;width:50rem;align-self:center;">
    <div style="display:flex;">
      <img style="width:3rem;height:3rem;border-radius:50%;margin-top:1rem;" :src="'http://114.55.38.94/api/avatar/'+item.avatar" >
      <div style="display:flex;flex-direction:column;">
        <p style="color:grey;margin-top:0.2rem;margin-left:1rem;width:5rem;">{{ item.name }}</p>
        <p style="margin-top:0.5rem;margin-left:1rem;min-height:3rem;">{{item.content}}</p>
        <div style="display:flex;flex-direction:row;margin-top:1rem;">
          <img v-if="item.relationship==0" src="http://114.55.38.94/api/picture/Like.png" @click='Like1(item.cmtId)' style="width:2rem;height:2rem;cursor:pointer;">
          <img v-else src="http://114.55.38.94/api/picture/Liked.png" @click="Like1(item.cmtId)" style="width:2rem;height:2rem;cursor:pointer;">
          <p style="width:2rem;height:2rem;">{{item.liked}}</p>
          <button @click="AddReply(commentData.indexOf(item))" style="height:2rem;width:4rem;margin-left:1rem;cursor:pointer;background-color:deepskyblue;border-radius:0.2rem;font-size:1rem;">回复</button>
          <p style="width:4rem;margin-left:1rem;height:2rem;">回复:{{item.kids}}</p>
          <button @click="ShowReply(item.cmtId)" style="height:2rem;width:6rem;cursor:pointer;background-color:deepskyblue;border-radius:0.2rem;font-size:1rem;">查看回复</button>
          <p style="margin-left:15rem;">{{item.birthTime}}</p>
        </div>
      </div>
    </div>
    <div v-if="isShow[item.cmtId]==1">
      <div v-for="item in replyData[item.cmtId]" :key="item.cmtId" style="display:flex;flex-direction:row;border-top:solid 1px black">
        <img style="width:30px;height:30px;" :src="'http://114.55.38.94/api/avatar/'+item.avatar">
        <p style="margin-left:1rem;color:grey;width:50px;">{{item.name}}</p>
        <p style="margin-left:10px;min-width:500px;">{{item.content}}</p>
        <img src="http://114.55.38.94/api/picture/Like.png" @click="Like2(item.cmtId,item.parentId)" style="width:30px;height:30px;cursor:pointer;">
        <p style="align-self:center;width:10px;">{{item.liked}}</p>
        <p style="margin-left:10px;align-self:end;">{{item.birthTime}}</p>
      </div>
    </div>
    <div v-if="canReply==commentData.indexOf(item)" style="display:flex;flex-direction:row;border-top:solid 1px black;height:70px;">
      <img :src="'http://114.55.38.94/api/avatar/'+avatar" style="width:60px;height:60px;border-radius:50%;margin:5px;">
      <textarea @input="DescReply()" style="resize:none;border:solid 1px black;width:300px;height:60px;margin-top:5px;"  v-model="reply" maxlength="100" :placeholder="'回复 @'+item.name+':'"></textarea>
      <p style="width:20px;font-size:5px;">可输{{this.couldReply}}字</p>
      <button @click="Reply(item.cmtId)" style="height:30px;width:40px;margin:20px;cursor:pointer;background-color:deepskyblue;border-radius:5px;color:white;">回复</button>
    </div>
  </div>
</div>
</template>
<script>
import axios from 'axios'
import {getCurrentInstance, nextTick, onMounted, ref} from "vue";
import router from "../Router/First";
export default {
setup(){
  const gci=getCurrentInstance()
  const content=ref(null)
  onMounted(async ()=>{
    content.value.placeholder="请输入您的评论"
    gci.data.blogId=router.currentRoute.value.query.blogId
    gci.data.id=gci.appContext.config.globalProperties.$id
    if(gci.appContext.config.globalProperties.$avatar!=null){gci.data.avatar=gci.appContext.config.globalProperties.$avatar}
    gci.data.mitt1=gci.appContext.config.globalProperties.$mitt1
    document.title='博客'+gci.data.blogId
    await axios.get('http://114.55.38.94/api/GetBlog',{params:{"blogId":gci.data.blogId}}).then((res)=>{gci.data.blogData=res.data.data})
    axios.get('http://114.55.38.94/api/GetNameById',{params:{"id":gci.data.blogData.authorId}}).then((res)=>{gci.data.authorName=res.data.data})
    axios.get('http://114.55.38.94/api/GetAvatarById',{params:{"id":gci.data.blogData.authorId}}).then((res)=>{gci.data.authorAvatar=res.data.data})
    axios.get('http://114.55.38.94/api/HasBlogLike',{params:{"id":gci.data.id,'blogId':gci.data.blogId}}).then((res)=>{gci.data.like=res.data.data})
    axios.get('http://114.55.38.94/api/HasCollection',{params:{"id":gci.data.id,"blogId":gci.data.blogData.blogId}}).then((res)=>{if(res.data.data==1){gci.data.collection=1}})
    axios.get("http://114.55.38.94/api/GetComment", {params:{"id":gci.data.id,"parentId":0,"blogId":gci.data.blogId}}).then((res)=>{gci.data.commentData=res.data.data})
    for(let i=0;i<gci.data.commentData.length;i++){
      if(gci.data.commentData[i].parentId!=undefined){
        this.ShowReply(gci.data.commentData[i].parentId)
      }
    }

    /*await axios.get('http://114.55.38.94/GetBlog',{params:{"blogId":gci.data.blogId}}).then((res)=>{gci.data.blogData=res.data.data})
    axios.get('http://114.55.38.94/GetNameById',{params:{"id":gci.data.blogData.authorId}}).then((res)=>{gci.data.authorName=res.data.data})
    axios.get('http://114.55.38.94/GetAvatarById',{params:{"id":gci.data.blogData.authorId}}).then((res)=>{gci.data.authorAvatar=res.data.data})
    axios.get('http://114.55.38.94/SelectCollection',{params:{"id":gci.data.id,"blogId":gci.data.blogData.blogId}}).then((res)=>{if(res.data.data==1){gci.data.collection=1}})
    axios.get("http://114.55.38.94/GetComment", {params:{"parentId":0,"blogId":gci.data.blogId}}).then((res)=>{gci.data.commentData=res.data.data})
    for(let i=0;i<gci.data.commentData.length;i++){
      if(gci.data.commentData[i].parentId!=undefined){
        this.ShowReply(gci.data.commentData[i].parentId)
      }
    }*/
  })
  return{
    content
  }
},
data(){
  return{
    blogData:{"avatar":"default.png"},
    comment:"",
    commentData:[],
    replyData:[[{}]],
    canReply:-1,
    reply:null,
    noClick: true,
    blogId:0,
    couldComment:100,
    couldReply:100,
    isShow:[],
    avatar:"default.png",
    isRouterAlive:true,
    id:0,
    authorName:"作者名称",
    authorAvatar:'default.png',
    collection:0,
    like:null,
  }
},
methods:{
  Comment(){
    const that=this
    axios.get("http://114.55.38.94/api/Comment",{params:{"id":this.id,"content":this.comment,"parentId":0,"blogId":this.blogId}}).then(function (res){
      if(res.data.data=="评论成功"){
        alert(res.data.data)
        that.isRouterAlive=false
        axios.get("http://114.55.38.94/api/GetComment", {params:{"parentId":0,"blogId":that.blogId}}).then(function (res){
          that.commentData=res.data.data
        })
        nextTick(()=>{that.isRouterAlive=true})
      }else{
        alert("评论失败")
      }
    })
  },
  Like1(cmtId){
    let that=this
    axios.get("http://114.55.38.94/api/LikeComment", {params:{"id":this.id,"cmtId":cmtId,"parentId":0,"blogId":this.blogId}}).then(function (res){
      for(let i=0;i<that.commentData.length;i++){
        if(that.commentData[i].cmtId==cmtId){
          that.commentData[i]=res.data.data
        }
      }
    })
  },
  Like2(cmtId,parentId){
    let that=this
    axios.get("http://114.55.38.94/api/LikeComment",{params:{"id":this.id,"cmtId":cmtId,"parentId":parentId,"blogId":this.blogId}}).then(function (res){
      that.replyData[parentId]=res.data.data
    })
  },
  LikeBlog(){
    const that=this
    axios.get('http://114.55.38.94/api/LikeBlog',{params:{"id":this.id,"blogId":this.blogId}}).then(function (res){
      that.blogData=res.data.data
      that.like=1-that.like
    })
  },
  AddReply(index){
    this.canReply=index
  },
  Reply(parentId){
    const that=this
    axios.get("/http://114.55.38.94/api/Comment",{params:{"id":this.id,"content":this.reply,"parentId":parentId,"blogId":this.blogId}}).then(function (res){
      if(res.data.data=="评论成功"){alert("回复成功");that.mitt1.emit('Reload')}
    })
  },
  ShowReply(parentId){
    let that=this
    axios.get("/http://114.55.38.94/api/GetComment",{params:{"parentId":parentId,"blogId":this.blogId}}).then(function (res){
      that.replyData[parentId]=res.data.data
    })
    if(this.isShow[parentId]!=1){this.isShow[parentId]=1}else{this.isShow[parentId]=0}
  },
  DescComment(){
    if(this.comment!=undefined){
      this.couldComment=100-this.comment.length
    }
  },
  DescReply(){
    if(this.reply!=undefined){
      this.couldReply=100-this.reply.length
    }
  },
  GoAuthor(authorId){
    router.push({path:'/Follow',query:{'authorId':authorId}})
  },
  CollectBlog(){
    const that=this
    axios.get('http://114.55.38.94/api/InsertCollection',{params:{"id":this.id,"blogId":this.blogId}}).then(()=>{
      axios.get('http://114.55.38.94/api/HasCollection',{params:{"id":that.id,"blogId":that.blogId}}).then((res)=>{
        that.collection=res.data.data
      })
    })
  },
}
}
</script>
<style scoped>*{margin:0;padding:0;list-style: none;text-decoration: none;border:0;outline: none;}</style>