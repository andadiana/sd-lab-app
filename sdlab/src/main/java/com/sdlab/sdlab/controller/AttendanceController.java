package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.dto.request.AttendanceRequestDTO;
import com.sdlab.sdlab.dto.response.AttendanceResponseDTO;
import com.sdlab.sdlab.model.Attendance;
import com.sdlab.sdlab.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = GET)
    public List<AttendanceResponseDTO> getAllAttendance() {
        //TODO change return value to ResponseEntity?
        List<Attendance> attendance = attendanceService.getAllAttendance();
        List<AttendanceResponseDTO> attendanceDTO = attendance.stream()
                .map(a -> modelMapper.map(a, AttendanceResponseDTO.class)).collect(Collectors.toList());
        return attendanceDTO;
    }

    @RequestMapping(method = GET, value = "/{attendanceId}")
    public ResponseEntity getAttendanceById(@PathVariable Integer attendanceId) {
        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
        if (attendance == null) {
            //attendance not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(attendance, AttendanceResponseDTO.class));
    }

    @RequestMapping(method = POST)
    public ResponseEntity createAttendance(@RequestBody AttendanceRequestDTO attendanceDTO) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Attendance createdAttendance = attendanceService.createAttendance(modelMapper.map(attendanceDTO, Attendance.class));
//        return ResponseEntity.status(HttpStatus.OK).body(createdAttendance);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = PUT, value = "/{attendanceId}")
    public ResponseEntity updateAttendance(@PathVariable Integer attendanceId, @RequestBody AttendanceRequestDTO attendanceDTO) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Attendance attendance1 = attendanceService.getAttendanceById(attendanceId);
        if (attendance1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendance not found!");
        }
        Attendance updatedAttendance = modelMapper.map(attendanceDTO, Attendance.class);
        updatedAttendance.setId(attendanceId);
        attendanceService.updateAttendance(updatedAttendance);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{attendanceId}")
    public ResponseEntity deleteAttendance(@PathVariable Integer attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
