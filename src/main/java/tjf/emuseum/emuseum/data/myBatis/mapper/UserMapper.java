/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 02:04:24
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 14:50:09
 */
package tjf.emuseum.emuseum.data.myBatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import tjf.emuseum.emuseum.entity.User;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<String> findPermissionsByUserId(Long id);

    String findSaltByUserId(Long id);
}
