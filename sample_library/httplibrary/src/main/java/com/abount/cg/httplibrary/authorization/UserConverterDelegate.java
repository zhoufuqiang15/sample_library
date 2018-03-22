package com.abount.cg.httplibrary.authorization;


import com.abount.cg.httplibrary.models.User;

/**
 * Created by mo_yu on 2018/3/22.用户信息
 */
public interface UserConverterDelegate {
    public User fromJson(String jsonString);

    public String toJson(User user);
}
