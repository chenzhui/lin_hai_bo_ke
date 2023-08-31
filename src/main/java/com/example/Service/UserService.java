package com.example.Service;

import com.example.Entity.Blog;
import com.example.Entity.BlogLike;
import com.example.Entity.Collection;
import com.example.Entity.Comment;
import com.example.Entity.CommentLike;
import com.example.Entity.Letter;
import com.example.Entity.Type;
import com.example.Entity.User;
import java.util.List;

public interface UserService {
    int AddUser(String name, String phone, String password);

    int HasPhone(String phone);

    int SearchId(String phone);

    String SearchPasswordById(int id);

    String SearchPassword(String phone);

    User GetUser(String phone, String password);

    String GetName(int id);

    String GetAvatar(int id);

    int ChangeAvatar(int id, String avatar);

    int ChangePassword(int id, String password);

    int ChangeName(int id, String name);

    List<User> SelectAllUsers();

    int AddBlog(Blog Blog);

    List<Blog> GetAllBlogs();

    Blog GetBlog(int blogId);

    List<Blog> GetOwnerBlogs(int authorId);

    List<Blog> GetOwnerTypeBlogs(int authorId, String type);

    List<Blog> GetTypeBlogs(String type);

    List<Blog> SelectSimilarBlogs(String search);

    int SelectAuthorById(int blogId);

    int FixBlog(Blog blog);

    int ChangeBlogsName(String authorName, int authorId);

    int DeleteBlog(int blogId);

    int AddComment(Comment comment);

    int AddChildComment(Comment comment);

    List<Comment> GetComment(int blogId);

    List<Comment> GetChildComment(int parentId);

    Comment GetCommentByCmtId(int cmtId);

    int HasBlogLike(BlogLike blogLike);

    int AddBlogLike(BlogLike blogLike);

    int ReduceBlogLike(BlogLike blogLike);

    int HasCommentLike(int id,int cmtId);

    int AddCommentLike(CommentLike commentLike);

    int ReduceCommentLike(CommentLike commentLike);

    int AddWaitKey(String phone);

    int SearchWaitKey(String phone);

    int InsertKind(String kind);

    List<Type> SelectKinds();

    int DeleteKind(String kind);

    int InsertLetter(Letter letter);

    List<Letter> SelectLetters(int small, int bigId);

    int InsertCollection(Collection collection);

    int DeleteCollection(Collection collection);

    int SelectCollection(Collection collection);

    int[] SelectCollectionsById(int authorId);
}
