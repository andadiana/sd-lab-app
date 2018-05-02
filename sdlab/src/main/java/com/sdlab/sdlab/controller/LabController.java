package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.dto.request.LaboratoryRequestDTO;
import com.sdlab.sdlab.dto.response.LaboratoryResponseDTO;
import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.service.LaboratoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/labs")
public class LabController {

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = GET)
    public ResponseEntity getLabs(@RequestParam(required = false, value = "keyword") String keyword) {
        if (keyword != null) {
            List<Laboratory> labs = laboratoryService.getAllLaboratoriesByKeyword(keyword);
            List<LaboratoryResponseDTO> labsDTO = labs.stream().map(l -> modelMapper.map(l, LaboratoryResponseDTO.class)).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(labsDTO);
        }
        else {
            List<Laboratory> labs = laboratoryService.getAllLaboratories();
            List<LaboratoryResponseDTO> labsDTO = labs.stream().map(l -> modelMapper.map(l, LaboratoryResponseDTO.class)).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(labsDTO);
        }
    }

    @RequestMapping(method = GET, value = "/{labId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getLaboratoryById(@PathVariable Integer labId) {
        Laboratory lab = laboratoryService.getLabById(labId);
        if (lab == null) {
            //lab not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(lab, LaboratoryResponseDTO.class));
    }

    @RequestMapping(method = POST)
    public ResponseEntity createLaboratory(@Validated @RequestBody LaboratoryRequestDTO labDTO) {
        Laboratory createdLab = laboratoryService.createLaboratory(modelMapper.map(labDTO, Laboratory.class));
        return ResponseEntity.status(HttpStatus.OK).body(createdLab);
    }

    @RequestMapping(method = PUT, value = "/{labId}")
    public ResponseEntity updateLaboratory(@PathVariable Integer labId, @Validated @RequestBody LaboratoryRequestDTO labDTO) {
        Laboratory lab1 = laboratoryService.getLabById(labId);
        if (lab1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lab not found!");
        }
        Laboratory updatedLab = modelMapper.map(labDTO, Laboratory.class);
        updatedLab.setId(labId);
        laboratoryService.updateLaboratory(updatedLab);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{labId}")
    public ResponseEntity deleteLaboratory(@PathVariable Integer labId) {
        laboratoryService.deleteLaboratory(labId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



}
