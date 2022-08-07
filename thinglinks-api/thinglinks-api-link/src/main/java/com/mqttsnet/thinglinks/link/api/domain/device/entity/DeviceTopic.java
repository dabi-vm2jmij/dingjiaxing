package com.mqttsnet.thinglinks.link.api.domain.device.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
* @Description: java类作用描述
* @Author: ShiHuan SUN
* @E-mail: 13733918655@163.com
* @Website: http://thinglinks.mqttsnet.com
* @CreateDate: 2022/6/15$ 15:23$
* @UpdateUser: ShiHuan SUN
* @UpdateDate: 2022/6/15$ 15:23$
* @UpdateRemark: 修改内容
* @Version: V1.0
*/
@ApiModel(value="设备Topic数据表")
@Data
public class DeviceTopic implements Serializable {
    /**
    * id
    */
    @ApiModelProperty(value="id")
    private Long id;

    /**
    * 设备标识
    */
    @ApiModelProperty(value="设备标识")
    private String device_identification;

    /**
    * 类型(0:基础Topic,1:自定义Topic)
    */
    @ApiModelProperty(value="类型(0:基础Topic,1:自定义Topic)")
    private String type;

    /**
    * topic
    */
    @ApiModelProperty(value="topic")
    private String topic;

    /**
    * 发布者
    */
    @ApiModelProperty(value="发布者")
    private String publisher;

    /**
    * 订阅者
    */
    @ApiModelProperty(value="订阅者")
    private String subscriber;

    /**
    * 创建者
    */
    @ApiModelProperty(value="创建者")
    private String create_by;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime create_time;

    /**
    * 更新者
    */
    @ApiModelProperty(value="更新者")
    private String update_by;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime update_time;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}