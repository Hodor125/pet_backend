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

    List<Pet> getPetListByQueryLimit(@Param("query") String query);

    List<Pet> getPetListByQuery(@Param("query") String query);

    List<Pet> getPetListByQueryV2(@Param("query") String kind,
                                  @Param("ageStart") Integer ageStart,
                                  @Param("ageEnd")Integer ageEnd,
                                  @Param("weightStart")Integer weightStart,
                                  @Param("weightEnd")Integer weightEnd);

    Integer addPet(Pet pet);

    Integer updatePet(Pet pet);

    Pet getPetById(@Param("id") Long id);

    Integer deleteById(Long id);
}
