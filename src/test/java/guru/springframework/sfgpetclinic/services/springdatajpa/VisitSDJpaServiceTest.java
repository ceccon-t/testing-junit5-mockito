package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {

        // given
        Visit visitA = new Visit();
        Visit visitB = new Visit();
        Visit visitC = new Visit();

        List<Visit> allVisits = new ArrayList<>();
        allVisits.add(visitA);
        allVisits.add(visitB);
        allVisits.add(visitC);

//        when(visitRepository.findAll()).thenReturn(allVisits);
        given(visitRepository.findAll()).willReturn(allVisits);

        // when
        Set<Visit> foundVisits = service.findAll();

        // then
        assertEquals(allVisits.size(), foundVisits.size());
        then(visitRepository).should().findAll();
    }

    @Test
    void findById() {
        // given
        Visit visit = new Visit();
        visit.setId(1l);

        given(visitRepository.findById(visit.getId())).willReturn(Optional.of(visit));

        // when
        Visit foundVisit = service.findById(visit.getId());

        // then
        assertThat(foundVisit).isNotNull();
        assertEquals(visit.getId(), foundVisit.getId());
        then(visitRepository).should().findById(visit.getId());

    }

    @Test
    void save() {
        // given
        Visit visit = new Visit();
        visit.setDescription("A visit");

        given(visitRepository.save(visit)).willReturn(visit);

        // when
        Visit persistedVisit = service.save(visit);

        // then
        assertEquals(visit.getDescription(), persistedVisit.getDescription());
        then(visitRepository).should(atLeastOnce()).save(visit);
    }

    @Test
    void delete() {
        // given
        Visit visit = new Visit();
        visit.setDescription("A visit");

        // when
        service.delete(visit);

        // then
        then(visitRepository).should(times(1)).delete(visit);

    }

    @Test
    void deleteById() {
        // given
        Visit visit = new Visit();
        visit.setId(1l);

        // when
        service.deleteById(visit.getId());

        // then
        then(visitRepository).should(times(1)).deleteById(visit.getId());

    }
}