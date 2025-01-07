package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.security.UserDetailsAdapter;
import com.epam.rd.autocode.spring.project.security.service.AuthService;
import com.epam.rd.autocode.spring.project.service.ClientService;
import com.epam.rd.autocode.spring.project.validation.validator.UserOldPasswordValidation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private static final String CLIENT_ATTRIBUTE = "client";

    private final ClientService clientService;
    private final AuthService authService;
    private final UserOldPasswordValidation userOldPasswordValidation;

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String getAllClient(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("clients", clientService.getAllClients(pageable));
        return "client/client-list";
    }

    @PreAuthorize("hasRole('EMPLOYEE') or @authExpressions.isUserAllowed(#email)")
    @GetMapping("/{email}")
    public String getClientByEmail(@PathVariable String email, Model model) {
        model.addAttribute(CLIENT_ATTRIBUTE, clientService.getClientByEmail(email));
        return "client/client-details";
    }

    @PreAuthorize("hasRole('EMPLOYEE') or @authExpressions.isUserAllowed(#email)")
    @GetMapping("/{email}/edit-page")
    public String getEditPage(@PathVariable String email, Model model) {
        model.addAttribute(CLIENT_ATTRIBUTE, new UpdateClientRequest(clientService.getClientByEmail(email)));
        return "client/client-edit";
    }

    @PreAuthorize("hasRole('EMPLOYEE') or isAnonymous()")
    @GetMapping("/create-page")
    public String getCreatePage(@ModelAttribute(name = CLIENT_ATTRIBUTE) CreateClientRequest clientDTO) {
        return "client/client-create";
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE') or isAnonymous()")
    public String createClient(@ModelAttribute(name = CLIENT_ATTRIBUTE) @Valid CreateClientRequest clientDTO,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "client/client-create";
        }
        clientService.addClient(clientDTO);
        return "redirect:/auth/login-page";
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasRole('EMPLOYEE') or @authExpressions.isUserAllowed(#email)")
    public String updateClient(@PathVariable String email, @ModelAttribute(name = CLIENT_ATTRIBUTE) @Valid
    UpdateClientRequest clientDTO, BindingResult bindingResult) {
        userOldPasswordValidation.validate(clientDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            return "client/client-edit";
        }
        clientService.updateClientByEmail(email, clientDTO);
        return "redirect:/clients/" + clientDTO.getEmail();
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('EMPLOYEE') or @authExpressions.isUserAllowed(#email)")
    public String deleteClientByEmail(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter,
                                      @PathVariable String email, HttpServletResponse httpResponse) {
        clientService.deleteClientByEmail(email);
        if(userDetailsAdapter.getUsername().equals(email)) {
            httpResponse.addCookie(authService.logout());
        }
        return "redirect:/clients";
    }

    @PatchMapping("/{email}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String blockUnblockClient(@PathVariable String email) {
        clientService.blockUnblockClient(email);

        return "redirect:/clients";
    }


}
