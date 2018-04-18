package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.auth.WebSecurityConfig;
import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.service.LaboratoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.sql.Date;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LabController.class)
@ContextConfiguration(classes={WebSecurityConfig.class})
public class LabControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LaboratoryService laboratoryServiceMock;

    @Test
    public void getLabById_labExists_OK() {
        Laboratory lab = new Laboratory();
        lab.setId(1);
        lab.setDescription("description");
        lab.setCurricula("curricula");
        lab.setDate(new Date(2018, 8, 10));
        lab.setTitle("lab2");
        lab.setLabNumber(2);

        when(laboratoryServiceMock.getLabById(1)).thenReturn(lab);

        try {
            mvc.perform(get("/labs/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.description", is("description")))
                    .andExpect(jsonPath("$.curricula", is("curricula")))
                    .andExpect(jsonPath("$.labNumber", is(2)));
        }catch (Exception e) {
            e.printStackTrace();
        }

        verify(laboratoryServiceMock, times(1)).getLabById(1);
        //verifyNoMoreInteractions(laboratoryServiceMock);
    }

    @Test
    public void getLab_LabIdDoesNotExist_404Returned() {
        Laboratory lab = new Laboratory();
        lab.setId(1);
        lab.setDescription("description");
        lab.setCurricula("curricula");
        lab.setDate(new Date(2018, 8, 10));
        lab.setTitle("lab2");
        lab.setLabNumber(2);

        when(laboratoryServiceMock.getLabById(1)).thenReturn(lab);

        try {
            mvc.perform(get("/labs/{id}", 2))
                    .andExpect(status().isNotFound());
        }catch (Exception e) {
            e.printStackTrace();
        }

        verify(laboratoryServiceMock, times(1)).getLabById(2);
        verifyNoMoreInteractions(laboratoryServiceMock);
    }

    @Test
    public void getAllLabs_labsFound_Ok() {
        Laboratory first = new Laboratory();
        first.setId(1);
        first.setDescription("description");
        first.setCurricula("curricula");
        first.setDate(new Date(2018, 8, 10));
        first.setTitle("lab1");
        first.setLabNumber(1);

        Laboratory second = new Laboratory();
        second.setId(2);
        second.setDescription("description");
        second.setCurricula("curricula");
        second.setDate(new Date(2018, 8, 10));
        second.setTitle("lab2");
        second.setLabNumber(2);

        when(laboratoryServiceMock.getAllLaboratories()).thenReturn(Arrays.asList(first, second));

        try {
            mvc.perform(get("/api/todo"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].title", is("lab1")))
                    .andExpect(jsonPath("$[0].labNumber", is(1)))
                    .andExpect(jsonPath("$[0].id", is(2)))
                    .andExpect(jsonPath("$[0].title", is("lab2")))
                    .andExpect(jsonPath("$[0].labNumber", is(2)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(laboratoryServiceMock, times(1)).getAllLaboratories();
        verifyNoMoreInteractions(laboratoryServiceMock);
    }
}
