package com.cgm.pagesplit.service.impl;

import com.cgm.pagesplit.service.IProxyService;
import com.cgm.pagesplit.util.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author cgm
 */
@Service
public class ProxyServiceImpl implements IProxyService {
    @Override
    public String proxyHtml(String url) {
        try {
            return replaceDomain(url);
        } catch (IOException e) {
            e.printStackTrace();
            return "FAILED: An IOException was happened";
        }
    }

    public static String replaceDomain(String url) throws IOException {
        String html = HttpUtils.doGet(url);
        String domain = url.split("//")[0] + "//" + url.split("/")[2] + "/";

        StringBuilder stringBuilder = new StringBuilder(html);
        int index = html.indexOf("<head>");
        stringBuilder.insert(index, "<base href=\"" + domain + "\">");

        return stringBuilder.toString();
    }
}
