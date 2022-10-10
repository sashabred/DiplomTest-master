package com.example.test.controller;

import com.example.test.model.Request;
import com.example.test.repository.RequestRepo;
import com.example.test.service.AddressServiceImpl;
import com.example.test.service.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/private/request")
public class RequestController {
    @Autowired
    private HouseServiceImpl houseService;
    @Autowired
    private AddressServiceImpl addressRepo;

    @Autowired
    private RequestRepo requestRepo;

    @GetMapping
    public String getRequests( Model model) {
        model.addAttribute("requestList",requestRepo.findAll());
        return "private/requests/requests";
    }

    @GetMapping("/switch_state_done/{id}")
    public String setDoneRequest (@PathVariable("id") long id, @RequestParam("doneState") Boolean done, Model model) {
        Request newReq = requestRepo.findById(id).orElseThrow(() -> new RuntimeException("Unavailable"));

        newReq.setDoneRequest(done);
        requestRepo.save(newReq);
        return "redirect:/private/request";
    }

}
