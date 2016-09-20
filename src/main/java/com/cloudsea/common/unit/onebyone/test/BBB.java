package com.cloudsea.common.unit.onebyone.test;

import org.springframework.stereotype.Service;

import com.cloudsea.common.dto.Result;
import com.cloudsea.common.unit.onebyone.BizId;

@Service
public interface BBB {

    int bb(int a, int b, @BizId String c, Result result);
    int bb(int a, int b, @BizId String c);
}
