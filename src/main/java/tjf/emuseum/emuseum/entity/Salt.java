package tjf.emuseum.emuseum.entity;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@TableName("sys_salt")
@Component
public class Salt {
    private Long id;
    private String userSalt;
    private String userName;
    private String email;
}
