package com.cloudsea.common.unit.onebyone.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:spring-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OneByOneTest {

    @Autowired
    AAA aaa;
    
    @Test
//  @Rollback(value = false)
    public void Test1() {
        System.out.println("sssssssss");
        int a = aaa.aa(0, 1, "s");
        System.out.println(a);
    }
}
