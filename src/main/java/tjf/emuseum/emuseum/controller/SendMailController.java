/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 15:01:18
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 15:09:31
 */
package tjf.emuseum.emuseum.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import tjf.emuseum.emuseum.entity.MailInfo;
import tjf.emuseum.emuseum.utils.Mail.SendMail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
public class SendMailController {
    @Qualifier("sendMail")
    @Autowired
    private SendMail sendMail;

    @PostMapping("/user/sendMail")
    public ResponseEntity<?> login(@RequestBody MailInfo mailInfo) {
        return sendMail.sendNumber(mailInfo.getMail(), UUID.randomUUID().toString());
    }
}
