package com.example.jeecg_tools.util;

import com.example.jeecg_tools.common.BasePayload;
import com.example.jeecg_tools.exploit.*;
import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;
import java.net.ProxySelector;

public class Tools {
    private static final Map<String, BasePayload>payloadMap =new HashMap<>();
    static {
        payloadMap.put("JEECG 登录绕过",new JEECG_unauthorized());
        payloadMap.put("JEECG icon文件上传",new JEECG_iconUpload());
        payloadMap.put("JEECG jeecgFormDemo文件上传",new JEECG_jeecgFormDemo());
        payloadMap.put("JEECG common文件上传",new JEECG_commonUpload());
        payloadMap.put("JEECG Xstream反序列化",new JEECG_XstreamInject());
    }

    public static BasePayload getPayload(String select){
        return payloadMap.get(select);
    }

}
