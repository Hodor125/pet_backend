package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dto.UserAddDTO;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.Pet;
import com.hodor.service.PetService;
import com.hodor.vo.pet.PetAddVO;
import com.hodor.vo.pet.PetUpdateVO;
import com.hodor.vo.user.UserAddVO;
import com.hodor.vo.user.UserListVO;
import com.hodor.vo.user.UserUpdateStateVO;
import com.hodor.vo.user.UserUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
@RestController
@CrossOrigin
public class PetController {
    @Autowired
    private PetService petService;

    /**
     * 获取宠物列表
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("/user/pets")
    public JsonResult<Map<String, Object>> getUserListByQuery(@RequestParam(required = false) String query,
                                                              @RequestParam(required = false, defaultValue = "1") Integer pagenum,
                                                              @RequestParam(required = false, defaultValue = "10") Integer pagesize) {
        try {
            JsonResult<Map<String, Object>> petListByQuery = petService.getPetListByQuery(query, pagenum, pagesize);
            return petListByQuery;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }


    @GetMapping("/user/pets/{kind}/{age}/{weight}")
    public JsonResult<Map<String, Object>> getUserListByQueryV2(@PathVariable String kind,
                                                                @PathVariable String age,
                                                                @PathVariable String weight) {
        try {
            JsonResult<Map<String, Object>> petListByQuery = petService.getPetListByQueryV2(kind, age, weight);
            return petListByQuery;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 添加宠物
     * @param pet
     * @return
     */
    @PostMapping("/user/pets")
    public JsonResult<PetAddVO> addUser(@RequestBody Pet pet) {
        try {
            JsonResult<PetAddVO> petAddVOJsonResult = petService.addPet(pet);
            return petAddVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<PetAddVO>().setMeta(new Meta("添加失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id查询宠物
     * @param id
     * @return
     */
    @GetMapping("/user/pets/{id}")
    public JsonResult<Pet> getPetByQuery(@PathVariable Long id) {
        try {
            JsonResult<Pet> petById = petService.getPetById(id);
            return petById;
        } catch (Exception e) {
            return new JsonResult<Pet>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 更新宠物
     * @param id
     * @param pet
     * @return
     */
    @PutMapping("/user/pets/{id}")
    public JsonResult<Pet> updateUserById(@PathVariable Long id, @RequestBody Pet pet) {
        try {
            JsonResult<Pet> petJsonResult = petService.updatePet(id, pet);
            return petJsonResult;
        } catch (Exception e) {
            return new JsonResult<PetUpdateVO>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id删除宠物
     * @param id
     * @return
     */
    @DeleteMapping("/user/pets/{id}")
    public JsonResult deleteById(@PathVariable Long id) {
        try {
            JsonResult jsonResult = petService.deleteById(id);
            return jsonResult;
        } catch (Exception e) {
            return new JsonResult<Pet>().setMeta(new Meta("删除失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }
}
