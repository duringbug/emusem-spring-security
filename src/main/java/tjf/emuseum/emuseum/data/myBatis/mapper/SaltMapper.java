package tjf.emuseum.emuseum.data.myBatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tjf.emuseum.emuseum.entity.Salt;

@Mapper
@Repository
public interface SaltMapper extends BaseMapper<Salt> {
    @Select("SELECT user_salt FROM sys_salt WHERE id = (SELECT id FROM sys_user WHERE username = #{username})")
    String getSaltByUsername(@Param("username") String username);
}
