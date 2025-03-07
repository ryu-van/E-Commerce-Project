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
    private String getListUserRoleAdminAndStaff(Model model,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "fullname") String sortField,@RequestParam(defaultValue = "asc") String sortDir, @RequestParam(defaultValue = "") String keyword)
    {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();
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
    public String getNewStaffPage(Model model) {
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createStaff(@Valid @ModelAttribute("user") User user,BindingResult bindingResult ,Model model, @RequestParam(value = "fileAvatar", required = false) MultipartFile file) {
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
        model.addAttribute("roleList", roles);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                             Model model, @RequestParam(value = "fileAvatar", required = false) MultipartFile file) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("roleList", roles);
            return "admin/user/update";
        }
        User existingUser = userService.getUserById(user.getId());
        if (existingUser == null) {
            return "redirect:/admin/users";
        }
        existingUser.setFullname(user.getFullname());
        existingUser.setGender(user.getGender());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        existingUser.setBirth(user.getBirth());
        existingUser.setRoles(user.getRoles());
        if (file != null && !file.isEmpty()) {
            String avatar = uploadService.handleUploadFile(file, "avatars");
            existingUser.setAvatar(avatar);
        }
        userService.updateUser(existingUser);
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

    @GetMapping("/admin/clients")
    public String getClientPage(Model model,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "fullname") String sortField,@RequestParam(defaultValue = "asc") String sortDir,@RequestParam(defaultValue = "") String keyword) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> page = userService.getClientPages(keyword,pageable);
        List<User> listClient = page.getContent();
        model.addAttribute("clients", listClient);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", pageNo);
        return "admin/client/show";
    }
    @GetMapping("/admin/client/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin/clients";
    }
    @GetMapping("/admin/client/detail/{id}")
    public String getClientDetailPage(@PathVariable("id") Long id, Model model) {
        User client = userService.getUserById(id);
        model.addAttribute("client", client);
        return "admin/client/detail";
    }
    @GetMapping("/admin/client/create")
    public String getCreateClientPage(Model model) {
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("client", new User());
        model.addAttribute("roleList", roles);
        return "admin/client/create";
    }
    @PostMapping("/admin/client/create")
    public String createClientPage(@Valid @ModelAttribute("client") User client, BindingResult bindingResult, Model model, @RequestParam(value = "fileAvatar", required = false) MultipartFile fileAvatar) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("roleList", roles);
            return "admin/client/create";
        }

        if (fileAvatar != null && !fileAvatar.isEmpty()) {
            String avatar = uploadService.handleUploadFile(fileAvatar, "avatars");
            client.setAvatar(avatar);
        } else {
            client.setAvatar("default-avatar.jpg");
        }
        String hashPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(hashPassword);
        userService.createUser(client);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/client/update/{id}")
    public String getUpdateClientPage(@PathVariable("id") Long id, Model model) {
        User client = userService.getUserById(id);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("client", client);
        model.addAttribute("roleList", roles);
        return "admin/client/update";
    }
    @PostMapping("/admin/client/update")
    public String updateClient(@Valid @ModelAttribute("user") User client, BindingResult bindingResult,
                             Model model, @RequestParam(value = "fileAvatar", required = false) MultipartFile file) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("roleList", roles);
            return "admin/client/update";
        }
        User existingClient = userService.getUserById(client.getId());
        if (existingClient == null) {
            return "redirect:/admin/users";
        }
        existingClient.setFullname(client.getFullname());
        existingClient.setGender(client.getGender());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setAddress(client.getAddress());
        existingClient.setBirth(client.getBirth());
        existingClient.setRoles(client.getRoles());
        if (file != null && !file.isEmpty()) {
            String avatar = uploadService.handleUploadFile(file, "avatars");
            existingClient.setAvatar(avatar);
        }
        userService.updateUser(client);
        return "redirect:/admin/users";
    }





}
