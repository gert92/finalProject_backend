package com.seg.controller;

import com.seg.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PackageController {

    private PackageRepository packageRepository;

    @Autowired
    public PackageController(PackageRepository packageRepository){
        this.packageRepository=packageRepository;
    }
}
