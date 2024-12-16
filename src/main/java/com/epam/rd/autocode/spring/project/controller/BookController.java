package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import com.epam.rd.autocode.spring.project.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String getAllBook(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("books", bookService.getAllBooks(pageable));
        return "/book/book-list";
    }

    @GetMapping("/{name}")
    public String findBookByName(@PathVariable String name, Model model) {
     model.addAttribute("book", bookService.getBookByName(name));
     return "/book/book-details";
    }

    @DeleteMapping("/{name}")
    public String deleteBook(@PathVariable String name) {
        bookService.deleteBookByName(name);
        return "redirect:/books";
    }

    @GetMapping("/{name}/edit")
    public String getEditPage(@PathVariable String name, Model model) {
        model.addAttribute("book", bookService.getBookByName(name));
        model.addAttribute("ageGroups", AgeGroup.values());
        model.addAttribute("languages", Language.values());
        return "/book/book-edit";
    }

    @PutMapping("/{name}")
    public String updateBook(@PathVariable String name, @ModelAttribute(name = "book") @Valid BookDTO book, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("ageGroups", AgeGroup.values());
            model.addAttribute("languages", Language.values());
            return "/book/book-edit";
        }
        bookService.updateBookByName(name, book);
        return "redirect:/books/" + name;
    }

    @GetMapping("/create-page")
    public String getCreatePage(@ModelAttribute(name = "book") BookDTO bookDTO) {
        return "/book/book-create";
    }

    @PostMapping
    public String createBook(@ModelAttribute(name = "book") @Valid BookDTO book, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("ageGroups", AgeGroup.values());
            model.addAttribute("languages", Language.values());
            return "/book/book-create";
        }
        bookService.addBook(book);
        return "redirect:/books/" + book.getName();
    }

}
