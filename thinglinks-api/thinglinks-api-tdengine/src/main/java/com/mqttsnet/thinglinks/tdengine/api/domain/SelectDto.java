package com.mqttsnet.thinglinks.tdengine.api.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassDescription: 查询所需入参对象
 * @ClassName: SelectDto
 * @Author: thinglinks
 * @Date: 2022-01-07 14:12:26
 * @Version 1.0
 */
@Data
public class SelectDto {

    @NotBlank(message = "invalid operation: tableName can not be empty")
    private String dataBaseName;

    @NotBlank(message = "invalid operation: tableName can not be empty")
    private String tableName;

    @NotBlank(message = "invalid operation: fieldName can not be empty")
    private String fieldName;

    @NotNull(message = "invalid operation: startTime can not be null")
    private Long startTime;

    @NotNull(message = "invalid operation: endTime can not be null")
    private Long endTime;
}
