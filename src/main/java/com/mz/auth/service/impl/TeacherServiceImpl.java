package com.mz.auth.service.impl;

import com.mz.auth.entity.User;
import com.mz.auth.mapper.UserMapper;
import com.mz.auth.query.UserQuery;
import com.mz.auth.service.TeacherService;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Long addTeacher(User user) {
        user.setType(2);//2代表老师
        user.setCreateTime(new Date());//注册时间
        //密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userMapper.addUser(user);
        return user.getId();
    }

    /**
     * 模糊查询列表，前端分页显示
     * 注意不是分页查询
     * @param userQuery
     * @return
     */
    @Override
    public PageList listPage(UserQuery userQuery) {
        /* 把查询得到的总的条数、每页的数据、封装到pageList对象里面去 */
        PageList pageList=new PageList();
        //查询总的条数
        Long total = userMapper.queryTotal(userQuery);
        pageList.setTotal(total);
        //查询每页的数据
        List<User> users = userMapper.queryData(userQuery);
        pageList.setRows(users);
        //封装到pageList对象里面去
        return pageList;
    }
}
