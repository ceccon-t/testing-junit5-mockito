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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void testDeleteByObject() {
        // given
        Speciality speciality = new Speciality();

        // when
        service.delete(speciality);

        // then
        then(specialityRepository).should().delete(any(Speciality.class));
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
        // given - non

        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        then(specialityRepository).should(times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeastOnce() {
        // given - none

        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        then(specialityRepository).should(atLeastOnce()).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeast() {
        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        then(specialityRepository).should(atLeast(2)).deleteById(1l);
//        verify(specialityRepository, atLeast(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        then(specialityRepository).should(atMost(5)).deleteById(1l);
//        verify(specialityRepository, atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        then(specialityRepository).should(atLeastOnce()).deleteById(1l);
        then(specialityRepository).should(never()).deleteById(5l);
//        verify(specialityRepository, atLeastOnce()).deleteById(1l);
//        verify(specialityRepository, never()).deleteById(5l);
    }

    @Test
    void testDelete() {
        // when
        service.delete(new Speciality());

        // then
        then(specialityRepository).should().delete(any());
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialityRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialityRepository.delete(new Speciality()));

        verify(specialityRepository).delete(any());
    }

    @Test
    void testFindByIDThrows() {
        given(specialityRepository.findById(1l)).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.findById(1l));

        then(specialityRepository).should().findById(1l);
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("boom")).given(specialityRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialityRepository.delete(new Speciality()));

        then(specialityRepository).should().delete(any());
    }

}