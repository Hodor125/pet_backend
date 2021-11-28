package com.hodor.pojo;

import com.hodor.vo.user.UserListVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/11/28
 */
@Data
public class ComplexPerson extends UserListVO {

    private List<SimpleActivity> activityList;

    private List<SimplePet> petList;
}
