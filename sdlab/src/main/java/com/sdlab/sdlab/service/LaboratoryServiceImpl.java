package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Override
    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    @Override
    public Laboratory getLabById(int id) {
        Optional<Laboratory> res = laboratoryRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Laboratory createLaboratory(Laboratory lab) {
        System.out.println("Lab service create: " + lab);
        lab.setId(0);
        return laboratoryRepository.save(lab);
    }

    @Override
    public Laboratory updateLaboratory(Laboratory lab) {
        Laboratory labToUpdate = laboratoryRepository.getOne(lab.getId());
        if (lab.getDate() != null) {
            labToUpdate.setDate(lab.getDate());
        }
        if (lab.getTitle() != null) {
            labToUpdate.setTitle(lab.getTitle());
        }
        if (lab.getCurricula() != null) {
            labToUpdate.setCurricula(lab.getCurricula());
        }
        if (lab.getDescription() != null) {
            labToUpdate.setDescription(lab.getDescription());
        }
        return laboratoryRepository.save(labToUpdate);
    }

    @Override
    public void deleteLaboratory(int id) {
        Optional<Laboratory> res = laboratoryRepository.findById(id);
        if (res.isPresent()) {
            laboratoryRepository.deleteById(id);
        }
    }

}
