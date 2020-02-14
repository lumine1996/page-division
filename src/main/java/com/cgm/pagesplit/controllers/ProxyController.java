package com.cgm.pagesplit.controllers;

import com.cgm.pagesplit.service.IProxyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author cgm
 */
@CrossOrigin
@RestController
@RequestMapping("/proxy")
public class ProxyController {
    @Resource
    private IProxyService proxyService;

    @GetMapping("/html")
    public String proxyHtml (@RequestParam String url) {
        return proxyService.proxyHtml(url);
    }
}
