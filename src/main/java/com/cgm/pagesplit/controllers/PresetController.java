package com.cgm.pagesplit.controllers;

import com.cgm.pagesplit.service.IConfigService;
import com.cgm.pagesplit.service.IPresetService;
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
@RequestMapping("/preset")
public class PresetController {
    @Resource
    private IPresetService presetService;

    @GetMapping("/query/list")
    public ResponseEntity<List<String>> queryPreset() {
        List<String> srcList = presetService.getConfigList();
        return new ResponseEntity<>(srcList, HttpStatus.OK);
    }

    @PostMapping("/update/{index}")
    public ResponseEntity<String> updateFrameSrc(@PathVariable int index, @RequestParam String src) {
        String result = presetService.updateConfig(index, src);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
