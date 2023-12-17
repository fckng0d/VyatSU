package ru.fckng0d.boot.loan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fckng0d.boot.loan.entities.User;
import ru.fckng0d.boot.loan.services.ClientService;
import ru.fckng0d.boot.loan.services.UserService;

@Controller
public class AuthController {
    private final UserService userService;
    private final ClientService clientService;

    @Autowired
    public AuthController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "auth/hello";
    }

    /*@PostMapping("/login")
    public String login()
    {
        return "redirect:/hello?denied=true";
    }*/

    @GetMapping("/logout")
    public String logout()
    {
        return "redirect:/hello";
    }

//    @PostMapping("login")
//    public String auth(@RequestParam("username") String username,
//                       @RequestParam("password") String password, Model model,
//                       RedirectAttributes redirectAttributes) {
//        User user = userService.getUserByUserName(username);
////        System.out.println("{noop}" + password);
//        if (user != null) {
////            System.out.println(userService.getPasswordByUserName(username));
////            System.out.println(userService.getRoleByUsername(username));
//            if (("{noop}" + password).equals(userService.getPasswordByUserName(username))) {
////                model.addAttribute("username", username);
//                if (userService.getRoleByUsername(username).equals("ROLE_USER")) {
//                    Long clientId = userService.clientIdByUserName(username);
//                    redirectAttributes.addAttribute("id", clientId);
////                    System.out.println("ДА, ЮЗЕР");
//                    return "redirect:/clients/{id}";
//                } /*else if (userService.getRoleByUsername(username).equals("ROLE_ADMIN")) {
////                    System.out.println("ДА, АДМИН");
//                    return "redirect:/clients";
//                }*/
//            }
//        }
//
//        System.out.println("РЕДИРЕКТ");
//
//        return "auth/hello";
//    }
}
