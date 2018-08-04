package com.fhm.backmonitor.test;

import com.fhm.backmonitor.entity.SRP;
import com.fhm.backmonitor.repository.SrpRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by wuao.tp on 2018/7/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class SRPtest {
    @Autowired
    private SrpRepository srpRepository;

    @Test
    public void insertSRP(){
        for (int i = 0; i < 10; i++) {
            SRP srp = new SRP();
            srp.setSrpName("spr"+i);
            SRP save = srpRepository.save(srp);
            System.out.println("save.setSrpName(); = " + save.getSrpName());
            }
        }
    }
