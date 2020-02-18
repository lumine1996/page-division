package com.cgm.pagesplit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cgm.pagesplit.entity.CellConfig;
import com.cgm.pagesplit.entity.GlobalConfig;
import com.cgm.pagesplit.entity.PageConfig;
import com.cgm.pagesplit.service.IConfigService;
import com.cgm.pagesplit.util.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cgm
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    private static final String USER_CONFIG_PATH = System.getProperty("user.home") + "/PageDivision/";
    private static final String GLOBAL_CONFIG_NAME = "global";
    private static final String GLOBAL_CONFIG_FILE = USER_CONFIG_PATH + "global.json";

    @Override
    public List<String> queryList() {
        String[] fileArray = new File(USER_CONFIG_PATH).list();
        List<String> customList = new ArrayList<>();
        if (fileArray == null) {
            return customList;
        }
        for (String fileName : fileArray) {
            if (fileName.endsWith(".json")) {
                customList.add(fileName.replace(".json", ""));
            }
        }
        customList.remove(GLOBAL_CONFIG_NAME);
        Collections.sort(customList);
        return customList;
    }

    @Override
    public PageConfig queryCurrent() {
        String json = JsonUtils.readJsonFile(GLOBAL_CONFIG_FILE);
        GlobalConfig globalConfig = JSON.parseObject(json, GlobalConfig.class);
        if (globalConfig == null || StringUtils.isEmpty(globalConfig.getCurrentConfig())) {
            return new PageConfig();
        }
        return queryByName(globalConfig.getCurrentConfig());
    }

    @Override
    public PageConfig queryByName(String name) {
        String json = JsonUtils.readJsonFile(USER_CONFIG_PATH + name);
        PageConfig pageConfig = JSON.parseObject(json, PageConfig.class);
        if (pageConfig == null) {
            return new PageConfig();
        }

        return pageConfig;
    }

    @Override
    public String addConfig(PageConfig pageConfig) {
        List<String> configList = queryList();
        if (configList.contains(pageConfig.getConfigName())) {
            return "EXISTED";
        }

        // 新增文件
        String jsonStr = JSONObject.toJSONString(pageConfig);
        JsonUtils.writeJsonFile(USER_CONFIG_PATH + pageConfig.getConfigName(), jsonStr);
        return "SUCCESS";
    }

    @Override
    public String updateConfig(String name, PageConfig pageConfig) {
        List<String> configList = queryList();
        if (!configList.contains(pageConfig.getConfigName())) {
            return "NOT EXISTED";
        }

        // 覆盖文件
        String jsonStr = JSONObject.toJSONString(pageConfig);
        JsonUtils.writeJsonFile(USER_CONFIG_PATH + pageConfig.getConfigName(), jsonStr);
        return "SUCCESS";
    }

    @Override
    public String updateCurrent(String name) {
        GlobalConfig globalConfig = new GlobalConfig();

        // 修改当前配置，写文件
        globalConfig.setCurrentConfig(name);
        String jsonNew = JSON.toJSONString(globalConfig);
        JsonUtils.writeJsonFile(GLOBAL_CONFIG_FILE, jsonNew);
        return "SUCCESS";
    }

    @Override
    public String updateSrc(String name, int index, String src) {
        String jsonStr = JsonUtils.readJsonFile(USER_CONFIG_PATH + name);
        PageConfig pageConfig = JSON.parseObject(jsonStr, PageConfig.class);
        if (pageConfig == null) {
            return "FAILED";
        }

        CellConfig cellConfig = pageConfig.getList().get(index - 1);
        cellConfig.setSrc(src);
        List<CellConfig> configList = pageConfig.getList();
        configList.remove(index - 1);
        configList.add(index - 1, cellConfig);
        pageConfig.setList(configList);

        jsonStr = JSONObject.toJSONString(pageConfig);
        JsonUtils.writeJsonFile(USER_CONFIG_PATH + name, jsonStr);

        return "SUCCESS";
    }

    @Override
    public String deleteConfig(String name) {
        File file = new File(USER_CONFIG_PATH + name + ".json");
        if (file.exists()) {
            if (file.delete()) {
                return "SUCCESS";
            }
            return "FAILED: Failed to delete file";
        }
        return "FAILED: File is not exist";
    }
}
