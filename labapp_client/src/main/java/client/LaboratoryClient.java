package client;

import model.Laboratory;
import model.UserCredentials;

import java.util.List;

public interface LaboratoryClient {

    public List<Laboratory> getLaboratories(UserCredentials userCredentials) throws Exception;
    public List<Laboratory> getLaboratories(String keyword, UserCredentials userCredentials) throws Exception;
    public Laboratory getLaboratory(int id, UserCredentials userCredentials) throws Exception;
    public void createLaboratory(Laboratory lab, UserCredentials userCredentials) throws Exception;
    public void updateLaboratory(Laboratory lab, UserCredentials userCredentials) throws Exception;
    public void deleteLaboratory(int id, UserCredentials userCredentials) throws Exception;
}
