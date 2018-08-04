package com.fhm.backmonitor.repository;


import com.fhm.backmonitor.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by wuao.tp on 2018/7/18.
 */
public interface StudRepository extends JpaRepository<Student,Integer> {
    Student getByUsernameIsAndPwdIs(String username, String pwd);
}
