package com.example.test.controller;

import com.example.test.authorization.model.ERole;
import com.example.test.authorization.model.Role;
import com.example.test.authorization.model.User;
import com.example.test.authorization.pojo.LoginRequest;
import com.example.test.authorization.pojo.SignupRequest;
import com.example.test.authorization.repository.RoleRepo;
import com.example.test.authorization.repository.UserRepo;
import com.example.test.model.House;
import com.example.test.model.Request;
import com.example.test.pojo.FlatRequest;
import com.example.test.pojo.SendRequest;
import com.example.test.repository.FlatRepo;
import com.example.test.repository.RequestRepo;
import com.example.test.service.UserDetailsImpl;
import com.example.test.service.UserDetailsServiceImpl;
import com.example.test.repository.HouseRepo;
import com.example.test.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class MainController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private HouseRepo houseRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    PasswordEncoder encoder;


    @GetMapping
    String getHello(Model model) {
            model.addAttribute("something", "good");
            model.addAttribute("houseList",houseRepo.findAll());
            return "index";
    }


    @GetMapping("/auth/login")
    String signIn(@RequestBody(required = false) LoginRequest loginRequest, Model model) {
        model.addAttribute("username", "Введите почту");
        model.addAttribute("password", "Введите пароль");
        return "auth/login";
    }

    @PostMapping("/auth/dologin")
    String doSignIn(@Valid @RequestBody LoginRequest loginRequest, Model model) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return "redirect:/private/house";
    }

    @GetMapping("/auth/signup")
        String signUp(Model model) {
            model.addAttribute("signUpRequest", new SignupRequest());
            return "auth/signup";
        }

    @PostMapping("/auth/dosignup")
        String registerUser(@RequestParam ("email") String email,
                            @RequestParam ("password") String password,
                            Model model) {
        if (userRepo.existsByUsername(email)) {
            return "auth/signuperror";
        }


        User user = new User(email,
                encoder.encode(password));

        Set<Role> roles = new HashSet<>();

        Role userRole = new Role(ERole.user);
        roles.add(userRole);

        user.setRoles(roles);
        userRepo.save(user);

        return "redirect:/auth/login";
    };



    @GetMapping("/sendrequest")
    String sendRequest( Model model) {
        model.addAttribute("houseList", houseRepo.findAll());
        model.addAttribute("flatList", flatRepo.findAll());
        model.addAttribute("sendRequest", new SendRequest());
        return "addrequest";
    }

    @PostMapping("/sendRequest")
    String recieveRequest(@Valid @RequestParam ("houseId") Long houseId,
                          @RequestParam ("flatNum") String flatNum,
                          @RequestParam("type") String type,
                          @RequestParam("text") String text,
                          @RequestParam("contact") String contact,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        Request newReq = new Request();
        House home= (House) houseRepo.findById(houseId).orElseThrow(() -> new RuntimeException("No data"));
        newReq.setHouse(home);
        newReq.setFlat(flatNum);
        newReq.setText(text);
        newReq.setContact(contact);
        requestRepo.save(newReq);
        redirectAttributes.addFlashAttribute("message", "Ваша заявка №"+newReq.getId()+" была успешно отправлена.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/";
    }
}
