package com.cgm.pagesplit.controllers;

import com.cgm.pagesplit.entity.PageConfig;
import com.cgm.pagesplit.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cgm
 */
@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
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

    @GetMapping("/query/custom/{name}")
    public ResponseEntity<PageConfig> queryCustomByName(@PathVariable String name) {
        return new ResponseEntity<PageConfig>(configService.queryCustomByName(name), HttpStatus.OK);
    }

    @PostMapping("/update/custom/{name}/src/{index}")
    public ResponseEntity<String> updateCustomSrc(@PathVariable String name, @PathVariable int index, @RequestParam String src) {
        String result = configService.updateCustomSrc(name, index, src);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/update/custom/{name}")
    public ResponseEntity<String> updateCustomConfig(@PathVariable String name, @RequestBody PageConfig pageConfig) {
        String result = configService.updateCustomConfig(name, pageConfig);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
