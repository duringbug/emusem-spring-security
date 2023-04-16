/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-16 03:23:36
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 09:58:48
 */
package tjf.emuseum.emuseum.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tjf.emuseum.emuseum.utils.Mail.ShiroMD5;

@Component("MD5PasswordEncoder")
public class MD5PasswordEncoder implements PasswordEncoder {
    private String salt;
    private ShiroMD5 shiroMD5;

    public MD5PasswordEncoder() {
        this.shiroMD5 = new ShiroMD5();
        this.salt = "95648c19-bf75-41ee-8d5c-68f8f4029ece";
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return shiroMD5.encryptPassword(rawPassword.toString(), this.salt, 1024);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String raw = shiroMD5.encryptPassword(rawPassword.toString(), this.salt, 1024);
        return raw.equals(encodedPassword);
    }
}
