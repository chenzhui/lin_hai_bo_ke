<template>
    <div style="display:flex;flex-direction:column;background-color:white;width:1000px;padding-top:10px;">
      <div style="display:flex;align-items:center;">
        <p style="font-size:30px;">标题:</p>
        <input type="text" ref="titleRef" @keydown.enter="typeRef.focus()" style="resize:none;height:30px;width:600px;border-radius:10px;font-size:25px;background-color:lightblue;" maxlength="30" v-model="blogData.title">
      </div>
      <!--<div style="display:flex;align-items:center;margin-top:10px;">
        <p>类型:</p>
        <input  ref="typeRef" type="text" v-model="blogData.type" style="background-color:lightgreen;width:140px;height:30px;border-radius:10px;" maxlength="10">
      </div>-->
      <div style="display:flex;align-items:center;margin-top:1%;">
        <p>类型:</p>
        <select v-model="kind" style="border:black solid 0.1rem;background-color: lightblue;">
          <option value="">{{blogData.type}}</option>
          <option v-for="item in types" :key="item.tId" :value="item.kind">{{item.kind}}</option>
        </select>
      </div>
      <div style="display:flex;">
        <input type="file" ref="file" accept="image/png, image/jpeg, image/gif, image/jpg" style="height:30px;margin-top:10px;margin-left:2%;opacity:1"/>
        <button @click="AddImage" style="cursor:pointer;background-color:deepskyblue;border-radius:5px;color:white;width:10%;height:30px;margin-top:10px;font-size:1rem;">添加图片</button>
      </div>
      <div ref="content" style="border-top:solid 1px lightgray;margin-top:1rem;width:60rem;border-bottom:solid 1px lightgray;min-height:700px;" @keydown.ctrl="Save">
        <div  contenteditable v-html="blogData.content" style="width:60rem;min-height:50px;resize:none;font-size:20px;"></div>
      </div>
      <button @click="FixBlog" style="align-self:center;background-color:deepskyblue;height:50px;width:200px;cursor:pointer;border-radius:5px;margin-top:30px;color:white;font-size:30px;">修改</button>
    </div>
</template>

<script>
import {getCurrentInstance, onMounted, ref} from "vue";
import axios from "axios";
import router from "../Router/First";
export default {
setup(){
  document.title="修改博客"
  const gci=getCurrentInstance()
  let titleRef=ref(null)
  let typeRef=ref(null)
  let content=ref(null)
  let file    = ref(null)
  onMounted(async ()=>{
    gci.data.id =1//gci.appContext.config.globalProperties.$id
    gci.data.blogId=router.currentRoute.value.query.blogId
    axios.get('http://114.55.38.94/api/SelectKinds').then((res)=>{gci.data.types=res.data.data})
    await axios.get('http://114.55.38.94/api/GetBlog',{params:{"blogId":gci.data.blogId}}).then(function (res){
      gci.data.blogData=res.data.data
    })
    //gci.data.kind=gci.data.blogData.type;
    //console.log(gci.data.kind)
  })
  return{
    titleRef,typeRef,content,file
  }
},
data(){
  return{
    blogData:{},
    types:[],
    titleOk:true,
    typeOk:true,
    fixOk:false,
    kind:"",
  }
},
mounted(){
  window.DeleteImage=this.DeleteImage
  //window.addEventListener("beforeunload", (event) => this.Remind(event))
},
methods:{
Remind(event){
  console.log(this.fixOk)
  if(this.fixOk==false){
    event.preventDefault()
    return ""
  }
},
CloseWindow(){
  console.log("CloseWindow")
  if(this.fixOk==false){
    console.log("false")
    let leave=confirm("您所作的修改还未保存，是否离开")
    if(leave==true){
      window.close()
    }
  }else{
    console.log("true")
    this.$router.go(-1)
  }
},
Save(e){
  if(e.keyCode==65){
    this.FixBlog()
  }
},
FixBlog(){
  let sureFix=confirm("点击“确定”修改,点击”取消“停止")
  if(sureFix==true){
    const that=this
    axios.post("http://114.55.38.94/api/FixBlog",{params:{'id':that.id,'title':that.title,'type':that.kind,'content':that.content.innerHTML}}).then(async function (res){
      if(res.data.data=="修改博客成功"){
        that.fixOk=true
        await alert("修改博客成功")
        console.log("修改博客成功")
        that.CloseWindow()
      }else{alert("系统繁忙,请稍后重试")}
    })
  }
  window.close()
},
AddImage(){
  let fd = new FormData
  fd.append("id",this.id)
  fd.append("file",this.file.files[0])
  const that=this
  axios.post("http://114.55.38.94/api/UploadImage",fd,{headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then((res)=>{
    that.file.value=''
    that.content.innerHTML=that.content.innerHTML+"<img src='http://114.55.38.94/api/picture/"+res.data.data+"' style='max-width:300px;max-height:300px;min-width:30px;min-height:30px'>"+ "<div contentEditable style='margin-top:20px;width:700px;min-height:100px;resize:none;font-size:25px;'></div>"
  })
},
DeleteImage(){
  event.currentTarget.parentElement.remove()
  axios.get("http://114.55.38.94/api/DeleteImage",{params:{id:this.id,imageName:event.currentTarget.parentElement.firstElementChild.getAttribute("src")}}).then(()=>{})
},
},
}
</script>
<style scoped></style>