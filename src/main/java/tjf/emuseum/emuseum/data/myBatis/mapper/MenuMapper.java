/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 11:00:19
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 15:18:25
 */
package tjf.emuseum.emuseum.data.myBatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tjf.emuseum.emuseum.entity.Menu;

import java.util.List;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/15 11:00
 * @description:
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userid);
}
