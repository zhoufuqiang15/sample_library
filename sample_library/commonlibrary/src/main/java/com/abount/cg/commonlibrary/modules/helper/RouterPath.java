package com.abount.cg.commonlibrary.modules.helper;

/**
 * Created by mo_yu on 2018/3/23.
 * ARouter的路由Path统一管理定义
 */

public class RouterPath {

    public static class IntentPath {

        //-------------------acg app------------------------
        public static class AcgApp {
            static final String ACG_APP = "/App";
            public static class Debug {
                public static final String CHANGE_HOST = ACG_APP + "/change_host_activity";
            }
        }

    }
}
