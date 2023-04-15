/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 01:01:34
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 01:12:30
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
    @RequestMapping(value = "/test", method = { RequestMethod.GET })
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('test')")
    ResponseEntity<Map<String, String>> login() {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("test", "sweniwnnccq");
        return ResponseEntity.ok(responseData);
    }
}
