package org.smartweb.service;

import org.smartweb.Annotation.Service;

@Service
public class TestService {

    public void say() {
        System.out.println("test service invoke say!!!");
    }
}
