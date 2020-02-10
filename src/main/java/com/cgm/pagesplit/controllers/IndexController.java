package com.cgm.pagesplit.controllers;

import com.cgm.pagesplit.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cgm
 */
@Controller
public class IndexController {
    @Autowired
    private IConfigService propertyService;

    @RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        int amount = Integer.parseInt(propertyService.getConfigList().get(0));
        response.sendRedirect("/division" + amount + ".html");

    }

    @RequestMapping("/8")
    public void indexEight(HttpServletResponse response) throws IOException {

        response.sendRedirect("/division8.html");

    }

    @RequestMapping("/9")
    public void indexNine(HttpServletResponse response) throws IOException {

        response.sendRedirect("/division9.html");

    }
}
