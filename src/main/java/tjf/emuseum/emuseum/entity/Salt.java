package tjf.emuseum.emuseum.entity;

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
public class Salt {
    Long id;
    String userSalt;
}
