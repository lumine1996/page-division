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

    @GetMapping("/query/preset")
    public ResponseEntity<List<String>> queryPreset() {
        List<String> srcList = configService.getConfigList();
        return new ResponseEntity<>(srcList, HttpStatus.OK);
    }

    @PostMapping("/update/preset/{index}")
    public ResponseEntity<String> updateFrameSrc(@PathVariable int index, @RequestParam String src) {
        String result = configService.updateConfig(index, src);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @GetMapping("/query/custom/list")
    public ResponseEntity<List<String>> queryCustomList() {
        return new ResponseEntity<>(configService.queryCustomList(), HttpStatus.OK);
    }

    @GetMapping("/query/custom/current")
    public ResponseEntity<PageConfig> queryCustomByName() {
        return new ResponseEntity<>(configService.queryCustomByName("配置1"), HttpStatus.OK);
    }

    @GetMapping("/query/custom")
    public ResponseEntity<PageConfig> queryCustomByName(@RequestParam String configName) {
        return new ResponseEntity<>(configService.queryCustomByName(configName), HttpStatus.OK);
    }

    @PostMapping("/update/custom/src")
    public ResponseEntity<String> updateCustomSrc(@RequestParam String configName, @RequestParam int index, @RequestParam String src) {
        String result = configService.updateCustomSrc(configName, index, src);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add/custom")
    public ResponseEntity<String> addCustomConfig(@RequestBody PageConfig pageConfig) {
        String result = configService.addCustomConfig(pageConfig);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update/custom")
    public ResponseEntity<String> updateCustomConfig(@RequestParam String configName, @RequestBody PageConfig pageConfig) {
        String result = configService.updateCustomConfig(configName, pageConfig);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
