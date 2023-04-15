package com.mz.auth.service;

import com.mz.auth.entity.User;
import com.mz.auth.query.UserQuery;
import com.mz.auth.util.PageList;

public interface TeacherService {
    Long addTeacher(User user);

    PageList listPage(UserQuery userQuery);

}
