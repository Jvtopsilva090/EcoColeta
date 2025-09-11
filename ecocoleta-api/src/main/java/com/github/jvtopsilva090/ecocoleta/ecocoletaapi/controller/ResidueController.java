package com.github.jvtopsilva090.ecocoleta.ecocoletaapi.controller;

import com.github.jvtopsilva090.ecocoleta.ecocoletaapi.service.ResidueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/residue")
public class ResidueController {

    private final ResidueService residueService;
}
