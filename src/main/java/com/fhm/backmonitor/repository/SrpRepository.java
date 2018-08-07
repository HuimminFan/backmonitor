package com.fhm.backmonitor.repository;


import com.fhm.backmonitor.entity.SRP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by fhm on 2018/7/19.
 */
public interface SrpRepository extends JpaRepository<SRP,Long>,JpaSpecificationExecutor<SRP> {

    //根据srpId和userName获取srp
//    SRP findSRPBySrpIdAndUserName();

    //根据userId获取srp列表
//    List<SRP> findSRPSByuserId(Long userId);

    /*根据id获取srp*/
    SRP findBySrpIdOrderBySrpId(Long srpId);

    @Modifying
    @Query(value = "delete from SRP s where s.srpId = :srpId ")
    int deleteBySrpId(Long srpId);


//    void deleteById(Long aLong);

    @Override
    void delete(SRP srp);

    //显示用户列表
    @Query(value = "select users.user_id,users.role,users.phone,users.user_pwd, string_agg(srps.srp_name,'，') as srpnames,users.user_name from users left join user_srp on users.user_id = user_srp.user_id left join srps on user_srp.srp_id = srps.srp_id group by users.user_id order by user_id", nativeQuery = true)
    List<Object[]> getusers();

//    @Modifying
//    @Query("UPDATE User u SET u.email = :email WHERE userId = :userId")
//    void updateUserEmail(@Param("userId") Long id, @Param("email") String email);

}
