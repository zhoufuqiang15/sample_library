package com.abount.cg.httplibrary.authorization;

import com.abount.cg.httplibrary.models.AppUser;
import com.abount.cg.httplibrary.models.User;
import com.abount.cg.httplibrary.utils.GsonUtil;
import com.google.gson.Gson;

/**
 * Created by mo_yu on 2018/3/22.
 */
public class AppUserConverter implements UserConverterDelegate{
    @Override
    public User fromJson(String jsonString) {
        try {
            return GsonUtil.getGsonInstance().fromJson(jsonString, AppUser.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toJson(User user) {
        return new Gson().toJson(user);
    }
}
