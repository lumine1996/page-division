package com.cgm.pagesplit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cgm.pagesplit.entity.PageConfig;
import com.cgm.pagesplit.service.IConfigService;
import com.cgm.pagesplit.util.JsonUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author cgm
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    private static final String DEFAULT_CONFIG_PATH = "default-config.txt";
    private static final String USER_CONFIG_PATH = System.getProperty("user.home") + "/PageSplit/";
    private static final String PRESET_CONFIG_FILE = USER_CONFIG_PATH + "preset-config.txt";

    @Override
    public List<String> getConfigList() {
        List<String> configList = new ArrayList<>();
        Properties properties = loadProperties();
        if (properties == null) {
            return configList;
        }

        // 填充结果列表
        configList.add(properties.getProperty("amount"));
        String url;
        int index = 1;
        do {
            url = properties.getProperty("frame" + index);
            index ++;
            configList.add(url);
        } while (!StringUtils.isEmpty(url));

        return configList;
    }

    @Override
    public String updateConfig(int index, String value) {
        Properties properties = loadProperties();
        if (properties == null) {
            return "FAILED: Failed to load properties";
        }

        if (index == 0) {
            properties.setProperty("amount", value);
        } else {
            properties.setProperty("frame" + index, value);
        }

        try {
            storeProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
            return "FAILED: Failed to store properties";
        }

        return "SUCCESS";
    }

    @Override
    public List<String> queryCustomList() {
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
        return customList;
    }

    @Override
    public PageConfig queryCustomByName(String name) {
        String json = JsonUtils.readJsonFile(USER_CONFIG_PATH + name);
        PageConfig pageConfig = JSON.parseObject(json, PageConfig.class);
        if (pageConfig == null) {
            return new PageConfig();
        }

        return pageConfig;
    }

    @Override
    public String updateCustomSrc(String name, int index, String src) {
        String jsonStr = JsonUtils.readJsonFile(USER_CONFIG_PATH + name);
        PageConfig pageConfig = JSON.parseObject(jsonStr, PageConfig.class);
        if (pageConfig == null) {
            return "FAILED";
        }
        pageConfig.getList().get(index).setSrc(src);
        jsonStr = JSONObject.toJSONString(pageConfig);
        JsonUtils.writeJsonFile(USER_CONFIG_PATH + name, jsonStr);

        return "SUCCESS";
    }

    @Override
    public String addCustomConfig(PageConfig pageConfig) {
        List<String> configList = queryCustomList();
        if (configList.contains(pageConfig.getConfigName())) {
            return "EXISTED";
        }

        // 新增文件
        String jsonStr = JSONObject.toJSONString(pageConfig);
        JsonUtils.writeJsonFile(USER_CONFIG_PATH + pageConfig.getConfigName(), jsonStr);
        return "SUCCESS";
    }

    @Override
    public String updateCustomConfig(String name, PageConfig pageConfig) {
        List<String> configList = queryCustomList();
        if (configList.contains(pageConfig.getConfigName())) {
            return "NOT EXISTED";
        }

        // 覆盖文件
        String jsonStr = JSONObject.toJSONString(pageConfig);
        JsonUtils.writeJsonFile(USER_CONFIG_PATH + pageConfig.getConfigName(), jsonStr);
        return "SUCCESS";
    }


    /**
     * 获取Properties封装的配置信息
     * @return 配置信息
     */
    private Properties loadProperties(){
        // 获取配置文件
        File configFile = new File(PRESET_CONFIG_FILE);
        if (!configFile.exists() && !initConfig(configFile)) {
            return null;
        }

        Properties properties = new Properties();
        try (InputStream propertyInputStream = new FileInputStream(PRESET_CONFIG_FILE)) {
            properties.load(propertyInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Loading '" + PRESET_CONFIG_FILE + "'");
            return null;
        }
        return properties;
    }


    /**
     * 初始化配置
     * @param configFile 配置文件
     * @return 是否成功
     */
    private Boolean initConfig(File configFile) {
        try {
            // 判断父目录是否存在，如果不存在，则创建
            if (configFile.getParentFile() != null && !configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }
            if (!configFile.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Resource resource = new ClassPathResource(DEFAULT_CONFIG_PATH);

        //创建输入输出流
        try (InputStream in = resource.getInputStream();
             OutputStream out = new FileOutputStream(configFile)) {
            byte[] bytes = new byte[1024];
            int len;
            while((len = in.read(bytes)) != -1) {
                out.write(bytes,0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void storeProperties(Properties properties) throws IOException {
        OutputStream outputStream = new FileOutputStream(PRESET_CONFIG_FILE);
        properties.store(outputStream, null);
        outputStream.close();
    }
}
