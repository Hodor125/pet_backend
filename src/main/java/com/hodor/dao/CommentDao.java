package com.hodor.dao;

import com.hodor.pojo.Activity;
import com.hodor.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface CommentDao {

    List<Comment> getCommentListByQuery(@Param("query") String query, Long userId);

    Integer insertComment(Comment comment);

    Integer deleteComment(Long id);

    Integer updateComment(Comment comment);
}
