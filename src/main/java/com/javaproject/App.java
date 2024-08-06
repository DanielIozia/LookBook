package com.javaproject;

import java.io.IOException;

import com.javaproject.controller.Controller;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       Controller c = new Controller();
       try {
        c.start();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
