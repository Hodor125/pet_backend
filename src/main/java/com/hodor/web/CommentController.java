package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.pojo.Comment;
import com.hodor.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author limingli006
 * @Date 2021/11/28
 */
@RestController
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping ("/user/msgs")
    public JsonResult getCommentByQuery(@RequestParam String query,
                                        @RequestParam(required = false, defaultValue = "1") Integer pagenum,
                                        @RequestParam(required = false, defaultValue = "10") Integer pagesize) {
        try {
            JsonResult<Map<String, Object>> commentByQuery = commentService.getCommentByQuery(query, pagenum, pagesize);
            return commentByQuery;
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<Object>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    @PostMapping("/user/msgs")
    public JsonResult<Comment> addComment(@RequestBody Comment comment) {
        try {
            JsonResult<Comment> commentJsonResult = commentService.addComment(comment);
            return commentJsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<Object>().setMeta(new Meta("添加失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    @DeleteMapping("/user/msgs/{id}")
    public JsonResult deleteComment(@PathVariable Long id) {
        try {
            JsonResult jsonResult = commentService.deleteComment(id);
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<Object>().setMeta(new Meta("删除失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    @PutMapping("/user/msgs")
    public JsonResult updateComment(@RequestBody Comment comment) {
        try {
            JsonResult<Comment> commentJsonResult = commentService.updateComment(comment);
            return commentJsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<Object>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }
}
