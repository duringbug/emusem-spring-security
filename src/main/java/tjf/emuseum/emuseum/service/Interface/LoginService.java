package tjf.emuseum.emuseum.service.Interface;

import org.springframework.http.ResponseEntity;
import tjf.emuseum.emuseum.entity.User;

import java.util.Map;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/15 10:03
 */
public interface LoginService {
    ResponseEntity<?> login(User user);
    ResponseEntity<?> logout();
}
