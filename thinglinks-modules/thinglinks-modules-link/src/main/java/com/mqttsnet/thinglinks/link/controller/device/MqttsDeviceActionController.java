package com.mqttsnet.thinglinks.link.controller.device;

import com.mqttsnet.thinglinks.common.core.domain.R;
import com.mqttsnet.thinglinks.common.core.web.controller.BaseController;
import com.mqttsnet.thinglinks.common.core.web.domain.AjaxResult;
import com.mqttsnet.thinglinks.common.core.web.page.TableDataInfo;
import com.mqttsnet.thinglinks.common.log.annotation.Log;
import com.mqttsnet.thinglinks.common.log.enums.BusinessType;
import com.mqttsnet.thinglinks.common.security.annotation.PreAuthorize;
import com.mqttsnet.thinglinks.link.api.domain.device.MqttsDeviceAction;
import com.mqttsnet.thinglinks.link.service.device.MqttsDeviceActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备动作Controller
 *
 * @author shisen
 * @date 2021-11-20
 */
@RestController
@RequestMapping("/device/action")
public class MqttsDeviceActionController extends BaseController {
    @Autowired
    private MqttsDeviceActionService mqttsDeviceActionService;

    /**
     * 查询设备动作列表
     */
    @PreAuthorize(hasPermi = "link:device:action:list")
    @GetMapping("/list")
    public TableDataInfo list(MqttsDeviceAction mqttsDeviceAction) {
        startPage();
        List<MqttsDeviceAction> list = mqttsDeviceActionService.selectMqttsDeviceActionList(mqttsDeviceAction);
        return getDataTable(list);
    }

    /**
     * 新增设备动作
     */
    @Log(title = "设备动作", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public R add(@RequestBody MqttsDeviceAction mqttsDeviceAction) {
        return R.ok(mqttsDeviceActionService.insertOrUpdate(mqttsDeviceAction));
    }

    /**
     * 删除设备动作
     */
    @PreAuthorize(hasPermi = "link:device:action:remove")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mqttsDeviceActionService.deleteMqttsDeviceActionByIds(ids));
    }
}
