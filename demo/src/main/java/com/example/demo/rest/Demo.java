package com.example.demo.rest;


import org.apache.catalina.util.IOTools;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-05-03 2:05 PM
 */
@RestController
public class Demo {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(HttpServletRequest request) {
        System.out.println(">>>>>>>>>>>>>>>");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        return "hello:" + id + " " +  name;
    }

    @RequestMapping(path = "/json", consumes = "application/json")
    @ResponseBody
    public String json(HttpServletRequest request) throws IOException {
        System.out.println(">>>>>>>>>>>>>>>");

        System.out.println(IOUtils.toString(request.getInputStream(), Charset.forName("utf-8")));
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        return "hello:" + id + " " +  name;
    }


    @RequestMapping(path = "/xml", consumes = "application/xml")
    @ResponseBody
    public String xml(HttpServletRequest request) throws IOException {
        System.out.println(">>>>>>>>>>>>>>>");

        System.out.println(IOUtils.toString(request.getInputStream(), Charset.forName("utf-8")));
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        return "hello:" + id + " " +  name;
    }

}
