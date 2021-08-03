package com.gitee.freakchicken.demo.plugin;

import com.alibaba.fastjson.JSONObject;
import com.gitee.freakchicken.dbapi.plugin.PluginConf;
import com.gitee.freakchicken.dbapi.plugin.TransformPlugin;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

/**
 * md5字段加密插件
 */
public class EncryptTransformerPlugin extends TransformPlugin {
    @Override
    public Object transform(List<JSONObject> data) {
        String[] columns = PluginConf.getKey("EncryptTransformer.columns").split(";");

        data.stream().forEach(t -> {
            for (String column : columns) {
                t.put(column, DigestUtils.md5Hex(t.getString(column)));
            }
        });
        return data;

    }
}
