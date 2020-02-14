package com.cgm.pagesplit.service;

public interface IProxyService {
    /**
     * html代理
     * @param url 地址
     * @return 代理后的html字符串
     */
    String proxyHtml(String url);
}
