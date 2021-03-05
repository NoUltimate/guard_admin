package org.jeecg.guardian.utils;
/*
 *
 *
 * @author sunbowen
 * @date 2021年03月03日 13:40
 */

import com.alibaba.fastjson.JSONObject;
import org.xmind.core.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class XmindUtil {

    private static IWorkbookBuilder builder = null;
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException, CoreException {
        String xmindPath = "D://opt//upFiles/test.xmind";
        List<String> lists = xmindToList(xmindPath);

        for(String list:lists){
            System.out.println(list);
        }
    }
    /**
     * 获取工作簿
     * IWorkbook：表示整个思维导图
     * @param xmindPath:xmind文件路径
     */
    public static IWorkbook getIWorkbook(String xmindPath) throws IOException, CoreException {
        if (builder == null){
            builder = Core.getWorkbookBuilder();// 初始化builder
        }
        FileInputStream fileInputStream=new FileInputStream(new File(xmindPath));
        return builder.loadFromStream(fileInputStream, ".");
    }

    /**
     * 获取根节点
     * @param  iWorkbook:加载的思维导图
     */
    public static ITopic getRootTopic(IWorkbook iWorkbook){
        return iWorkbook.getPrimarySheet().getRootTopic();
    }

    /**
     * 获取根节点
     * @param  iWorkbook:加载的思维导图
     */
    public static void getRelationship(IWorkbook iWorkbook){
        Set<IRelationship> relationshipSet = iWorkbook.getPrimarySheet().getRelationships();
        for(IRelationship relationship : relationshipSet) {
            System.out.println(JSONObject.toJSONString(relationship.getEnd1().getId()));
            System.out.println(JSONObject.toJSONString(relationship.getEnd2().getId()));
        }
    }

    /**
     * 获取从根目录到每一个叶子节点的的路径
     */
    public static List<String> getAllPath(ITopic rootTopic){
        return getAllPathIter(rootTopic.getTitleText(),rootTopic.getAllChildren());
    }

    public static List<String> getAllPathIter(String parentContext,List<ITopic> childrens){
        for(ITopic children:childrens){
            if(children.getAllChildren().size() == 0){
                list.add(parentContext+" —— "+ children.getId() + " " + children.getTitleText());
            }else {
                getAllPathIter(parentContext+" —— " + children.getId() + " " + children.getTitleText(), children.getAllChildren());
            }
        }
        return list;
    }

    /**
     * 解析Xmind文件
     */
    public static List<String> xmindToList(String xmindPath) throws IOException, CoreException {
        getRelationship(getIWorkbook(xmindPath));
        return getAllPath(getRootTopic(getIWorkbook(xmindPath)));
    }


}
