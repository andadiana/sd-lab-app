package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/labs")
public class LabController {

    @Autowired
    private LaboratoryService laboratoryService;

    @RequestMapping(method = GET, value = "/{labId}")
    public ResponseEntity getLaboratoryById(@PathVariable Integer labId) {
        Laboratory lab = laboratoryService.getLabById(labId);
        if (lab == null) {
            //lab not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(lab);
    }

    @RequestMapping(method = POST)
    public ResponseEntity createLaboratory(@Validated @RequestBody Laboratory lab) {
        Laboratory createdLab = laboratoryService.createLaboratory(lab);
        return ResponseEntity.status(HttpStatus.OK).body(createdLab);
    }

    @RequestMapping(method = PUT, value = "/{labId}")
    public ResponseEntity updateLaboratory(@PathVariable Integer labId, @Validated @RequestBody Laboratory lab) {
        Laboratory lab1 = laboratoryService.getLabById(labId);
        if (lab1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lab not found!");
        }
        lab.setId(labId);
        laboratoryService.updateLaboratory(lab);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{labId}")
    public ResponseEntity deleteLaboratory(@PathVariable Integer labId) {
        laboratoryService.deleteLaboratory(labId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity getLabs(@RequestParam(required = false, value = "keyword") String keyword) {
        if (keyword != null) {
            List<Laboratory> labs = laboratoryService.getAllLaboratoriesByKeyword(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(labs);
        }
        else {
            List<Laboratory> labs = laboratoryService.getAllLaboratories();
            return ResponseEntity.status(HttpStatus.OK).body(labs);
        }
    }

}
