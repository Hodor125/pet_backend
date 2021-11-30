package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    JsonResult<Map<String, Object>> getCommentByQuery(String query, Integer pageno, Integer pagesize);

    JsonResult<Comment> addComment(Comment comment);

    JsonResult deleteComment(Long id);

    JsonResult<Comment> updateComment(Comment comment);
}
