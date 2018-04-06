package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LabController {

    @Autowired
    private LaboratoryService laboratoryService;

    @RequestMapping("/")
    public void home() {
        List<Laboratory> labs = laboratoryService.getAllLaboratories();
        for (Laboratory lab: labs) {
            System.out.println(lab);
        }
    }

}
