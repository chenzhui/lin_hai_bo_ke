<template>
  <div style="display:flex;flex-direction:column;background-color:white;padding-left:2%;padding-top:10px;">
    <div style="display:flex;align-items:center;">
      <p style="font-size:1rem;">标题:</p>
      <input type="text" @keydown.enter="typeRef.focus()" style="resize:none;height:30px;width:50%;border-radius:10px;font-size:1rem;background-color:lightblue;" maxlength="100" v-model="title" @input="JudgeTitle" @blur="ExameTitle">
      <p v-if="!titleOk" style="color:red;margin-left:22%;">标题不能为空</p>
      <p v-else style="margin-left:2%;color:lightblue;">还可输入{{couldTitle}}字</p>
    </div>
    <div style="display:flex;align-items:center;margin-top:1%;">
      <p>类型:</p>
      <select v-model="kind" style="border:black solid 0.1rem;background-color: lightblue;">
        <option value="">请选择</option>
        <option v-for="item in types" :key="item.tId" :value="item.kind">{{item.kind}}</option>
      </select>
    </div>
    <div style="display:flex;">
      <input type="file" ref="file" accept="image/png, image/jpeg, image/gif, image/jpg" style="height:30px;margin-top:10px;margin-left:2%;opacity:1"/>
      <button @click="AddImage" style="cursor:pointer;background-color:deepskyblue;border-radius:5px;color:white;width:10%;height:30px;margin-top:10px;font-size:1rem;">添加图片</button>
    </div>
    <div ref="content" style="border:solid 1px lightgray;margin-top:10px;width:50%;min-height:700px;" @keydown.ctrl="Save">
      <div  contenteditable style="width:100%;min-height:50px;resize:none;font-size:20px;"></div>
    </div>
    <button @click="ReleaseBlog" style="background-color:deepskyblue;height:50px;width:20%;cursor:pointer;border-radius:5px;margin-top:30px;color:white;font-size:2rem;margin-left:15%">提交</button>
  </div>
</template>
<script>
import {getCurrentInstance,onMounted, ref,} from "vue";
import {onBeforeRouteLeave} from "vue-router";
import axios from "axios";
import router from "../Router/First";
export default {
setup(){
  const gci = getCurrentInstance()
  let typeRef = ref(null)
  let blogRef = ref(null)
  let content = ref(null)
  let file    = ref(null)
  onBeforeRouteLeave((to,from,next)=>{
    let a=confirm("是否离开")
    if(a){
      next(true)
    }else{
      next(false)
    }
  })
  onMounted(async()=>{
    document.title = "发布博客"
    document.documentElement.style.fontSize = document.documentElement.clientWidth/100 + 'px';
    gci.data.mitt1=gci.appContext.config.globalProperties.$mitt1
    gci.data.id = gci.appContext.config.globalProperties.$id
    gci.data.avatar = gci.appContext.config.globalProperties.$avatar
    axios.get('http://114.55.38.94/api/SelectKinds').then((res)=>{gci.data.types=res.data.data})
  })
  return {
    blogRef,typeRef,content,file
  }
},
data(){
  return {
    id: "",
    title: "",
    titleOk: true,
    couldTitle: 30,
    kind:"",
    couldType: 10,
    imageUrl:null,
    types:[],
  }
},
mounted(){
  window.DeleteImage=this.DeleteImage
  window.addEventListener("beforeunload", (e) => this.beforeunloadHandler(e));
},
methods:{
beforeunloadHandler(e) {
  if (e) {
    e = e || window.event;
    if (e) {
      e.returnValue = "关闭提示";
    }
    return "关闭提示";
  }
},
Save(e){
  if(e.keyCode==65){
    this.ReleaseBlog()
  }
},
ReleaseBlog(){
  const that=this
  axios.get('http://114.55.38.94/api/ReleaseBlog',{params:{'id':that.id,'title':that.title,'type':that.kind,'content':that.content.innerHTML}}).then((res)=>{
    if(res.data.data =="发布成功"){
      alert("发布成功")
      router.push('/Index')
      that.mitt1.emit('Reload')
    }else{alert("发布失败")}})
},
AddImage(){
  let fd = new FormData
  fd.append("id",this.id)
  fd.append("file",this.file.files[0])
  const that=this
  axios.post("http://114.55.38.94/api/UploadImage",fd,{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then((res)=>{
    that.file.value=''
    that.content.innerHTML=that.content.innerHTML+
        "<div style='display:flex;flex-direction:row'> " +
          "<img src='http://114.55.38.94/api/picture/"+res.data.data+"' style='max-width:300px;max-height:300px;min-width:30px;min-height:30px'>" +
          "<img src='http://114.55.38.94/api/picture/deleteIcon.png' onclick='DeleteImage()' style='width:2rem;height:2rem;margin-left:2rem;cursor:pointer;'>"+
        "</div>" +
        "<div contentEditable style='width:700px;resize:none;font-size:25px;'></div>"
  })
},
JudgeTitle() {
  this.ExameTitle()
  this.couldTitle = 30 - this.title.length
},
JudgeType() {
  this.ExameType()
  this.couldType = 10 - this.type.length
},
ExameTitle() {
  if(this.title.length != 0) {
    this.titleOk = true
  } else {
    this.titleOk = false
  }
},
DeleteImage(){
  event.currentTarget.parentElement.remove()
  axios.get("http://114.55.38.94/api/DeleteImage",{params:{id:this.id,imageName:event.currentTarget.parentElement.firstElementChild.getAttribute("src")}}).then(()=>{})
},
ExameType(){
  if(this.type.length != 0) {
    this.typeOk = true
  } else {
    this.typeOk = false
  }
},
}
}
</script><style scoped></style>