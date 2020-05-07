package com.example.ldemo.service.impl;

import com.example.ldemo.service.Insure;

public class Build implements Insure {
    @Override
    public void quote() {
        System.out.println("建工险报价");
    }

    @Override
    public void complete() {
        System.out.println("建工险完成");

    }
}
