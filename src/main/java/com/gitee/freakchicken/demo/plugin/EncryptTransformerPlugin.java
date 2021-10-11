package com.gitee.freakchicken.demo.plugin;

import com.alibaba.fastjson.JSONObject;
import com.gitee.freakchicken.dbapi.common.ApiConfig;
import com.gitee.freakchicken.dbapi.plugin.TransformPlugin;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * md5字段加密插件
 */
public class EncryptTransformerPlugin extends TransformPlugin {

    /**
     * sql查询结果数据转换
     * @param data
     * @param config
     * @return
     */
    @Override
    public Object transform(List<JSONObject> data, ApiConfig config) {
        String params = config.getTransformPluginParams();
        if (StringUtils.isNoneBlank(params)) {
            String[] columns = params.split(";");
            data.stream().forEach(t -> {
                for (String column : columns) {
                    t.put(column, DigestUtils.md5Hex(t.getString(column)));
                }
            });
        }
        return data;

    }
}
