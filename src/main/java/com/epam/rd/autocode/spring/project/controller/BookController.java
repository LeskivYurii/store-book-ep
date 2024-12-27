package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    public static final String AGE_GROUP_ATTRIBUTE = "ageGroups";
    public static final String LANGUAGES_ATTRIBUTE = "languages";
    private final BookService bookService;

    @GetMapping
    public String getAllBook(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("books", bookService.getAllBooks(pageable));
        return "/book/book-list";
    }

    @GetMapping("/{id}")
    public String findBookById(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "/book/book-details";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit-page")
    public String getEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute(AGE_GROUP_ATTRIBUTE, AgeGroup.values());
        model.addAttribute(LANGUAGES_ATTRIBUTE, Language.values());
        return "/book/book-edit";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute(name = "book") @Valid ModifyBookRequest book, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute(AGE_GROUP_ATTRIBUTE, AgeGroup.values());
            model.addAttribute(LANGUAGES_ATTRIBUTE, Language.values());
            return "/book/book-edit";
        }
        bookService.updateBookById(id, book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/create-page")
    public String getCreatePage(@ModelAttribute(name = "book") ModifyBookRequest modifyBookRequest, Model model) {
        model.addAttribute(AGE_GROUP_ATTRIBUTE, AgeGroup.values());
        model.addAttribute(LANGUAGES_ATTRIBUTE, Language.values());
        return "/book/book-create";
    }

    @PostMapping
    public String createBook(@ModelAttribute(name = "book") @Valid ModifyBookRequest book, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute(AGE_GROUP_ATTRIBUTE, AgeGroup.values());
            model.addAttribute(LANGUAGES_ATTRIBUTE, Language.values());
            return "/book/book-create";
        }
        bookService.addBook(book);
        return "redirect:/books/" + book.getName();
    }

}
