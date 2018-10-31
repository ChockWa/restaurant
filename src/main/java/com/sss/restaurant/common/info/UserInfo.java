package com.sss.restaurant.common.info;

import org.springframework.stereotype.Component;

@Component
public class UserInfo {

    private static ThreadLocal<Info> userInfo = new ThreadLocal<>();

    public void setUserInfo(String content){
        String[] contents = content.split("_");
        Info info = new Info();
        info.setDeviceId(contents[0]);
        info.setUid(contents[1]);
        info.setTableUid(contents[2]);
        userInfo.set(info);
    }

    public static Info getInfo(){
        return userInfo.get();
    }

    public class Info{
        private String deviceId;
        private String uid;
        private String tableUid;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTableUid() {
            return tableUid;
        }

        public void setTableUid(String tableUid) {
            this.tableUid = tableUid;
        }
    }

}
