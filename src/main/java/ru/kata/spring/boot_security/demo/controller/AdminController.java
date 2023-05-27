package ru.kata.spring.boot_security.demo.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/admin")
    public String showAllUser(Model model) {
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("allRoles", roleService.getAllRole());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "adminPanel";
    }

    @GetMapping("/admin/user/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "userPage";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("userNew", new User());
        model.addAttribute("allRoles", roleService.getAllRole());
        return "adminPanel";
    }

    @PostMapping("/admin/user")
    public String addUser(@ModelAttribute("messages") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/admin/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", roleService.getAllRole());
        return "adminPanel";
    }

    @PatchMapping("/admin/user/{id}")
    public String updateUser(@PathVariable("id") Long id, ModelMap model) {
        User messages = userService.findUserById(id);
        model.addAttribute("messages", messages);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("roles", roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
