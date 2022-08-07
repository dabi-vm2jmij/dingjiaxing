package com.mqttsnet.thinglinks.tdengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mqttsnet.thinglinks.common.core.constant.Constants;
import com.mqttsnet.thinglinks.common.core.domain.R;
import com.mqttsnet.thinglinks.common.core.enums.DataTypeEnum;
import com.mqttsnet.thinglinks.common.core.utils.StringUtils;
import com.mqttsnet.thinglinks.common.redis.service.RedisService;
import com.mqttsnet.thinglinks.tdengine.api.domain.*;
import com.mqttsnet.thinglinks.tdengine.mapper.TdEngineMapper;
import com.mqttsnet.thinglinks.tdengine.service.TdEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassDescription: TdEngine业务层的实现层
 * @ClassName: TdEngineServiceImpl
 * @Author: thinglinks
 * @Date: 2021-12-27 13:55:49
 * @Version 1.0
 */
@Service
@Slf4j
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TdEngineServiceImpl implements TdEngineService {

    @Autowired
    private TdEngineMapper tdEngineMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public void createDateBase(String dataBaseName) {
        this.tdEngineMapper.createDatabase(dataBaseName);
    }

    @Override
    public void createSuperTable(List<FieldsVo> schemaFields, List<FieldsVo> tagsFields, String dataBaseName, String superTableName) {
        this.tdEngineMapper.createSuperTable(schemaFields, tagsFields, dataBaseName, superTableName);
    }

    @Override
    public void createTable(TableDto tableDto) {
        this.tdEngineMapper.createTable(tableDto);
    }

    @Override
    public void insertData(TableDto tableDto) {
        this.tdEngineMapper.insertData(tableDto);
    }

    @Override
    public List<Map<String, Object>> selectByTimesTamp(SelectDto selectDto) {
        List<Map<String, Object>> maps = this.tdEngineMapper.selectByTimestamp(selectDto);
        for (Map<String, Object> map : maps) {
            Map<String, Object> filterMap = map.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() != null)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return maps;
    }

    @Override
    public void addColumnForSuperTable(String superTableName, FieldsVo fieldsVo) {
        this.tdEngineMapper.addColumnForSuperTable(superTableName, fieldsVo);
    }

    @Override
    public Long getCountByTimesTamp(SelectDto selectDto) {
        Map<String, Long> countMap = this.tdEngineMapper.getCountByTimestamp(selectDto);
        if (countMap == null) {
            return 0L;
        }
        Long count = countMap.get("count");
        return count;
    }

    /**
     * 检查数据库表是否存在
     * @param dataBaseName
     * @param tableName tableName 可以为超级表名或普通表名
     * @return
     */
    public boolean checkTableExists(String dataBaseName,String tableName) {
        try {
            Integer count = tdEngineMapper.checkTableExists(dataBaseName, tableName);
            return count == 1;
        } catch (Exception e) {
            log.error("数据库表不存在");
            return false;
        }
    }

    @Override
    public void initSTableFrame(String msg) throws Exception {
        final SuperTableDto superTableDto = JSONObject.toJavaObject(JSONObject.parseObject(msg), SuperTableDto.class);
        //从入参对象获取列字段（超级表结构）对象集合
        List<Fields> schemaFields = superTableDto.getSchemaFields();
        //从入参对象获取标签字段对象集合
        List<Fields> tagsFields = superTableDto.getTagsFields();
        //从入参获取数据库名称
        String dataBaseName = superTableDto.getDataBaseName();
        //从入参获取超级表名称
        String superTableName = superTableDto.getSuperTableName();
        final boolean tableExists = this.checkTableExists(dataBaseName, superTableName);
        if(tableExists){
            log.info("超级表{}已存在",superTableName);
            return;
        }
        //获取列字段对象集合的第一个对象的字段数据类型
        DataTypeEnum dataType = schemaFields.get(0).getDataType();
        //如果该数据类型不是时间戳，打印和返回报错信息
        if (dataType == null || !"timestamp".equals(dataType.getDataType())) {
            log.error("invalid operation: first column must be timestamp");
            return;
        }
        //将列字段对象集合和标签字段对象集合转码为字段Vo类对象集合
        List<FieldsVo> schemaFieldsVoList = FieldsVo.fieldsTranscoding(schemaFields);
        List<FieldsVo> tagsFieldsVoList = FieldsVo.fieldsTranscoding(tagsFields);
        //创建超级表
        this.createSuperTable(schemaFieldsVoList, tagsFieldsVoList, dataBaseName, superTableName);
        log.info("create {} super table success", superTableName);
    }


}
