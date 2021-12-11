package com.hodor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 更新预约信息
 * @Author limingli006
 * @Date 2021/12/11
 */
@Data
@AllArgsConstructor
@Builder
public class ReserveUpdateDTO {

    private Long id;

    private Integer state;
}
