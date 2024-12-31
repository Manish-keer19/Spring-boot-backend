package com.ms19.LearningSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dev {

    // @Autowired // it is called field injection
    // private Leptop leptop;

    // public Dev(Leptop leptop) { // it is called contructore injection
    // this.leptop = leptop;
    // }

    private Leptop leptop;

    @Autowired
    public void setLeptop(Leptop leptop) { // it is called setter injection
        this.leptop = leptop;
    }

    @Autowired
    private Computer computer;

    public void build() {
        leptop.compile();
        System.out.println("build spring boot project");
    }
}
