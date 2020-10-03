package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void testDeleteByObject() {
        Speciality speciality = new Speciality();

        service.delete(speciality);

        verify(specialityRepository).delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();

        when(specialityRepository.findById(1l)).thenReturn(Optional.of(speciality));

        Speciality foundSpecialty = service.findById(1l);

        assertThat(foundSpecialty).isNotNull();
        verify(specialityRepository).findById(1l);
    }

    @Test
    void findByIdBddTest() {
        Speciality speciality = new Speciality();

        given(specialityRepository.findById(1l)).willReturn(Optional.of(speciality));

        Speciality foundSpecialty = service.findById(1l);

        assertThat(foundSpecialty).isNotNull();

        then(specialityRepository).should().findById(anyLong());
        then(specialityRepository).shouldHaveNoMoreInteractions();

        // Alternative:
        //then(specialtyRepository).should(times(1)).findById(anyLong());

    }

    @Test
    void deleteById() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialityRepository, times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeastOnce() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialityRepository, atLeastOnce()).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeast() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialityRepository, atLeast(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialityRepository, atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialityRepository, atLeastOnce()).deleteById(1l);
        verify(specialityRepository, never()).deleteById(5l);
    }

    @Test
    void testDelete() {
        service.delete(new Speciality());
    }
}