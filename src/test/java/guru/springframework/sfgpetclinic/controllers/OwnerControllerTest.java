package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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

    @BeforeEach
    void setup() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();

                    String name = invocation.getArgument(0);

                    if (name.equals("%Buck%"))  {
                        owners.add(new Owner(1l, "Joe", "Buck"));
                        return owners;
                    } else if (name.equals("%DontFindMe%")) {
                        return owners;
                    } else if (name.equals("%FindMe%")) {
                        owners.add(new Owner(1l, "Joe", "FindMe1"));
                        owners.add(new Owner(2l, "Jim", "2FindMe"));
                        return owners;
                    }

                    throw new RuntimeException("Invalid argument");
                });
    }

    @Test
    void processFindFormWildcardStringCaptorFromAnnotation() {
        // given
        Owner owner = new Owner(1l, "Joe", "Buck");

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
        assertThat(viewName).isEqualToIgnoringCase("redirect:/owners/1");
    }

    @Test
    void processFindFormWildcardStringCaptorFromAnnotationNotFound() {
        // given
        Owner owner = new Owner(1l, "Joe", "DontFindMe");

        // when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%DontFindMe%");
        assertThat(viewName).isEqualToIgnoringCase("owners/findOwners");
    }

    @Test
    void processFindFormWildcardStringCaptorFromAnnotationFoundMany() {
        // given
        Owner owner = new Owner(1l, "Joe", "FindMe");

        // when
        String viewName = controller.processFindForm(owner, bindingResult, Mockito.mock(Model.class));

        // then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%FindMe%");
        assertThat(viewName).isEqualToIgnoringCase("owners/ownersList");
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