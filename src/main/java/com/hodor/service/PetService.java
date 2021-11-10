package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.Pet;
import com.hodor.vo.pet.PetAddVO;
import com.hodor.vo.user.*;

import java.util.Map;

public interface PetService {

    JsonResult<Map<String, Object>> getPetListByQuery(String query, Integer pageno, Integer pagesize);

    JsonResult<PetAddVO> addPet(Pet pet);

    JsonResult<Pet> getPetById(Long id);

    JsonResult<Pet> updatePet(Long id, Pet pet);

    JsonResult deleteById(Long id);
}

