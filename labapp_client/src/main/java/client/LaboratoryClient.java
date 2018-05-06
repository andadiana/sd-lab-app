package client;

import model.Laboratory;

import java.util.List;

public interface LaboratoryClient {

    public List<Laboratory> getLaboratories();
    public List<Laboratory> getLaboratories(String keyword);
    public Laboratory getLaboratory(int id);
    //TODO boolean return value for create operations? to know when operations are not successful
    public void createLaboratory(Laboratory lab);
    public void updateLaboratory(Laboratory lab);
    public void deleteLaboratory(int id);
}
