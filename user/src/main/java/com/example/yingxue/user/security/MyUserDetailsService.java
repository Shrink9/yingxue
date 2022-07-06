package com.example.yingxue.user.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.common.user.entity.Role;
import com.example.yingxue.common.user.entity.UserRole;
import com.example.yingxue.user.mapper.RoleMapper;
import com.example.yingxue.user.mapper.UserMapper;
import com.example.yingxue.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService, UserDetailsPasswordService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException{
        //查到用户
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("phone",phone);
        List<User> users=userMapper.selectList(userQueryWrapper);
        User user=null;
        //如果未注册则先注册
        if(users.size()==0){
            user=new User();
            user.setAvatar(User.DEFAULT_AVATAR);
            user.setCreatedAt(new Date());
            user.setFollowersCount(0);
            user.setFollowingCount(0);
            user.setIntro(User.DEFAULT_INTRO);
            user.setName(phone);
            user.setPhone(phone);
            user.setUpdatedAt(new Date());
            user.setPhoneLinked(1);
            user.setWechatLinked(0);
            userMapper.insert(user);
        }
        else{
            user=users.get(0);
        }
        //查询手机验证码
        String verifyCode=stringRedisTemplate.opsForValue()
                                             .get(User.VERIFY_CODE_PREFIX+phone);
        QueryWrapper<UserRole> userRoleQueryWrapper=new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",user.getId());
        //查出用户的角色id
        List<UserRole> userRoles=userRoleMapper.selectList(userRoleQueryWrapper);
        ArrayList<SimpleGrantedAuthority> authorities=new ArrayList<>();
        if(userRoles.size()>0){
            Integer[] roleIds=new Integer[userRoles.size()];
            for(int i=0;i<userRoles.size();i++){
                roleIds[i]=userRoles.get(i)
                                    .getRoleId();
            }
            QueryWrapper<Role> roleQueryWrapper=new QueryWrapper<>();
            roleQueryWrapper.in("id",roleIds);
            //查出用户的角色
            List<Role> roles=roleMapper.selectList(roleQueryWrapper);
            for(Role role: roles){
                //添加角色,角色必须以"ROLE_"开头.
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getPhone(),verifyCode,authorities);
    }
    @Override
    public UserDetails updatePassword(UserDetails userDetails,String s){
        return null;
    }
}
