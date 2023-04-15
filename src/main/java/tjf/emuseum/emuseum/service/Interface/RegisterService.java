package tjf.emuseum.emuseum.service.Interface;

import org.springframework.http.ResponseEntity;

import tjf.emuseum.emuseum.entity.User;

public interface RegisterService {
    ResponseEntity<?> register(User user);

    ResponseEntity<?> removeAccount(User user);
}
