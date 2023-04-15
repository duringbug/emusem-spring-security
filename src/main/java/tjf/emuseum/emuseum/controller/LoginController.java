package tjf.emuseum.emuseum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tjf.emuseum.emuseum.entity.LoginToken;
import tjf.emuseum.emuseum.service.Interface.LoginService;

@RestController
public class LoginController {
    @Qualifier("loginServiceImpl1")
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginToken loginToken) {
        return loginService.login(loginToken);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logout() {
        return loginService.logout();
    }
}
