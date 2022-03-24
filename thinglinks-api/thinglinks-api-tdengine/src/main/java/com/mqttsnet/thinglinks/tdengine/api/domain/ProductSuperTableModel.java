package com.mqttsnet.thinglinks.tdengine.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Optional;

/**
 * @Description: 产品超级表模型
 * @Author: ShiHuan SUN
 * @E-mail: 13733918655@163.com
 * @Website: http://thinglinks.mqttsnet.com
 * @CreateDate: 2022/1/1$ 19:37$
 * @UpdateUser: ShiHuan SUN
 * @UpdateDate: 2022/1/1$ 19:37$
 * @UpdateRemark: 修改内容
 * @Version: V1.0
 */
@Data
public class ProductSuperTableModel {
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Timestamp ts;

    private String superTableName;

    /**
     * columnsName,columnsProperty
     */
    private HashMap<Optional,Optional> columns;

    /**
     * tagsName,tagsProperty
     */
    private HashMap<Optional,Optional> tags;

}
