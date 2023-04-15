package tjf.emuseum.emuseum.utils.Mail;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/3/12 18:51
 * @description:
 */
@Service("shiroMD5")
public class ShiroMD5 {
    public String encryptPassword(String s, String random_salt, int hash) {
        Md5Hash md5Hash = new Md5Hash(s, random_salt, hash);
        return md5Hash.toHex();
    }
}
