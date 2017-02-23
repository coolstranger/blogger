package com.abc.xyz.web;

import com.abc.xyz.common.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ApplicationManager {



    @PostConstruct
    public void initialize(){
        ErrorCodes.init();
    }

}
