package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy //@Mock
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12l);
        Pet pet3 = new Pet(3l);
        petService.save(pet);
        petService.save(pet3);
        given(petService.findById(anyLong())).willCallRealMethod(); //.willReturn(pet);

        // when
        Visit visit = visitController.loadPetWithVisit(12l, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(12l);
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitStubbin() {
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12l);
        Pet pet3 = new Pet(3l);

        petService.save(pet);
        petService.save(pet3);

        given(petService.findById(anyLong())).willReturn(pet3);

        // when
        Visit visit = visitController.loadPetWithVisit(3l, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(3l);
        verify(petService, times(1)).findById(anyLong());
    }
}