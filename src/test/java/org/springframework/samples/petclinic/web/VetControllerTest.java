package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;

    @Spy
    Map<String, Object> model;

    @BeforeEach
    void setUp() {
        List<Vet> vets = new ArrayList<>();
        vets.add(new Vet());
        given(clinicService.findVets()).willReturn(vets);
    }

    @Test
    void showVetList() {
        //given
        //Map<String, Object> model = new HashMap<>();

        //when
        String view = vetController.showVetList(model);

        //then
        assertThat("vets/vetList").isEqualTo(view);
        then(clinicService).should(atLeastOnce()).findVets();
        then(model).should(atLeastOnce()).put(anyString(), any());
    }

    @Test
    void showResourcesVetList() {
        //when
        Vets vets = vetController.showResourcesVetList();

        //then
        assertThat(vets.getVetList().size()).isEqualTo(1);
        then(clinicService).should(atLeastOnce()).findVets();
    }
}