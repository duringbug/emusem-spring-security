/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 01:01:34
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 19:06:34
 */
package tjf.emuseum.emuseum.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {
    @RequestMapping(value = "/testGet", method = { RequestMethod.GET })
    @Transactional(rollbackFor = Exception.class)
    // @PreAuthorize("hasAuthority('test')")
    ResponseEntity<Map<String, String>> get() {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("test", "get");
        return ResponseEntity.ok(responseData);
    }

    @RequestMapping(value = "/testPost", method = { RequestMethod.GET })
    @Transactional(rollbackFor = Exception.class)
    // @PreAuthorize("hasAuthority('test')")
    ResponseEntity<Map<String, String>> post() {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("test", "post");
        return ResponseEntity.ok(responseData);
    }
}
