package com.cloudsea.common.unit.onebyone.test;

import org.springframework.stereotype.Service;

import com.cloudsea.common.dto.Result;
import com.cloudsea.common.unit.onebyone.BizId;
import com.cloudsea.common.unit.onebyone.OnebyOne;

@Service
public class BBBImpl implements BBB {
    
    @OnebyOne(bizType="BB")
    public int bb(int a, int b, @BizId String c, Result result){
        result.setMessage("work by OneByOne");
        work(a);
        return a + b;
    }
    
    public int bb(int a, int b, String c){
        return 0;
    }

    
    @OnebyOne(bizType="www")
    public int work(@BizId int i ){
        return i;
    }
}
