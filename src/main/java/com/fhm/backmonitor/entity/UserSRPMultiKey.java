package com.fhm.backmonitor.entity;

import java.io.Serializable;

/**
 * Created by fhm on 2018/7/24.
 */
public class UserSRPMultiKey implements Serializable{
    private Long userId;
    private Long srpId;


    public UserSRPMultiKey() {
    }

    public UserSRPMultiKey(long userId, Long srpId) {
        this.userId = userId;
        this.srpId = srpId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getSrpId() {
        return srpId;
    }

    public void setSrpId(Long srpId) {
        this.srpId = srpId;
    }

    //  ***重写hashCode与equals方法***
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((userId == null) ? 0 : userId.hashCode());
        result = PRIME * result + ((srpId == null) ? 0 : srpId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }

        final UserSRPMultiKey other = (UserSRPMultiKey)obj;
        if(userId == null){
            if(other.userId != null){
                return false;
            }
        }else if(!userId.equals(other.userId)){
            return false;
        }
        if(srpId == null){
            if(other.srpId != null){
                return false;
            }
        }else if(!srpId.equals(other.srpId)){
            return false;
        }

        return true;
    }
}
