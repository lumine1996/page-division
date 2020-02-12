package com.cgm.pagesplit.controllers;

import com.cgm.pagesplit.entity.PageConfig;
import com.cgm.pagesplit.service.IConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cgm
 */
@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Resource
    private IConfigService configService;

    @GetMapping("/query/list")
    public ResponseEntity<List<String>> queryList() {
        return new ResponseEntity<>(configService.queryList(), HttpStatus.OK);
    }

    @GetMapping("/query/current")
    public ResponseEntity<PageConfig> queryByName() {
        return new ResponseEntity<>(configService.queryCurrent(), HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<PageConfig> queryByName(@RequestParam String configName) {
        return new ResponseEntity<>(configService.queryByName(configName), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addConfig(@RequestBody PageConfig pageConfig) {
        String result = configService.addConfig(pageConfig);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateConfig(@RequestParam String configName, @RequestBody PageConfig pageConfig) {
        String result = configService.updateConfig(configName, pageConfig);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update/current")
    public ResponseEntity<String> updateCurrent(@RequestParam String configName) {
        String result = configService.updateCurrent(configName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update/src")
    public ResponseEntity<String> updateSrc(@RequestParam String configName, @RequestParam int index, @RequestParam String src) {
        String result = configService.updateSrc(configName, index, src);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
