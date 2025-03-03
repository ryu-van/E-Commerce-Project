package org.example.ecommerceproject.controller.admin;


import jakarta.validation.Valid;
import org.example.ecommerceproject.model.Role;
import org.example.ecommerceproject.model.User;
import org.example.ecommerceproject.service.RoleService;
import org.example.ecommerceproject.service.UploadService;
import org.example.ecommerceproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;


    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, UploadService uploadService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/users")
    private String getListUser(Model model,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "fullname") String sortField,@RequestParam(defaultValue = "asc") String sortDir, @RequestParam(defaultValue = "") String keyword)
    {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField);
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> page = userService.getUsersPage(keyword,pageable);
        List<User> listUser = page.getContent();
        model.addAttribute("users", listUser);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", pageNo);
        return "admin/user/show";
    }


    @GetMapping("/admin/user/create")
    public String getNewUserPage(Model model) {
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult ,Model model, @RequestParam(value = "fileAvatar", required = false) MultipartFile file) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("roles", roles);
            return "admin/user/create";
        }
        if (file != null && !file.isEmpty()) {
            String avatar = uploadService.handleUploadFile(file, "avatars");
            user.setAvatar(avatar);
        } else {
            user.setAvatar("default-avatar.jpg");
        }
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/delete/{id}")
    private String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult ,Model model,@RequestParam(value = "fileAvatar", required = false) MultipartFile file) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("user", userService.getUserById(user.getId()));
            model.addAttribute("roles", roles);
            return "admin/user/update";
        }
        if (file != null && !file.isEmpty()) {
            String avatar = uploadService.handleUploadFile(file, "avatars");
            user.setAvatar(avatar);
        } else {
            user.setAvatar("default-avatar.jpg");
        }
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/detail/{id}")
    public String getUserDetailPage(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/user/detail";
    }
}
