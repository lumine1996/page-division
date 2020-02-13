package com.cgm.pagesplit.service;

import com.cgm.pagesplit.entity.PageConfig;

import java.util.List;

/**
 * @author cgm
 */

public interface IConfigService {
    /**
     * 查询自定义配置列表
     * @return 字符串列表
     */
    List<String> queryList();

    /**
     * 查询当前配置
     * @return 当前配置详情
     */
    PageConfig queryCurrent();

    /** 根据配置名称查询配置详情
     * @param name 配置名称
     * @return 配置详情
     */
    PageConfig queryByName(String name);

    /**
     * 新建自定义配置
     * @param pageConfig 配置
     * @return 是否成功（SUCCESS/FAILED）
     */
    String addConfig(PageConfig pageConfig);

    /**
     * 修改自定义配置
     * @param name 配置名称
     * @param pageConfig 配置
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateConfig(String name, PageConfig pageConfig);

    /**
     * 修改当前配置
     * @param name 配置名称
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateCurrent(String name);

    /**
     * 修改自定义配置的地址
     * @param name 配置名称
     * @param index 序号
     * @param src 地址
     * @return 是否成功（SUCCESS/FAILED）
     */
    String updateSrc(String name, int index, String src);

    /**
     * 删除配置
     * @param name 配置名称
     * @return 是否成功（SUCCESS/FAILED）
     */
    String deleteConfig(String name);
}
