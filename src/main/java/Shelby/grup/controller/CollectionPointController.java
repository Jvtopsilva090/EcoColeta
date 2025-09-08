package Shelby.grup.controller;

import Shelby.grup.model.CollectionPoint;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collection-points")
public class CollectionPointController {

    private final List<CollectionPoint> points = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public CollectionPointController() {
        points.add(new CollectionPoint(idGenerator.getAndIncrement(), "EcoPonto Central", "Rua A, 123", "Plástico"));
        points.add(new CollectionPoint(idGenerator.getAndIncrement(), "Coleta Verde", "Av. B, 456", "Papel"));
        points.add(new CollectionPoint(idGenerator.getAndIncrement(), "Vidro Recicla", "Praça C, 789", "Vidro"));
        points.add(new CollectionPoint(idGenerator.getAndIncrement(), "Eco Parque", "Rua sei la de 777", "Vidro"));
        points.add(new CollectionPoint(idGenerator.getAndIncrement(), "top ciclico", "rua sei la da esquina 777", "Plástico"));
        points.add(new CollectionPoint(idGenerator.getAndIncrement(), "ali cicliquico", "Rua sei la perto da rua la 444", "Plástico"));
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
        point.setId(idGenerator.getAndIncrement());
        points.add(point);
        return point;
    }

    @PutMapping("/{id}")
    public CollectionPoint updatePoint(@PathVariable Long id, @RequestBody CollectionPoint updatedPoint) {
        for (CollectionPoint p : points) {
            if (p.getId().equals(id)) {
                p.setName(updatedPoint.getName());
                p.setAddress(updatedPoint.getAddress());
                p.setType(updatedPoint.getType());
                return p;
            }
        }
        throw new RuntimeException("Ponto não encontrado com ID: " + id);
    }
}