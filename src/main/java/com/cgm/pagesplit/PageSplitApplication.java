package com.cgm.pagesplit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * @author cgm
 */
@SpringBootApplication
public class PageSplitApplication {

    public static void main(String[] args) {
        init();
        SpringApplication.run(PageSplitApplication.class, args);
    }

    private static void init() {
        String path = System.getProperty("user.home") + "/PageDivision";
        File workDir = new File(path);
        if (!workDir.exists()) {
            workDir.mkdir();
        }
    }
}
