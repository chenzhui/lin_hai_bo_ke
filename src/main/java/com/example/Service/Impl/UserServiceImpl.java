package com.example.Service.Impl;

import com.example.Dao.UserDao;
import com.example.Entity.Blog;
import com.example.Entity.BlogLike;
import com.example.Entity.Collection;
import com.example.Entity.Comment;
import com.example.Entity.CommentLike;
import com.example.Entity.Letter;
import com.example.Entity.Type;
import com.example.Entity.User;
import com.example.Service.UserService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public int AddUser(String name, String phone, String password) {
        return this.userDao.AddUser(name, phone, password);
    }

    public int HasPhone(String phone) {
        return this.userDao.HasPhone(phone);
    }

    public int SearchId(String phone) {
        return this.userDao.SearchId(phone);
    }

    public String SearchPassword(String phone) {
        return this.userDao.SearchPassword(phone);
    }

    public User GetUser(String phone, String password) {
        return this.userDao.GetUser(phone, password);
    }

    public String GetName(int id) {
        return this.userDao.GetName(id);
    }

    public String GetAvatar(int id) {
        return this.userDao.GetAvatar(id);
    }

    public int ChangeName(int id, String name) {
        return this.userDao.ChangeName(id, name);
    }

    public int ChangePassword(int id, String password) {
        return this.userDao.ChangePassword(id, password);
    }

    public int ChangeAvatar(int id, String avatar) {
        return this.userDao.ChangeAvatar(id, avatar);
    }

    public List<User> SelectAllUsers() {
        return this.userDao.SelectAllUsers();
    }

    public int AddBlog(Blog blog) {
        return this.userDao.AddBlog(blog);
    }

    public List<Blog> GetAllBlogs() {
        return this.userDao.GetAllBlogs();
    }

    public Blog GetBlog(int blogId) {
        return this.userDao.GetBlog(blogId);
    }

    public List<Blog> GetOwnerBlogs(int authorId) {
        return this.userDao.GetOwnerBlogs(authorId);
    }

    public List<Blog> GetOwnerTypeBlogs(int authorId, String type) {return this.userDao.GetOwnerTypeBlogs(authorId, type);}

    public List<Blog> GetTypeBlogs(String type) {
        return this.userDao.GetTypeBlogs(type);
    }

    public List<Blog> SelectSimilarBlogs(String search) {
        boolean digit;
        int i;
        for (digit = true, i = 0; i < search.length(); ) {
            if (!Character.isDigit(search.charAt(i)))
                digit = false;
            i++;
        }
        return (digit == true) ? this.userDao.SelectIntBlogs(Integer.parseInt(search)) : this.userDao.SelectSimilarBlogs(search);
    }

    public int FixBlog(Blog blog) {
        return this.userDao.FixBlog(blog);
    }

    public int DeleteBlog(int blogId) {
        return this.userDao.DeleteBlog(blogId);
    }

    public int AddComment(Comment comment) {
        return this.userDao.AddComment(comment);
    }

    public int AddChildComment(Comment comment) {
        this.userDao.AddKids(comment.getParentId());
        return this.userDao.AddChildComment(comment);
    }

    public List<Comment> GetComment(int blogId) {
        return this.userDao.GetComment(blogId);
    }

    @Override
    public Comment GetCommentByCmtId(int cmtId) {
        return this.userDao.GetCommentByCmtId(cmtId);
    }

    public List<Comment> GetChildComment(int parentId) {
        return this.userDao.GetChildComment(parentId);
    }

    public int HasBlogLike(BlogLike blogLike) {
        return this.userDao.HasBlogLike(blogLike);
    }

    public int AddBlogLike(BlogLike blogLike) {
        this.userDao.BlogLikeAdd(blogLike.getBlogId());
        return this.userDao.LikeBlog(blogLike);
    }

    public int ReduceBlogLike(BlogLike blogLike) {
        this.userDao.BlogLikeReduce(blogLike.getBlogId());
        return this.userDao.DislikeBlog(blogLike);
    }

    public int HasCommentLike(int id,int cmtId) {
        return this.userDao.HasCommentLike(id,cmtId);
    }

    public int AddCommentLike(CommentLike commentLike) {
        this.userDao.CommentLikeAdd(commentLike.getCmtId());
        return this.userDao.LikeComment(commentLike);
    }

    public int ReduceCommentLike(CommentLike commentLike) {
        this.userDao.CommentLikeReduce(commentLike.getCmtId());
        return this.userDao.DislikeComment(commentLike);
    }

    public int AddWaitKey(String phone) {
        return this.userDao.AddWaitKey(phone);
    }

    public int SearchWaitKey(String phone) {
        return this.userDao.SearchWaitKey(phone);
    }

    public int InsertKind(String kind) {
        return this.userDao.InsertKind(kind);
    }

    public int ChangeBlogsName(String authorName, int authorId) {
        return this.userDao.ChangeBlogsName(authorName, authorId);
    }

    public List<Type> SelectKinds() {
        return this.userDao.SelectKinds();
    }

    @Override
    public String SearchPasswordById(int id) {return this.userDao.SearchPasswordById(id);}

    public int DeleteKind(String kind) {
        return this.userDao.DeleteKind(kind);
    }

    public int InsertLetter(Letter letter) {
        return this.userDao.InsertLetter(letter);
    }

    public List<Letter> SelectLetters(int smallId, int bigId) {
        return this.userDao.SelectLetters(smallId, bigId);
    }

    public int InsertCollection(Collection collection) {
        return this.userDao.InsertCollection(collection);
    }

    public int DeleteCollection(Collection collection) {
        return this.userDao.DeleteCollection(collection);
    }

    public int SelectCollection(Collection collection) {
        return this.userDao.SelectCollection(collection);
    }

    public int[] SelectCollectionsById(int authorId) {
        return this.userDao.SelectCollectionsById(authorId);
    }

    public int SelectAuthorById(int blogId) {
        return this.userDao.SelectAuthorById(blogId);
    }
}