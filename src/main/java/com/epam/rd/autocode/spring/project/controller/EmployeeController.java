package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private final EmployeeService employeeService;

    @GetMapping
    public String getAllEmployee(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees(pageable));
        return "/employee/employee-list";
    }

    @GetMapping("/{email}")
    public String getEmployeeByEmail(@PathVariable String email, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeByEmail(email));
        return  "/employee/employee-details";
    }

    @GetMapping("/{email}/edit-page")
    public String getEditPage(@PathVariable String email, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeByEmail(email));
        return "/employee/employee-edit";
    }

    @PutMapping("/{email}")
    public String updateEmployee(@PathVariable(name = "email") String email, @ModelAttribute(name = "employee") @Valid
    EmployeeDTO employeeDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeService.getEmployeeByEmail(email));
            return "/employee/employee-edit";
        }
        employeeService.updateEmployeeByEmail(email, employeeDTO);
        return "redirect:/employee/" + employeeDTO.getEmail();
    }

    @GetMapping("/create-page")
    public String getCreatePage(@ModelAttribute(name = "employee") EmployeeDTO employeeDTO) {
        return "/employee/employee-create";
    }

    @PostMapping
    public String createEmployee(@ModelAttribute(name = "employee") @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/employee/employee-create";
        }
        employeeService.addEmployee(employeeDTO);
        return "redirect:/employee/" +  employeeDTO.getEmail();
    }

    @DeleteMapping("/{email}")
    public String deleteEmployeeByEmail(@PathVariable String email) {
        employeeService.deleteEmployeeByEmail(email);
        return "redirect:/employees";
    }

}
