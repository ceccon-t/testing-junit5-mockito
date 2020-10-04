package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private static final String BASE_REDIRECT_CREATION_FORM = "redirect:/owners/";

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController controller;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormWildcardString() {
        // given
        Owner owner = new Owner(5l, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat(captor.getValue()).isEqualToIgnoringCase("%Buck%");
    }

    @Test
    void processFindFormWildcardStringCaptorFromAnnotation() {
        // given
        Owner owner = new Owner(5l, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
    }

    @Test
    void processCreationForm() {
        // given
        Long ownerId = 5l;
        Owner owner = new Owner(ownerId, "John", "Doe");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        // when
        String route = controller.processCreationForm(owner, bindingResult);

        // then
        then(ownerService).should(atLeastOnce()).save(any(Owner.class));
        assertEquals(BASE_REDIRECT_CREATION_FORM + ownerId, route);

    }

    @Test
    void processCreationFormWithErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(true);
        Owner owner = new Owner(1l, "John", "Doe");

        // when
        String route = controller.processCreationForm(owner, bindingResult);

        // then
        then(ownerService).shouldHaveZeroInteractions();
        assertEquals(VIEWS_OWNER_CREATE_OR_UPDATE_FORM, route);
    }


}