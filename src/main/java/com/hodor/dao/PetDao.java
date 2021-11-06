package com.hodor.dao;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.Pet;
import com.hodor.pojo.User;
import com.hodor.vo.user.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
@Mapper
public interface PetDao {

    List<Pet> getPetListByQueryLimit(@Param("query") String query,
                                     @Param("start") Long pageno,
                                     @Param("size") Long pagesize);

    List<Pet> getPetListByQuery(@Param("query") String query);

    Integer addPet(Pet pet);

    Integer updatePet(Pet pet);

    Pet getPetById(@Param("id") Long id);

    Integer deleteById(Long id);
}
