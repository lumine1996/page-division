package com.cgm.pagesplit.service;

import com.cgm.pagesplit.entity.PageConfig;

import java.util.List;

/**
 * @author cgm
 */

public interface IConfigService {
    /**
     * 以字符串列表的形式读取配置文件
     * @return 配置列表
     */
    public List<String> getConfigList();

    /**
     * 根据序号修改配置
     * @param index 序号
     * @param value 值
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateConfig(int index, String value);


    /**
     * 查询自定义配置列表
     * @return 字符串列表
     */
    List<String> queryCustomList();

    /** 根据配置名称查询配置详情
     * @param name 配置名称
     * @return 配置详情
     */
    PageConfig queryCustomByName(String name);

    /**
     * 修改自定义配置的地址
     * @param name 配置名称
     * @param index 序号
     * @param src 地址
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateCustomSrc(String name, int index, String src);

    /**
     * 新建自定义配置
     * @param pageConfig 配置
     * @return 是否成功（SUCCESS/FAILED）
     */
    String addCustomConfig(PageConfig pageConfig);

    /**
     * 修改自定义配置
     * @param name 配置名称
     * @param pageConfig 配置
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateCustomConfig(String name, PageConfig pageConfig);
}
