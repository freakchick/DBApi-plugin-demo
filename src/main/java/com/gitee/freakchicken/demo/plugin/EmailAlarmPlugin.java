package com.gitee.freakchicken.demo.plugin;

import com.gitee.freakchicken.dbapi.common.ApiConfig;
import com.gitee.freakchicken.dbapi.plugin.AlarmPlugin;

import javax.servlet.http.HttpServletRequest;

public class EmailAlarmPlugin extends AlarmPlugin {
    /**
     * 插件初始化方法，实例化插件的时候执行，永远只会执行一次
     */
    @Override
    public void init() {

    }

    /**
     * 告警逻辑
     *
     * @param e           异常
     * @param config      API元数据
     * @param request     请求
     * @param pluginParam 告警插件局部参数
     */
    @Override
    public void alarm(Exception e, ApiConfig config, HttpServletRequest request, String pluginParam) {

    }

    /**
     * 插件名称，用于在页面上显示，提示用户
     *
     * @return
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * 插件功能描述，用于在页面上显示，提示用户
     *
     * @return
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * 插件参数描述，用于在页面上显示，提示用户
     *
     * @return
     */
    @Override
    public String getParamDescription() {
        return null;
    }
}
