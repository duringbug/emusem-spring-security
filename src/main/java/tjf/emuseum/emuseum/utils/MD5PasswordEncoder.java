package tjf.emuseum.emuseum.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tjf.emuseum.emuseum.utils.Mail.ShiroMD5;

@Component("MD5PasswordEncoder")
public class MD5PasswordEncoder implements PasswordEncoder {
    private String salt;
    private ShiroMD5 shiroMD5;

    public MD5PasswordEncoder() {
    }

    public MD5PasswordEncoder(String salt) {
        this.salt = salt;
        this.shiroMD5 = new ShiroMD5();
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return shiroMD5.encryptPassword(rawPassword.toString(), this.salt, 1024);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }

}
