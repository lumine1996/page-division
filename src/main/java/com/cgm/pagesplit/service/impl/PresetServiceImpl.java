package com.cgm.pagesplit.service.impl;

import com.cgm.pagesplit.service.IPresetService;
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
public class PresetServiceImpl implements IPresetService {
    // 项目内置预设文件
    private static final String BUILD_IN_CONFIG_FILE = "preset-config.txt";
    private static final String PRESET_CONFIG_FILE = System.getProperty("user.home") + "/PageDivision/preset-config.txt";

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
        Resource resource = new ClassPathResource(BUILD_IN_CONFIG_FILE);

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
