/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 01:02:50
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 15:15:47
 */
package tjf.emuseum.emuseum.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/3/12 23:57
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Nullable
public class LoginUser implements UserDetails {
    private User user;
    // 存放当前登录用户的权限信息，一个用户可以有多个权限
    private List<String> permissions;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    // 权限集合
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        List<GrantedAuthority> newList = new ArrayList<>();
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            newList.add(authority);
        }
        return newList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    // 是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
