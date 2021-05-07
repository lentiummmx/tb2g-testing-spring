package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    @Autowired
    ClinicService clinicService;

    @Autowired
    OwnerController ownerController;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

        given(clinicService.findOwnerByLastName(stringArgCaptor.capture()))
                .willAnswer(invocationOnMock -> {
                    String lastName = invocationOnMock.getArgument(0);
                    List<Owner> results = new ArrayList<>();

                    Owner owner;
                    switch (lastName) {
                        case "":
                        case "Do not find me!":
                            break;
                        case "One Owner":
                            owner = new Owner();
                            owner.setId(1);
                            owner.setFirstName("First Name");
                            owner.setLastName(lastName);
                            results.add(owner);
                            break;
                        case "Many Owners":
                            owner = new Owner();
                            owner.setFirstName("First Name 1");
                            owner.setLastName(lastName);
                            results.add(owner);
                            owner = new Owner();
                            owner.setFirstName("First Name 2");
                            owner.setLastName(lastName);
                            results.add(owner);
                            break;
                        default:
                            throw new RuntimeException("Invalid Argument");
                    }
                    return results;
                });
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void initCreationFormTest() throws Exception {
        mockMvc.perform(get("/owners/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("owner"))
            .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processFindFormNotFoundTest() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Do not find me!"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void processFindFormNotFoundNullTest() throws Exception {
        mockMvc.perform(get("/owners")
                .param("firstName", "Just First Name"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/findOwners"));

        then(clinicService).should().findOwnerByLastName(anyString());
        assertThat("").isEqualToIgnoringCase(stringArgCaptor.getValue());
    }

    @Test
    void processFindFormOwnerTest() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "One Owner"))
            .andExpect(status().is3xxRedirection())
            .andExpect(status().is(HttpStatus.FOUND.value()))
            .andExpect(view().name("redirect:/owners/1"));

        then(clinicService).should().findOwnerByLastName(anyString());
    }

    @Test
    void processFindFormOwnersTest() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Many Owners"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/ownersList"));

        then(clinicService).should().findOwnerByLastName(anyString());
    }

    @Test
    void processCreationFormValidTest() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("address", "1 Some St")
                .param("city", "Some City")
                .param("telephone", "5579461380"))
            .andExpect(status().is3xxRedirection());

        then(clinicService).should().saveOwner(any(Owner.class));
    }

    @Test
    void processCreationFormNotValidTest() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("city", "Some City"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("owner"))
            .andExpect(model().attributeHasFieldErrors("owner", "address"))
            .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
            .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processUpdateOwnerFormValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("address", "1 Some St")
                .param("city", "Some City")
                .param("telephone", "5579461380"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/{ownerId}"));

        then(clinicService).should().saveOwner(any(Owner.class));
    }

    @Test
    void processUpdateOwnerFormNotValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("city", "Some City"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("owner"))
            .andExpect(model().attributeHasFieldErrors("owner", "address"))
            .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
            .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }
}