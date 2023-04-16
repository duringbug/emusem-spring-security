/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-16 14:40:23
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 15:43:22
 */
/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 11:00:19
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 15:18:25
 */
package tjf.emuseum.emuseum.data.myBatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tjf.emuseum.emuseum.entity.Role;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/15 11:00
 * @description:
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}