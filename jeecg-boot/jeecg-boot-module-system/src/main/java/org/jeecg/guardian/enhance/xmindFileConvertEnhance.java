package org.jeecg.guardian.enhance;
/*
 *
 *
 * @author sunbowen
 * @date 2021年03月04日 10:15
 */

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.codec.Base64;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.guardian.utils.XmindUtil;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component("xmindFileConverterEnhance")
public class xmindFileConvertEnhance implements CgformEnhanceJavaInter {

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    @SneakyThrows
    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {
        System.out.println("-------------自定义增强1");
        String fileName = new String(Base64.decode(jsonObject.getString("xmind")));
        fileName = fileName.replace("https://guardian-star.oss-cn-hangzhou.aliyuncs.com/", "");
        InputStream inputStream = OssBootUtil.getOssFile(fileName, "guardian-star");
        File file = new File(uploadpath + "/test.xmind");
        FileUtils.copyInputStreamToFile(inputStream, file);
        List<String> lists = XmindUtil.xmindToList(uploadpath + "/test.xmind");
        for(String list:lists){
            System.out.println(list);
        }
        return 0;
    }

    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        System.out.println("-------------自定义增强2");
        System.out.println(s);
        System.out.println(JSONObject.toJSONString(map));
        return 0;
    }
}
