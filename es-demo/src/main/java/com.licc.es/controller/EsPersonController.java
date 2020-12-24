package com.licc.es.controller;

import com.licc.es.es.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsPersonController {


    @Autowired
    private PersonMapper personMapper;




}
