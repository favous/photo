package com.cloudsea.common.unit.onebyone.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsea.common.dto.Result;
import com.cloudsea.common.unit.onebyone.BizId;
import com.cloudsea.common.unit.onebyone.OnebyOne;

@Service
public class AAA {

    @Autowired
    BBB bb;
    
    @OnebyOne(bizType="AA")
    public int aa(int i, @BizId int j, String k){
        Result result = new Result();
        int a = bb.bb(i*2, j*2, k+":bb", result);
        System.out.println(result.getMessage());
        int b = bb.bb(i*2, j*2, k+":bb");
        System.out.println(b);
        return a/2;
    }
}
