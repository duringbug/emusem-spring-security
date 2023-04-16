/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-16 15:33:50
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 15:36:22
 */
package tjf.emuseum.emuseum.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class Role {
    private Long userId;
    private Long roleId;
}
