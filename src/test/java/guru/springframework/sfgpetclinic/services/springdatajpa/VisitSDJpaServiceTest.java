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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {

        Visit visitA = new Visit();
        Visit visitB = new Visit();
        Visit visitC = new Visit();

        List<Visit> allVisits = new ArrayList<>();
        allVisits.add(visitA);
        allVisits.add(visitB);
        allVisits.add(visitC);

        when(visitRepository.findAll()).thenReturn(allVisits);

        Set<Visit> foundVisits = service.findAll();

        assertEquals(allVisits.size(), foundVisits.size());
    }

    @Test
    void findById() {
        Visit visit = new Visit();
        visit.setId(1l);

        when(visitRepository.findById(visit.getId())).thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(visit.getId());

        assertThat(foundVisit).isNotNull();
        assertEquals(visit.getId(), foundVisit.getId());

    }

    @Test
    void save() {
        Visit visit = new Visit();
        visit.setDescription("A visit");

        when(visitRepository.save(visit)).thenReturn(visit);

        Visit persistedVisit = service.save(visit);

        assertEquals(visit.getDescription(), persistedVisit.getDescription());
        verify(visitRepository, atLeastOnce()).save(visit);
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        visit.setDescription("A visit");

        service.delete(visit);

        verify(visitRepository, times(1)).delete(visit);

    }

    @Test
    void deleteById() {
        Visit visit = new Visit();
        visit.setId(1l);

        service.deleteById(visit.getId());

        verify(visitRepository, times(1)).deleteById(visit.getId());

    }
}