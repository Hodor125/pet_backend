package com.hodor.service.impl;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.PetDao;
import com.hodor.dao.UserDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.Pet;
import com.hodor.pojo.User;
import com.hodor.service.PetService;
import com.hodor.service.UserService;
import com.hodor.util.IdCardUtils;
import com.hodor.vo.pet.PetAddVO;
import com.hodor.vo.pet.PetUpdateVO;
import com.hodor.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户信息相关
 * @Author limingli006
 * @Date 2021/10/17
 */
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetDao petMapper;


    @Override
    public JsonResult<Map<String, Object>> getPetListByQuery(String query, Long pageno, Long pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("pets", new ArrayList<>());
            return new JsonResult<List<Pet>>().setMeta(new Meta("获取失败", 400L)).setData(map);
        }
        List<Pet> petListByQueryLimit = petMapper.getPetListByQueryLimit(query, (pageno - 1) * pagesize, pagesize);
        List<Pet> petListByQuery = petMapper.getPetListByQuery(query);

        Meta meta = new Meta("获取成功", 200L);

        map.put("total", petListByQuery.size());
        map.put("pagenum", pageno);
        map.put("pets", petListByQueryLimit);
        return new JsonResult<List<Pet>>().setMeta(meta).setData(map);
    }

    @Override
    public JsonResult<PetAddVO> addPet(Pet pet) {
        validAddPet(pet);
        petMapper.addPet(pet);
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("添加成功", 200L))
                .setData(new PetAddVO(pet.getId(), pet.getName(), pet.getImg()));
    }

    @Override
    public JsonResult<Pet> getPetById(Long id) {
        Pet petById = petMapper.getPetById(id);
        if(petById == null) {
            throw new PetBackendException("宠物不存在");
        }
        Meta meta = new Meta("获取成功", 200L);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(petById);
    }

    @Override
    public JsonResult<Pet> updatePet(Long id, Pet pet) {
        if(id == null) {
            throw new PetBackendException("id为空");
        }
        pet.setId(id);
        petMapper.updatePet(pet);
        Pet petById = petMapper.getPetById(id);
        if(petById == null) {
            throw new PetBackendException("宠物不存在");
        }
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 200L))
                .setData(new PetUpdateVO(petById.getId(), petById.getState()));
    }

    @Override
    public JsonResult deleteById(Long id) {
        Integer integer = petMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 200L)).setData(null);
    }

    private String validAddPet(Pet pet) {
        if (pet.getName() == null || "".equals(pet.getName()))
            return "姓名不能为空";
        if(pet.getAge() == null)
            return "年龄不能为空";
        if(pet.getWeight() == null)
            return "体重不能为空";
        if(pet.getKind() == null || "".equals(pet.getKind()))
            return "类型不能为空";
        if(pet.getImg() == null || "".equals(pet.getImg()))
            pet.setImg("");
        if(pet.getImmunity() == null || "".equals(pet.getImmunity()))
            pet.setImmunity("");
        if(pet.getWorm() == null || "".equals(pet.getImmunity()))
            pet.setWorm("");
        if(pet.getHealthy() == null || "".equals(pet.getHealthy()))
            pet.setHealthy("");
        if(pet.getCharacter() == null || "".equals(pet.getHealthy()))
            pet.setCharacter("");
        if(pet.getState() == null)
            pet.setState(0);
        if(pet.getSex() == null || "".equals(pet.getSex()))
            pet.setSex("");
        return "";
    }
}
