package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController controller;

    @Test
    void processCreationForm() {

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