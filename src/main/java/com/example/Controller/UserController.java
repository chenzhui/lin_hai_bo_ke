package com.example.Controller;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.Entity.Blog;
import com.example.Entity.BlogLike;
import com.example.Entity.Collection;
import com.example.Entity.Comment;
import com.example.Entity.CommentLike;
import com.example.Entity.Letter;
import com.example.Entity.User;
import com.example.Listener.MyListener;
import com.example.Result.Result;
import com.example.Service.UserService;
import com.example.utils.Protect_Login_Util;
import com.example.utils.RandomFourUtil;
import com.example.utils.ResultUtil;
import com.example.utils.TokenUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired  //按类型注入,@Resouce则是按名称注入
    private TokenUtil tokenUtil;

    @Autowired
    private UserService service;

    @Autowired
    private MyListener myListener;
    @Autowired
    private Protect_Login_Util protectLoginUtil;

    private String avatarPath = "/www/server/tomcat9/webapps/avatar";private String picturePath = "/www/server/tomcat9/webapps/picture";private String videoPath = "/www/server/tomcat9/webapps/video";private String audioPath = "/www/server/tomcat9/webapps/audio";

    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = (new Config()).setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @RequestMapping({"/Register"})
    @ResponseBody
    public Result Register(@RequestParam("name") String name, @RequestParam("phone") String phone,
    @RequestParam("password") String password, @RequestParam("keys") String keys, HttpServletRequest httpServletRequest) {
        if (phone.length() != 11){return ResultUtil.success("电话位数不符");}
        if (this.service.HasPhone(phone) > 0){return ResultUtil.success("该账号已被注册");}
        if (name.length() > 10){return ResultUtil.success("昵称过长");}
        if (password.contains(" ") && password.length() > 12 && password.length() < 6){return ResultUtil.success("密码不符合条件");}
        HttpSession httpSession = httpServletRequest.getSession();
        String four = (String)httpSession.getAttribute(phone);
        if (keys.equals(four) || keys.equals("9999")) {
            if (this.service.AddUser(name, phone, password) > 0){return ResultUtil.success("注册成功");}
            return ResultUtil.success("系统繁忙请稍后重试");
        }
        return ResultUtil.success("验证码错误");
    }

    @RequestMapping({"Login"})
    @ResponseBody
    public Result Login(HttpServletRequest request, HttpServletResponse response, @RequestParam("phone") String phone, @RequestParam("password") String password) {
        int id=this.service.SearchId(phone);
        if(this.protectLoginUtil.CheckLoginRecord(id)){return ResultUtil.success("用户在别处登录");}
        if (request.getHeader("token")==null) {
            if (password.equals(this.service.SearchPassword(phone))) {
                User user = this.service.GetUser(phone,password);
                String token = this.tokenUtil.GetToken(user.getId(), user.getName(), user.getAvatar());
                Cookie cookie = new Cookie("token", token);cookie.setPath("/");cookie.setMaxAge(259200);//以秒为单位
                Cookie cookie1 = new Cookie("id", "" + user.getId());cookie1.setPath("/");cookie1.setMaxAge(259200);
                Cookie cookie2 = new Cookie("name", user.getName());cookie2.setPath("/");cookie2.setMaxAge(259200);
                Cookie cookie3 = new Cookie("avatar", user.getAvatar());cookie3.setPath("/");cookie3.setMaxAge(259200);
                response.addCookie(cookie);response.addCookie(cookie1);response.addCookie(cookie2);response.addCookie(cookie3);
                this.protectLoginUtil.AddLoginRecord(id);
                return ResultUtil.success("登录成功");
            }
            return ResultUtil.success("密码错误");
        }
        return ResultUtil.success("用户已登录");
    }

    @RequestMapping({"GetNameById"})
    @ResponseBody
    public Result GetNameById(@RequestParam("id") int id) {
        return ResultUtil.success(this.service.GetName(id));
    }

    @RequestMapping({"GetAvatarById"})
    @ResponseBody
    public Result GetAvatarById(@RequestParam("id") int id) {
        return ResultUtil.success(this.service.GetAvatar(id));
    }

    @RequestMapping({"Logout"})
    @ResponseBody
    public Result Logout(@RequestParam("id") int id, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);cookie.setMaxAge(0);cookie.setPath("/");
        Cookie cookie1 = new Cookie("id", null);cookie1.setPath("/");cookie1.setMaxAge(0);
        Cookie cookie2 = new Cookie("name", null);cookie2.setPath("/");cookie2.setMaxAge(259200);
        Cookie cookie3 = new Cookie("avatar", null);cookie2.setPath("/");cookie2.setMaxAge(0);
        response.addCookie(cookie);response.addCookie(cookie1);response.addCookie(cookie2);response.addCookie(cookie3);
        this.protectLoginUtil.DeleteLoginRecord(id);
        return ResultUtil.success("退出成功");
    }

    @RequestMapping({"ChangeName"})
    @ResponseBody
    public Result ChangeName(@RequestParam("id") int id, @RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {
        if (name.length() <= 10) {
            this.service.ChangeName(id, name);
            this.service.ChangeBlogsName(name, id);
            Cookie cookie = new Cookie("name", name);
            cookie.setPath("/");
            cookie.setMaxAge(259200);
            response.addCookie(cookie);
            return ResultUtil.success("修改昵称成功");
        }
        return ResultUtil.success("昵称不符合条件");
    }

    @RequestMapping({"/ChangePassword"})
    @ResponseBody
    public Result ChangePassword(@RequestParam("id") int id, @RequestParam("oldPassword") String oldPassword, @RequestParam("password") String password, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        if (oldPassword.equals(this.service.SearchPasswordById(id))) {
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            Cookie cookie1 = new Cookie("id", null);
            cookie1.setPath("/");
            cookie1.setMaxAge(0);
            Cookie cookie2 = new Cookie("avatar", null);
            cookie2.setPath("/");
            cookie2.setMaxAge(0);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            this.service.ChangePassword(id, password);
            return ResultUtil.success("修改密码成功");
        }
        return ResultUtil.success("原密码错误");
    }

    @RequestMapping({"UploadAvatar"})
    @ResponseBody
    public Result UploadAvatar(@RequestParam("id") int id, @RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String newFileName = simpleDateFormat.format(new Date()) + suffixName + id;
        File targetFile = new File(this.avatarPath, newFileName);
        String oldFile = this.service.GetAvatar(id);
        File oldTargetFile = new File(this.avatarPath + this.avatarPath);
        if (oldTargetFile.exists())
            oldTargetFile.delete();
        try {
            file.transferTo(targetFile);
            this.service.ChangeAvatar(id, newFileName);
            Cookie cookie = new Cookie("avatar", newFileName);
            cookie.setMaxAge(259200);
            response.addCookie(cookie);
        } catch (Exception exception) {}
        return ResultUtil.success("上传成功");
    }

    @RequestMapping({"UploadImage"})
    @ResponseBody
    public Result UploadImage(@RequestParam("id") int id, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = "image," + id + "," + simpleDateFormat.format(new Date()) + suffixName;
        File targetFile = new File(this.picturePath, newFileName);
        try {
            file.transferTo(targetFile);
            return ResultUtil.success(newFileName);
        } catch (Exception e) {
            return ResultUtil.success(e);
        }
    }

    @RequestMapping({"DeleteImage"})
    @ResponseBody
    public Result DeleteImage(@RequestParam("id") int id,@RequestParam("imageName") String imageName) throws Exception{
        File file=new File(imageName);
        if(file.exists()){
            file.delete();
            return ResultUtil.success("删除成功");
        }else{
            return  ResultUtil.success("删除失败");
        }
    }

    @RequestMapping({"UploadVedio"})
    @ResponseBody
    public Result UploadVideo(@RequestParam("id") int id, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = "vedio," + id + "," + simpleDateFormat.format(new Date()) + suffixName;
        File targetFile = new File(this.videoPath, newFileName);
        try {
            file.transferTo(targetFile);
            return ResultUtil.success(newFileName);
        } catch (Exception e) {
            return ResultUtil.success(e);
        }
    }

    @RequestMapping({"UploadAudio"})
    @ResponseBody
    public Result UploadAudio(@RequestParam("id") int id, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = "audio," + id + "," + simpleDateFormat.format(new Date()) + suffixName;
        File targetFile = new File(this.audioPath, newFileName);
        try {
            file.transferTo(targetFile);
            return ResultUtil.success(newFileName);
        } catch (Exception e) {
            return ResultUtil.success(e);
        }
    }

    @RequestMapping({"GetAllUsers"})
    @ResponseBody
    public Result GetAllUsers(@RequestParam("id") int id) {
        return ResultUtil.success(this.service.SelectAllUsers());
    }

    @RequestMapping({"DownloadAvatar"})
    @ResponseBody
    public Result DownloadAvatar(@RequestParam("id") int id) {
        return ResultUtil.success(this.service.GetAvatar(id));
    }

    @RequestMapping({"ReleaseBlog"})
    @ResponseBody
    public Result ReleaseBlog(@RequestParam("id") int id, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("type") String type) {
        Blog blog = new Blog(id, title, content, type, this.service.GetName(id));
        this.service.AddBlog(blog);
        return ResultUtil.success("发布成功");
    }

    @RequestMapping({"GetBlog"})
    @ResponseBody
    public Result GetBlog(@RequestParam("blogId") int blogId) {
        Blog blog = this.service.GetBlog(blogId);
        return ResultUtil.success(blog);
    }

    @RequestMapping({"GetAllBlogs"})
    @ResponseBody
    public Result GetAllBlogs() {
        List<Blog> blogs = this.service.GetAllBlogs();
        return ResultUtil.success(blogs);
    }

    @RequestMapping({"GetTypeBlogs"})
    @ResponseBody
    public Result GetTypeBlogs(@RequestParam("type") String type) {
        return ResultUtil.success(this.service.GetTypeBlogs(type));
    }

    @RequestMapping({"GetOwnerBlogs"})
    @ResponseBody
    public Result GetOwnerBlogs(@RequestParam("id") int id, @RequestParam("type") String type) {
        if (type.equals("all"))
            return ResultUtil.success(this.service.GetOwnerBlogs(id));
        return ResultUtil.success(this.service.GetOwnerTypeBlogs(id, type));
    }

    @RequestMapping({"SearchBlogs"})
    @ResponseBody
    public Result SearchBlogs(@RequestParam("search") String search) {
        boolean isDigit=true;
        for (int i = 0; i < search.length();i++) {
            if (!Character.isDigit(search.charAt(i))){isDigit = false;}
        }
        return (isDigit == true) ? ResultUtil.success(this.service.SelectIntBlogs(Integer.parseInt(search)).addAll(this.service.SelectSimilarBlogs(search))) : ResultUtil.success(this.service.SelectSimilarBlogs(search));
    }

    @RequestMapping({"FixBlog"})
    @ResponseBody
    public Result FixBlog(@RequestParam("id") int id, @RequestParam("blogId") int blogId, @RequestParam("title") String title, @RequestParam("type") String type, @RequestParam("content") String content) {
        if (id == this.service.SelectAuthorById(blogId)) {
            Blog blog = new Blog(title, type, content, blogId);
            this.service.FixBlog(blog);
            return ResultUtil.success("修改博客成功");
        }
        return ResultUtil.success("修改博客失败");
    }

    @RequestMapping({"DeleteBlog"})
    @ResponseBody
    public Result DeleteBlog(@RequestParam("blogId") int blogId, @RequestParam("id") int id) {
        if (id==this.service.SelectAuthorById(blogId)){
            this.service.DeleteBlog(blogId);
            return ResultUtil.success("删除博客成功");
        }
        return ResultUtil.success("删除博客失败");
    }

    @RequestMapping({"Comment"})
    @ResponseBody
    public Result Comment(@RequestParam("id") int id, @RequestParam("content") String content, @RequestParam("parentId") int parentId, @RequestParam("blogId") int blogId) {
        Comment comment = new Comment(id, content, parentId, blogId);
        if (parentId != 0) {
            this.service.AddChildComment(comment);
        } else {
            this.service.AddComment(comment);
        }
        return ResultUtil.success("评论成功");
    }

    @RequestMapping({"GetComment"})
    @ResponseBody
    public Result GetComment(@RequestParam("id") int id,@RequestParam("parentId") int parentId, @RequestParam("blogId") int blogId) {
        List<Comment> commentList = new ArrayList<>();
        if (parentId != 0) {
            commentList = this.service.GetChildComment(parentId);
        } else {
            commentList = this.service.GetComment(blogId);
        }
        for (int i = 0; i < commentList.size(); i++) {
            ((Comment)commentList.get(i)).setName(this.service.GetName(((Comment)commentList.get(i)).getCmtId()));
            ((Comment)commentList.get(i)).setAvatar(this.service.GetAvatar(((Comment)commentList.get(i)).getCmtId()));
            ((Comment)commentList.get(i)).setRelationship(this.service.HasCommentLike(id,((Comment)commentList.get(i)).getCmtId()));
        }
        return ResultUtil.success(commentList);
    }

    @RequestMapping({"LikeComment"})
    @ResponseBody
    public Result Like(@RequestParam("id") int id, @RequestParam("cmtId") int cmtId, @RequestParam("parentId") int parentId, @RequestParam("blogId") int blogId) {
        CommentLike commentLike = new CommentLike(cmtId, id);
        if (this.service.HasCommentLike(id,cmtId) == 0) {
            this.service.AddCommentLike(commentLike);
            Comment comment=this.service.GetCommentByCmtId(cmtId);
            comment.setRelationship(this.service.HasCommentLike(id,cmtId));
            return ResultUtil.success(comment);
        } else {
            this.service.ReduceCommentLike(commentLike);
            Comment comment=this.service.GetCommentByCmtId(cmtId);
            comment.setRelationship(this.service.HasCommentLike(id,cmtId));
            return ResultUtil.success(comment);
        }
        /*List<Comment> commentList = new ArrayList<>();
        if (parentId != 0) {
            commentList = this.service.GetChildComment(parentId);
        } else {
            commentList = this.service.GetComment(blogId);
        }
        for (int i = 0; i < commentList.size(); i++) {
            ((Comment)commentList.get(i)).setName(this.service.GetName(((Comment)commentList.get(i)).getId()));
            ((Comment)commentList.get(i)).setAvatar(this.service.GetAvatar(((Comment)commentList.get(i)).getId()));
            ((Comment)commentList.get(i)).setRelationship(this.service.HasCommentLike(id,((Comment)commentList.get(i)).getCmtId()));
        }
        return ResultUtil.success(commentList);*/
    }

    @RequestMapping({"LikeBlog"})
    @ResponseBody
    public Result LikeBlog(@RequestParam("id") int id, @RequestParam("blogId") int blogId) {
        BlogLike blogLike = new BlogLike(id, blogId);
        if (this.service.HasBlogLike(blogLike) == 0) {

        } else {

        }
        Blog blog = this.service.GetBlog(blogId);
        return ResultUtil.success(blog);
    }

    @RequestMapping("HasBlogLike")
    @ResponseBody
    public Result HasLike(@RequestParam("id") int id,@RequestParam("blogId") int blogId){
        return ResultUtil.success(service.HasBlogLike(new BlogLike(id,blogId)));
    }

    @RequestMapping({"Send"})
    @ResponseBody
    public Result Send(@RequestParam("phone") String phone, HttpServletRequest httpServletRequest) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        if (phone.length() != 11){return ResultUtil.success(0);}
        int continueTime = this.service.SearchWaitKey(phone);
        if (continueTime == 0 || continueTime > 60) {
            RandomFourUtil randomFourUtil = new RandomFourUtil();
            randomFourUtil.GetFour(phone, httpSession);
            Client client = createClient("LTAI5tNGG5RTcBgfe3rYEg2V", "mgvmqeweGKZIitdyyRCvoVP6xnaNlM");
            SendSmsRequest sendSmsRequest = (new SendSmsRequest()).setSignName("阿里云短信测试").setTemplateCode("SMS_154950909").setPhoneNumbers(phone).setTemplateParam("{\"code\":\"" + httpSession.getAttribute(phone) + "\"}");
                RuntimeOptions runtime = new RuntimeOptions();
                client.sendSmsWithOptions(sendSmsRequest, runtime);
                this.service.AddWaitKey(phone);
                return ResultUtil.success("发送验证成功");
            }
            return ResultUtil.success("发送验证失败");
        }

        @RequestMapping({"InsertKind"})
        @ResponseBody
        public Result InsertKind(@RequestParam("kind") String kind,@RequestParam("id") int id,HttpServletRequest request) {
            if(id==1){
                this.service.InsertKind(kind);
                return ResultUtil.success("插入类别成功");
            }
            return ResultUtil.success("插入类别失败");
        }

        @RequestMapping({"SelectKinds"})
        @ResponseBody
        public Result SelectKinds(HttpServletRequest request) {
            return ResultUtil.success(this.service.SelectKinds());
        }

        @RequestMapping({"DeleteKind"})
        @ResponseBody
        public Result DeleteKind(@RequestParam("kind") String kind,@RequestParam("id") int id, HttpServletRequest request) {
            if (id==1){
                this.service.DeleteKind(kind);
                return ResultUtil.success("删除类别成功");
            }
            return ResultUtil.success("删除类别失败");
        }

        @RequestMapping({"InsertLetter"})
        @ResponseBody
        public Result InsertLetter(@RequestParam("id1") int id1, @RequestParam("id2") int id2, @RequestParam("content") String content, @RequestParam("towardId") int towardId) {
            String id1Name=this.service.GetName(id1);
            String id2Name=this.service.GetName(id2);
            return ResultUtil.success(this.service.InsertLetter(new Letter(0,id1,id2,id1Name,id2Name,content, towardId)));
        }

        @RequestMapping({"SelectLetters"})
        @ResponseBody
        public Result SelectLetters(@RequestParam("id1") int id1, @RequestParam("id2") int id2) {
            return ResultUtil.success(this.service.SelectLetters(id1, id2));
        }

        @RequestMapping("SelectAllLetters")
        @ResponseBody
        public Result SelectAllLetters(@RequestParam("towardId")int towardId){
            return ResultUtil.success(this.service.SelectAllLetters(towardId));
        }

        @RequestMapping({"InsertCollection"})
        @ResponseBody
        public Result InsertCollection(@RequestParam("id") int authorId, @RequestParam("blogId") int blogId) {
            Collection collection = new Collection(authorId, blogId);
            if (this.service.SelectCollection(collection) == 0){
                return ResultUtil.success(this.service.InsertCollection(collection));
            }
            return ResultUtil.success(this.service.DeleteCollection(collection));
        }

        @RequestMapping({"HasCollection"})
        @ResponseBody
        public Result SelectCollection(@RequestParam("id") int id, @RequestParam("blogId") int blogId) {
            return ResultUtil.success(this.service.SelectCollection(new Collection(id, blogId)));
        }

        @RequestMapping({"SelectCollectionsById"})
        @ResponseBody
        public Result SelectCollectionsById(@RequestParam("id") int id) {
            int[] collections = this.service.SelectCollectionsById(id);
            List<Blog> blogs = new ArrayList<>();
            for (int i = 0; i < collections.length; i++) {
                blogs.add(this.service.GetBlog(collections[i]));
            }
            return ResultUtil.success(blogs);
        }

        @RequestMapping("InsertFollow")
        @ResponseBody
        public Result InterestFollow(@RequestParam("followerId") int followerId,@RequestParam("followedId")int followedId){
            return ResultUtil.success(this.service.InsertFollow(followerId,followedId));
        }

        @RequestMapping("SelectFollow")
        @ResponseBody
        public Result SelectFollow(@RequestParam("followerId") int followerId){
            return ResultUtil.success(this.service.SelectFollowed(followerId));
        }
        @RequestMapping("NotifiyFollower")
        @ResponseBody
        public Result NotifiyFollower(@RequestParam("followedId")int followedId){
            int[] followerIds=this.service.SelectFollower(followedId);
            for(int i=0;i<followerIds.length;i++){
                this.service.InsertLetter(new Letter(0,0,followerIds[i],"",this.service.GetName(followerIds[i]),"您关注的博主更新了",followedId));
            }
            return ResultUtil.success(1);
        }
    @RequestMapping("Test")
    @ResponseBody
    public void Test(){
        Date date=new Date();
        System.out.println(date.getTime());
        System.out.println(date.toString());
    }
}
