package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/labs")
public class LabController {

    @Autowired
    private LaboratoryService laboratoryService;

    @RequestMapping(method = GET)
    public List<Laboratory> getAllLaboratories() {
        List<Laboratory> labs = laboratoryService.getAllLaboratories();
        for (Laboratory lab: labs) {
            System.out.println(lab);
        }
        return labs;
    }

    @RequestMapping(method = GET, value = "/{labId}")
    public Laboratory getLaboratoryById(@PathVariable Integer labId) {
        Laboratory lab = laboratoryService.getLabById(labId);
        return lab;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<String> createLaboratory(@RequestBody Laboratory lab) {
        //TODO check if lab is valid (add method in labservice

//        if (personService.isValid(person)) {
//            personRepository.persist(person);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        laboratoryService.createLaboratory(lab);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = PUT, value = "/{labId}")
    public ResponseEntity<String> updateLaboratory(@RequestBody Laboratory lab) {
        //TODO check if lab is valid (add method in labservice

//        if (personService.isValid(person)) {
//            personRepository.persist(person);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        laboratoryService.updateLaboratory(lab);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{labId}")
    public ResponseEntity<String> deleteLaboratory(@PathVariable Integer labId) {

        // true -> can delete
        // false -> cannot delete, f.e. is FK reference somewhere
        boolean wasOk = laboratoryService.deleteLaboratory(labId);

        //TODO do stuff if !ok
//        if (!wasOk) {
//            // will write to user which item couldn't be deleted
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            model.addAttribute("item", item);
//            return "items/error";
//        }
//
//        return "redirect:/items";

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
