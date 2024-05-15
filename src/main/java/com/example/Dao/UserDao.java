package com.example.Dao;

import com.example.Entity.Blog;
import com.example.Entity.BlogLike;
import com.example.Entity.Collection;
import com.example.Entity.Comment;
import com.example.Entity.CommentLike;
import com.example.Entity.Letter;
import com.example.Entity.Type;
import com.example.Entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int AddUser(String name, String phone, String password);

    int HasPhone(String phone);

    String SearchPasswordById(int id);
    int SearchId(String phone);

    String SearchPassword(String phone);

    User GetUser(String phone, String password);

    String GetName(int phone);

    String GetAvatar(int id);

    int ChangeAvatar(int id, String avatar);

    int ChangePassword(int id, String password);

    int ChangeName(int id, String name);

    List<User> SelectAllUsers();

    int AddBlog(Blog blog);

    List<Blog> GetAllBlogs();

    Blog GetBlog(int blogId);

    List<Blog> GetOwnerBlogs(int authorId);

    List<Blog> GetOwnerTypeBlogs(int authorId, String type);

    List<Blog> GetTypeBlogs(String type);

    List<Blog> SelectSimilarBlogs(String search);

    List<Blog> SelectIntBlogs(int search);

    int SelectAuthorById(int blogId);

    int FixBlog(Blog blog);

    int ChangeBlogsName(String authorName, int authorId);

    int DeleteBlog(int blogId);

    int AddComment(Comment Comment);

    int AddChildComment(Comment comment);

    int AddKids(int parentId);

    List<Comment> GetComment(int blogId);

    List<Comment> GetChildComment(int parentId);

    Comment GetCommentByCmtId(int cmtId);

    int HasBlogLike(BlogLike blogLike);

    int LikeBlog(BlogLike blogLike);

    int DislikeBlog(BlogLike blogLike);

    int AddBlogLike(int blogId);

    int BlogLikeReduce(int blogId);

    int HasCommentLike(int likerId,int cmtId);

    int LikeComment(CommentLike commentLike);

    int DislikeComment(CommentLike commentLike);

    int CommentLikeAdd(int cmtId);

    int CommentLikeReduce(int cmtId);

    int AddWaitKey(String phone);

    int SearchWaitKey(String phone);

    int InsertKind(String kind);

    List<Type> SelectKinds();

    int DeleteKind(String kind);

    int InsertLetter(Letter letter);

    List<Letter> SelectLetters(int id1, int id2);
    List<Letter> SelectAllLetters(int towardId);
    int InsertCollection(Collection collection);
    int DeleteCollection(Collection collection);

    int SelectCollection(Collection collection);

    int[] SelectCollectionsById(int authorId);

    int InsertFollow(int followerId,int followedId);

    int[] SelectFollowed(int followerId);
    int[] SelectFollower(int followedId);

}
