package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Laboratory;

import java.util.List;

public interface LaboratoryService {

    public List<Laboratory> getAllLaboratories();
    public Laboratory getLabById(int id);


}
