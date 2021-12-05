package com.hodor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hodor.constants.Constans;
import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.PetDao;
import com.hodor.dao.UserDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.Pet;
import com.hodor.pojo.User;
import com.hodor.service.PetService;
import com.hodor.service.UploadService;
import com.hodor.service.UserService;
import com.hodor.util.IdCardUtils;
import com.hodor.util.StringUtil;
import com.hodor.vo.pet.PetAddVO;
import com.hodor.vo.pet.PetUpdateVO;
import com.hodor.vo.user.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户信息相关
 * @Author limingli006
 * @Date 2021/10/17
 */
@Service
@Slf4j
public class PetServiceImpl implements PetService {

    @Autowired
    private PetDao petMapper;
    @Autowired
    private UploadService uploadService;

    private static Integer MAX_POOL_SIZE = 10;

    /**
     * 线程池
     */
    static final ThreadPoolExecutor DELETE_IMG_THREAD_POOL = new ThreadPoolExecutor(10, MAX_POOL_SIZE,
            60 * 5L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(MAX_POOL_SIZE) {
                @Override
                public boolean offer(Runnable runnable) {
                    try {
                        super.put(runnable);
                    } catch (Exception e) {
                        log.error("计算线程池put()方法异常-error", e);
                    }
                    return true;
                }
            }
            , new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * 宠物列表
     * @param query 搜索词
     * @param pageno 页码
     * @param pagesize 页大小
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getPetListByQuery(String query, Integer pageno, Integer pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("pets", new ArrayList<>());
            return new JsonResult<List<Pet>>().setMeta(new Meta("获取失败", 500L)).setData(map);
        }
        PageHelper.startPage(pageno, pagesize);
        List<Pet> petListByQueryLimit = petMapper.getPetListByQueryLimit(query);
        PageInfo pageRes = new PageInfo(petListByQueryLimit);

        Meta meta = new Meta("获取成功", 200L);

        petListByQueryLimit.forEach(pet -> {
            if(StringUtils.isNotBlank(pet.getImg())) {
                pet.setImg(uploadService.getPrivateFile(pet.getImg()));
            }
        });

        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("pets", petListByQueryLimit);
        return new JsonResult<List<Pet>>().setMeta(meta).setData(map);
    }

    /**
     * 宠物列表按分类展示
     * @param query 搜索词
     * @param ages 年龄范围
     * @param weights 重量单位
     * @param breed 品种
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getPetListByQueryV2(String query, String ages, String weights, String breed) {
        Map<String, Object> map = new HashMap<>();
        String[] ageList = ages.split("-");
        if(ageList.length < 2)
            throw new PetBackendException("年龄参数不正确");
        String[] weightList = weights.split("-");
        if(weightList.length < 2)
            throw new PetBackendException("体重参数不正确");
        if(Constans.DOG.equals(query))
            query = Constans.DOG_ZH;
        else if(Constans.CAT.equals(query))
            query = Constans.CAT_ZH;
        else
            query = null;
        List<Pet> petListByQueryV2 = petMapper.getPetListByQueryV2(query, Integer.parseInt(ageList[0]), Integer.parseInt(ageList[1]),
                Integer.parseInt(weightList[0]), Integer.parseInt(weightList[1]), breed);
        petListByQueryV2.forEach(pet -> {
            if(StringUtils.isNotBlank(pet.getImg())) {
                pet.setImg(uploadService.getPrivateFile(pet.getImg()));
            }
        });
        map.put("total", petListByQueryV2.size());
        map.put("pets", petListByQueryV2);

        Meta meta = new Meta("获取成功", 200L);
        return new JsonResult<List<Pet>>().setMeta(meta).setData(map);
    }

    /**
     * 添加宠物
     * @param pet 宠物信息
     * @return
     */
    @Override
    public JsonResult<PetAddVO> addPet(Pet pet) {
        validAddPet(pet);
        petMapper.addPet(pet);
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("添加成功", 201L))
                .setData(new PetAddVO(pet.getId(), pet.getName(), pet.getImg()));
    }

    /**
     * 根据id查询
     * @param id 宠物id
     * @return
     */
    @Override
    public JsonResult<Pet> getPetById(Long id) {
        Pet petById = petMapper.getPetById(id);
        if(petById == null) {
            throw new PetBackendException("宠物不存在");
        }
        if(StringUtils.isNotBlank(petById.getImg())) {
            petById.setImg(uploadService.getPrivateFile(petById.getImg()));
        }
        Meta meta = new Meta("获取成功", 200L);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(petById);
    }

    /**
     * 更新宠物
     * @param id 宠物id
     * @param pet 宠物信息
     * @return
     */
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
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 201L))
                .setData(new PetUpdateVO(petById.getId(), petById.getState()));
    }

    /**
     * 删除宠物
     * @param id 宠物id
     * @return
     */
    @Override
    public JsonResult deleteById(Long id) {
        Integer integer = petMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 204L)).setData(null);
    }

    /**
     * 上传图片
     * @param id 宠物id
     * @param file 文件流
     * @return
     */
    @Override
    public String uploadImg(Long id, MultipartFile file) {
        Pet petById = petMapper.getPetById(id);
        if(Objects.isNull(petById)) {
            throw new PetBackendException("该宠物不存在");
        }
        //上传得到fileKey
        String fileKey = uploadService.uploadImg(file);
        String imgUrl = uploadService.getPrivateFile(fileKey);
        if(StringUtils.isNotBlank(fileKey)) {
            Pet pet = new Pet();
            pet.setId(id);
            pet.setImg(fileKey);
            petMapper.updatePet(pet);
        }
        if(StringUtils.isNoneBlank(petById.getImg())) {
            deleteImgAsync(petById.getImg());
        }
        return imgUrl;
    }

    /**
     * 异步删除图片
     */
    private void deleteImgAsync(String fileKey) {
        DELETE_IMG_THREAD_POOL.submit(() -> deleteImg(fileKey));
    }

    private void deleteImg(String fileKey) {
        uploadService.removeFile(fileKey);
    }

    /**
     * 获取所有品种
     * @return
     */
    @Override
    public List<String> getAllBreeds() {
        List<Pet> petListByQueryLimit = petMapper.getPetListByQueryLimit(null);
        List<String> breeds = petListByQueryLimit.stream().map(Pet::getKind).distinct().collect(Collectors.toList());
        return breeds;
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
