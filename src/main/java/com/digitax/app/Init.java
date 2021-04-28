package com.digitax.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {



    @Override
    public void run(String... args) throws Exception {
        System.out.println("***************************************");
        System.out.print("**************");
        System.out.print("Digitax API");
        System.out.println("**************");
        System.out.println("***************************************");
    }
}