package com.group9.sdmfas.controller;

import com.group9.sdmfas.model.Lga;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lgas")
public class LgaController {

    @GetMapping
    public Lga[] getLgas() {
        return Lga.values();
    }
}
