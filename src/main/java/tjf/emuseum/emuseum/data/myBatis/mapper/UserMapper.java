package tjf.emuseum.emuseum.data.myBatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tjf.emuseum.emuseum.entity.User;

@Mapper
@Repository()
public interface UserMapper extends BaseMapper<User> {

}
