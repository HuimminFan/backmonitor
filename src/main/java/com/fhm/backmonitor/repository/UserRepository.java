package com.fhm.backmonitor.repository;


import com.fhm.backmonitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by fhm on 2018/7/19.
 */
public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {

    //获得单个用户对象，根据username和pwd的字段匹配
    User getByUserIdIsAndAndUserPwdIs(Long userid, String userpwd);

    //根据用户名获取用户
    User findByUserName(String userName);

    //根据手机号获取用户
    User findByPhone(Long phone);

    //根据userId获取用户
    User findByUserId(Long userId);

    //获取用户列表，包括用户基本信息和srpName
    @Query(value = "select users.user_id,users.email,users.role,users.phone,users.user_pwd,users.remark, string_agg(srps.srp_name,'，') as srpnames,users.user_name from users left join user_srp on users.user_id = user_srp.user_id left join srps on user_srp.srp_id = srps.srp_id group by users.user_id order by user_id", nativeQuery = true)
    List<Object[]> getuserlist();


//    //查询用户名称包含username字符串的用户对象
//    List<User> findByUsernameContaining(String username);
//
//    //获得单个用户对象，根据username和pwd的字段匹配
//    User getByUsernameIsAndUserpwdIs(String username, String userpwd);
//
    //精确匹配username的用户对象
//    User getByUsernameIs(String username);
//
//    @Modifying
//    @Query("update User u set u.username = :username where u.userId= :userId")
//    void updateUserUsername(@Param("userId") Long userid, @Param("username") String username);

//    //没用到
//    /*更新用户密码*/
//    @Modifying
//    @Query("UPDATE User u SET u.userPwd = :userPwd WHERE userId = :userId")
//    void updateUserPwd(@Param("userId") Long id, @Param("userPwd") String userPwd);
}
