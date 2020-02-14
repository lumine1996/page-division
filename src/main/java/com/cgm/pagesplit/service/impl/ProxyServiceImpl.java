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
        String parent = url.substring(0, url.lastIndexOf("/"));
        if (html.contains("src='/")) {
            html = html.replaceAll("src='/", "src='" + domain);
        }
        if (html.contains("src=\"/")) {
            html = html.replaceAll("src=\"/", "src=\"" + domain);
        }
        if (html.contains("src = '/")) {
            html = html.replaceAll("src = '/", "src = '" + domain);
        }
        if (html.contains("src = \"/")) {
            html = html.replaceAll("src = \"/", "src = \"" + domain);
        }

        if (html.contains("href='/")) {
            html = html.replaceAll("href='/", "href='" + domain);
        }
        if (html.contains("href=\"/")) {
            html = html.replaceAll("href=\"/", "href=\"" + domain);
        }
        if (html.contains("href = '/")) {
            html = html.replaceAll("href = '/", "href = '" + domain);
        }
        if (html.contains("href = \"/")) {
            html = html.replaceAll("href = \"/", "href = \"" + domain);
        }

        if (html.contains("=\"./")) {
            html = html.replaceAll("=\"./", "=\"" + parent + "/");
        }
        if (html.contains("='./")) {
            html = html.replaceAll("='./", "='" + parent + "/");
        }
        if (html.contains("= \"./")) {
            html = html.replaceAll("= \"./", "= \"" + parent + "/");
        }
        if (html.contains("= './")) {
            html = html.replaceAll("= './", "= '" + parent + "/");
        }
        return html;
    }
}
