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

    @Override
    public int AddUser(String name, String phone, String password) {
        return this.userDao.AddUser(name, phone, password);
    }

    @Override
    public int HasPhone(String phone) {
        return this.userDao.HasPhone(phone);
    }

    @Override
    public int SearchId(String phone) {
        return this.userDao.SearchId(phone);
    }

    @Override
    public String SearchPassword(String phone) {
        return this.userDao.SearchPassword(phone);
    }

    @Override
    public User GetUser(String phone, String password) {
        return this.userDao.GetUser(phone, password);
    }

    @Override
    public String GetName(int id) {
        return this.userDao.GetName(id);
    }

    @Override
    public String GetAvatar(int id) {
        return this.userDao.GetAvatar(id);
    }

    @Override
    public int ChangeName(int id, String name) {
        return this.userDao.ChangeName(id, name);
    }

    @Override
    public int ChangePassword(int id, String password) {
        return this.userDao.ChangePassword(id, password);
    }

    @Override
    public int ChangeAvatar(int id, String avatar) {
        return this.userDao.ChangeAvatar(id, avatar);
    }

    @Override
    public List<User> SelectAllUsers() {
        return this.userDao.SelectAllUsers();
    }

    @Override
    public int AddBlog(Blog blog) {
        return this.userDao.AddBlog(blog);
    }

    @Override
    public List<Blog> GetAllBlogs() {
        return this.userDao.GetAllBlogs();
    }

    @Override
    public Blog GetBlog(int blogId) {
        return this.userDao.GetBlog(blogId);
    }

    @Override
    public List<Blog> GetOwnerBlogs(int authorId) {
        return this.userDao.GetOwnerBlogs(authorId);
    }

    @Override
    public List<Blog> GetOwnerTypeBlogs(int authorId, String type) {return this.userDao.GetOwnerTypeBlogs(authorId, type);}

    @Override
    public List<Blog> GetTypeBlogs(String type) {
        return this.userDao.GetTypeBlogs(type);
    }

    @Override
    public List<Blog> SelectIntBlogs(int search){return this.userDao.SelectIntBlogs(search);}

    @Override
    public List<Blog> SelectSimilarBlogs(String search){ return this.userDao.SelectSimilarBlogs(search);}

    @Override
    public int FixBlog(Blog blog) {
        return this.userDao.FixBlog(blog);
    }

    @Override
    public int AddComment(Comment comment) {
        return this.userDao.AddComment(comment);
    }

    @Override
    public int AddChildComment(Comment comment) {this.userDao.AddKids(comment.getParentId());return this.userDao.AddChildComment(comment);}

    @Override
    public List<Comment> GetComment(int blogId) {
        return this.userDao.GetComment(blogId);
    }
    @Override
    public Comment GetCommentByCmtId(int cmtId) {
        return this.userDao.GetCommentByCmtId(cmtId);
    }

    @Override
    public List<Comment> GetChildComment(int parentId) {
        return this.userDao.GetChildComment(parentId);
    }

    @Override
    public int HasBlogLike(BlogLike blogLike) {
        return this.userDao.HasBlogLike(blogLike);
    }

    @Override
    public int AddBlogLike(int blogId) {return this.userDao.AddBlogLike(blogId);}

    @Override
    public int ReduceBlogLike(int blogId) {return 0;}
    @Override
    public int DeleteBlog(int blogId) {return this.userDao.DeleteBlog(blogId);}

    @Override
    public int DisLikeComment(CommentLike commentLike) {return userDao.DislikeComment(commentLike);}

    @Override
    public int LikeComment(CommentLike commentLike) {return userDao.LikeComment(commentLike);}

    @Override
    public int LikeBlog(BlogLike blogLike) {return this.userDao.LikeBlog(blogLike);}
    @Override
    public int DislikeBlog(BlogLike blogLike) {return this.userDao.DislikeBlog(blogLike);}
    @Override
    public int HasCommentLike(int likerId,int cmtId) {
        return this.userDao.HasCommentLike(likerId,cmtId);
    }
    @Override
    public int AddCommentLike(CommentLike commentLike) {return this.userDao.CommentLikeAdd(commentLike.getCmtId());}
    @Override
    public int ReduceCommentLike(CommentLike commentLike) {return this.userDao.CommentLikeReduce(commentLike.getCmtId());}
    @Override
    public int AddWaitKey(String phone) {
        return this.userDao.AddWaitKey(phone);
    }
    @Override
    public int SearchWaitKey(String phone) {
        return this.userDao.SearchWaitKey(phone);
    }
    @Override
    public int InsertKind(String kind) {
        return this.userDao.InsertKind(kind);
    }
    @Override
    public int ChangeBlogsName(String authorName, int authorId) {return this.userDao.ChangeBlogsName(authorName, authorId);}
    @Override
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

    public List<Letter> SelectLetters(int id1, int id2) {
        return this.userDao.SelectLetters(id1, id2);
    }
    public List<Letter> SelectAllLetters(int towardId){return this.userDao.SelectAllLetters(towardId);}

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

    public int InsertFollow(int followerId,int followedId){return  this.userDao.InsertFollow(followerId,followedId);}

    public int[] SelectFollowed(int followerId){return this.userDao.SelectFollowed(followerId);}

    public int[] SelectFollower(int followedId){return this.userDao.SelectFollower(followedId);}

}