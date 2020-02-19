package com.cgm.pagesplit.service.impl;

import com.cgm.pagesplit.service.IProxyService;
import com.cgm.pagesplit.util.HttpUtils;
import com.cgm.pagesplit.util.TextFileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

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

    public String replaceDomain(String url) throws IOException {
        String html = HttpUtils.doGet(url);
        String baseUri = url.split("//")[0] + "//" + url.split("/")[2];
        String domain = baseUri.split("//")[1];
        Resource resource = new ClassPathResource("static/js/config.js");
        File configJs = resource.getFile();

        String backend = TextFileUtils.read(configJs).split("\"")[1];

        Document document = Jsoup.parse(html);
        // 添加<base>标签及href属性
        Elements base = document.getElementsByTag("base");
        if (base.size() == 0) {
            Elements title = document.getElementsByTag("title");
            title.after("<base href='" + baseUri + "'>");
        } else if (!base.hasAttr("href")) {
            base.get(0).attr("href", baseUri);
        }

        // 修改href为代理链接
        Elements aList = document.getElementsByTag("a");
        for (Element a : aList) {
            // target未设置或为self时进行处理，否则跳过
            String target = a.attr("target");
            if (!StringUtils.isEmpty(target) && !"_self".equals(target)) {
                continue;
            }

            String href = a.attr("href");
            if (href.indexOf("/") == 0) {
                href = baseUri + href;
            }
            if (href.indexOf(baseUri) == 0 || href.contains(domain)) {
                String proxyHref = backend + "/proxy/html?url=" + URLEncoder.encode(href, "UTF-8");
                a.attr("href", proxyHref);
            }
        }

        return document.toString();
    }
}
