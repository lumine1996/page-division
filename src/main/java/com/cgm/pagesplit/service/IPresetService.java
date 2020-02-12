package com.cgm.pagesplit.service;

import java.util.List;

public interface IPresetService {
    /**
     * 以字符串列表的形式读取配置文件
     * @return 配置列表
     */
    List<String> getConfigList();

    /**
     * 根据序号修改配置
     * @param index 序号
     * @param value 值
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateConfig(int index, String value);
}
