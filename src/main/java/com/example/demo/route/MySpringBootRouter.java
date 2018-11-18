//package com.example.demo.route;
//
//import org.apache.camel.LoggingLevel;
//import org.apache.camel.spring.boot.FatJarRouter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MySpringBootRouter extends FatJarRouter {
//
//
//    @Override
//    public void configure() {
//        from("timer:trigger").log(LoggingLevel.INFO, "${body}").end();
//        
//        
//    }
//
//
//}