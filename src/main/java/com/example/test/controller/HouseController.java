package com.example.test.controller;

import com.example.test.service.HouseServiceImpl;
import com.example.test.model.Address;
import com.example.test.model.House;
import com.example.test.service.AddressServiceImpl;
import com.example.test.pojo.HouseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/private/house")
public class HouseController {

    @Autowired
    private HouseServiceImpl houseService;
    @Autowired
     private AddressServiceImpl addressRepo;

    @GetMapping
    public String start( Model model) {
        model.addAttribute("houseList",houseService.getAll());
        model.addAttribute("houseEditPath",houseService.getAll());
        return "private/house/index";
    }

    @GetMapping(value = "/add")
    public String addHouse( Model model) {
        model.addAttribute("houseRequest", new HouseRequest());
        return "private/house/addhouse";
    }

    @GetMapping ("/{id}")
    public String updateHouse(Model model, @PathVariable("id") long id) {
        model.addAttribute("houseRequest", (House) houseService.getById(id));
        return "private/house/editHouseForm";
    }

    @PostMapping("/commit")
    public String editPost(@Valid @RequestParam ("id") Long id,
                           @RequestParam ("city") String city,
                           @RequestParam("street") String street,
                           @RequestParam("num") String num,
                           @RequestParam("type") String type,
                           @RequestParam("square") Double square, Model model) {

        House newHome = (House) houseService.getById(id);
        newHome.setCity(city);
        newHome.setStreet(street);
        newHome.setNum(num);
        newHome.setType(type);
        newHome.setSquare(square);
        houseService.save(newHome);
        return "redirect:/private/house";
    }


    @PostMapping("/save")
    @ResponseBody
    public String saveHouse (@Valid @RequestParam ("city") String city,
                      @RequestParam("street") String street,
                      @RequestParam("num") String num,
                      @RequestParam("type") String type,
                      @RequestParam("square") Double square,
                      Model model) {

        Address newAd = new Address(city,street);
        addressRepo.save(newAd);
        House newHome = new House( newAd, num);
        newHome.setType(type);
        newHome.setSquare(square);
        houseService.save(newHome);
        return "redirect:/private/house";
    }

    @GetMapping("/delete/{id}")
    public String deleteHouse(@PathVariable("id") long id, Model model) {
        House house = (House) houseService.getById(id);
        if (house == null ) {
            model.addAttribute("errorMessage","Ошибка при удалении дома, дом не найден");
            return "index";
        }
        houseService.deleteById(id);
        return "redirect:/private/house";
    }
}
