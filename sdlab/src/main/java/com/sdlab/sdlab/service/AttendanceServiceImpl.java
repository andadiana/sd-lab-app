package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Attendance;
import com.sdlab.sdlab.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance getAttendanceById(int id) {
        Optional<Attendance> res = attendanceRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    @Override
    public List<Attendance> getAttendanceByLabId(int labId) {
        return attendanceRepository.findByLaboratoryId(labId);
    }

    @Override
    public Attendance createAttendance(Attendance attendance) {
        attendance.setId(0);
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance updateAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(int id) {
        Optional<Attendance> res = attendanceRepository.findById(id);
        if (res.isPresent()) {
            attendanceRepository.deleteById(id);
        }
    }
}
