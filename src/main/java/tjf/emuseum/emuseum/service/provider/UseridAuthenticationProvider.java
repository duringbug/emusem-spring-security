package tjf.emuseum.emuseum.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tjf.emuseum.emuseum.entity.LoginUser;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/18 17:27
 * @description:
 */
@Component
public class UseridAuthenticationProvider implements AuthenticationProvider {
    @Qualifier("userDetailsServiceImpl0")
    @Autowired
    private UserDetailsService userDetailsService0;
    @Qualifier("MD5PasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userid = authentication.getName();
        String password = authentication.getCredentials().toString();

        LoginUser loginUser = (LoginUser) userDetailsService0.loadUserByUsername(userid);
        if (passwordEncoder.matches(password, loginUser.getPassword())) {
            //如果密码匹配，则返回Authentication接口的实现以及必要的详细信息
            return new UsernamePasswordAuthenticationToken(loginUser, password, loginUser.getAuthorities());
        } else {	//密码不匹配，抛出异常
            throw new BadCredentialsException("Something went wrong!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
