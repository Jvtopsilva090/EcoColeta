package Shelby.grup.controller;

import Shelby.grup.model.CollectionPoint;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/points")
public class CollectionPointController {

    private final List<CollectionPoint> points = new ArrayList<>();

    public CollectionPointController() {
        points.add(new CollectionPoint("EcoPonto Central", "Rua A, 123", "Plástico"));
        points.add(new CollectionPoint("Coleta Verde", "Av. B, 456", "Papel"));
        points.add(new CollectionPoint("Vidro Recicla", "Praça C, 789", "Vidro"));
    }

    @GetMapping
    public List<CollectionPoint> getAllPoints() {
        return points;
    }

    @GetMapping("/search")
    public List<CollectionPoint> searchPoints(@RequestParam String name) {
        return points.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public CollectionPoint addPoint(@RequestBody CollectionPoint point) {
        points.add(point);
        return point;
    }
}
