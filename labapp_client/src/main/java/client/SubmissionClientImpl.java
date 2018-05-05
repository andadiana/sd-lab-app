package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.SubmissionResponseDTO;
import model.Submission;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubmissionClientImpl implements SubmissionClient {

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;


    public SubmissionClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Submission> getSubmissions() {
        try {
            String jsonResponse = HTTPRequest.sendGet("/submissions");

            System.out.println("RESPONSE: " + jsonResponse);
            List<SubmissionResponseDTO> submissionsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<SubmissionResponseDTO>>(){});
            return submissionsDTO.stream().map(s -> modelMapper.map(s, Submission.class)).collect(Collectors.toList());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
