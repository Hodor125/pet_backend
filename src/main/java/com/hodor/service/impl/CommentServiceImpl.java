package com.hodor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.CommentDao;
import com.hodor.dao.UserDao;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.Comment;
import com.hodor.pojo.User;
import com.hodor.service.CommentService;
import com.hodor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author limingli006
 * @Date 2021/11/28
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentMapper;
    @Autowired
    private UserDao userMapper;

    /**
     * 评论列表
     * @param query 搜索词
     * @param pageno 页码
     * @param pagesize 页大小
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getCommentByQuery(String query, Integer pageno, Integer pagesize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageno, pagesize);
        Long userId = null;
        if(StringUtils.isNumeric(query)) {
            userId = Long.parseLong(query);
        }
        List<Comment> commentListByQuery = commentMapper.getCommentListByQuery(query, userId);

        PageInfo pageRes = new PageInfo(commentListByQuery);
        Meta meta = new Meta("获取成功", 200L);
//        List<UserListVO> userListVOS = transUserToUserListVO(userListByQueryLimit);
        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("msgs", commentListByQuery);
        return new JsonResult<Map<String, Object>>().setMeta(meta).setData(map);
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @Override
    public JsonResult<Comment> addComment(Comment comment) {
        valideComment(comment);
        Integer integer = commentMapper.insertComment(comment);
        Meta meta = new Meta("添加成功", 201L);
        return new JsonResult<Comment>().setMeta(meta).setData(comment);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @Override
    public JsonResult deleteComment(Long id) {
        Integer integer = commentMapper.deleteComment(id);
        Meta meta = new Meta("删除成功", 204L);
        return new JsonResult<Comment>().setMeta(meta).setData(id);
    }

    /**
     * 更新评论
     * @param comment
     * @return
     */
    @Override
    public JsonResult<Comment> updateComment(Comment comment) {
        if(Objects.isNull(comment.getId())) {
            throw new PetBackendException("留言id为空");
        }
        Integer integer = commentMapper.updateComment(comment);
        Meta meta = new Meta("更新成功", 201L);
        return new JsonResult<Comment>().setMeta(meta).setData(comment);
    }

    private void valideComment(Comment comment) {
        if(Objects.isNull(comment.getContent())) {
            comment.setContent("");
        }
        if(Objects.isNull(comment.getU_id())) {
            throw new PetBackendException("用户id为空");
        }
        if(Objects.isNull(comment.getParentId())) {
            comment.setParentId(0L);
        }
    }
}
