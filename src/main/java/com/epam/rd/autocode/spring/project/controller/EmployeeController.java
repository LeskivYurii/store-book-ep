package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.CreateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest;
import com.epam.rd.autocode.spring.project.security.UserDetailsAdapter;
import com.epam.rd.autocode.spring.project.security.service.AuthService;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private static final String EMPLOYEE_ATTRIBUTE = "employee";

    private final EmployeeService employeeService;
    private final UserOldPasswordValidation userOldPasswordValidation;
    private final AuthService authService;

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String getAllEmployee(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees(pageable));
        return "employee/employee-list";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{email}")
    public String getEmployeeByEmail(@PathVariable String email, Model model) {
        model.addAttribute(EMPLOYEE_ATTRIBUTE, employeeService.getEmployeeByEmail(email));
        return  "employee/employee-details";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{email}/edit-page")
    public String getEditPage(@PathVariable String email, Model model) {
        model.addAttribute(EMPLOYEE_ATTRIBUTE, new UpdateEmployeeRequest(employeeService.getEmployeeByEmail(email)));
        return "employee/employee-edit";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{email}")
    public String updateEmployee(@PathVariable(name = "email") String email, @ModelAttribute(name = EMPLOYEE_ATTRIBUTE)
    @Valid UpdateEmployeeRequest employeeDTO, BindingResult bindingResult) {
        userOldPasswordValidation.validate(employeeDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            return "employee/employee-edit";
        }
        employeeService.updateEmployeeByEmail(email, employeeDTO);
        return "redirect:/employee/" + employeeDTO.getEmail();
    }

    @PreAuthorize("hasRole('EMPLOYEE') or isAnonymous()")
    @GetMapping("/create-page")
    public String getCreatePage(@ModelAttribute(name = EMPLOYEE_ATTRIBUTE) CreateEmployeeRequest employeeDTO) {
        return "employee/employee-create";
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE') or isAnonymous()")
    public String createEmployee(@ModelAttribute(name = EMPLOYEE_ATTRIBUTE) @Valid CreateEmployeeRequest employeeDTO,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "employee/employee-create";
        }
        employeeService.addEmployee(employeeDTO);
        return "redirect:/auth/login-page";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/{email}")
    public String deleteEmployeeByEmail(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter,
                                        @PathVariable String email, HttpServletResponse httpResponse) {
        employeeService.deleteEmployeeByEmail(email);
        if(userDetailsAdapter.getUsername().equals(email)) {
            httpResponse.addCookie(authService.logout());
        }
        return "redirect:/auth/login-page";
    }

}
