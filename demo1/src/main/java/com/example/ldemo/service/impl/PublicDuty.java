package com.example.ldemo.service.impl;

import com.example.ldemo.service.Insure;

public class PublicDuty implements Insure {
    @Override
    public void quote() {
        System.out.printf("公众险报价");
    }

    @Override
    public void complete() {

        System.out.printf("公众险完成");
    }
}
