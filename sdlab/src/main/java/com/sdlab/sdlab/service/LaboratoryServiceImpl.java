package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.repository.LaboratoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    private LaboratoryRepository laboratoryRepository;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public Laboratory getLabById(int id) {
        Optional<Laboratory> res = laboratoryRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }
}
