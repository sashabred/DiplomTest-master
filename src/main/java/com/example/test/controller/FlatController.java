package com.example.test.controller;

import com.example.test.model.Flat;
import com.example.test.model.House;
import com.example.test.service.FlatServiceImpl;
import com.example.test.service.HouseServiceImpl;
import com.example.test.pojo.FlatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/private/flat")
public class FlatController {
    @Autowired
    private HouseServiceImpl houseService;

    @Autowired
    private FlatServiceImpl flatService;


    @GetMapping
    public String getFlatList( Model model) {
        model.addAttribute("houseList", houseService.getAll());
        model.addAttribute("flatList", flatService.getAll());
        model.addAttribute("flatRequest", new FlatRequest());
        return "private/flat/flatList";
    }

    @GetMapping(value = "/add")
    public String addFlat( Model model) {

        model.addAttribute("flatRequest", new FlatRequest());
        model.addAttribute("houseList", houseService.getAll());
        return "private/flat/addflat";
    }

    @PostMapping(value = "/save")
    public String saveFlat ( @Valid @RequestParam("houseId") Long houseId,
                            @RequestParam ("num") String num,
                            @RequestParam("type") String type,
                             Model model) {

        House fHouse = (House) houseService.getById(houseId);
        Flat newFlat = new Flat(num, type, fHouse);
        flatService.save(newFlat);
        return "redirect:/private/flat";
    }

    @RequestMapping(value = "/deleteflats", method = RequestMethod.GET)
    public String deleteFlats(@RequestParam("fid") Long[] flatListId)
    {
        flatService.deleteFlatList(flatListId);
        return "redirect:/private/flat";
    }
}
