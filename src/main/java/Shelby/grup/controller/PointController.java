package Shelby.grup.controller;

import Shelby.grup.model.Point;
import Shelby.grup.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points") // URL separada do CollectionPointController
public class PointController {

    @Autowired
    private PointRepository pointRepository;

    @GetMapping
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    @GetMapping("/search")
    public List<Point> searchByName(@RequestParam String name) {
        return pointRepository.findByNameContainingIgnoreCase(name);
    }


    @PostMapping
    public Point createPoint(@RequestBody Point point) {
        Point saved = pointRepository.save(point);
        return pointRepository.save(point);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Point> updatePoint(@PathVariable Long id, @RequestBody Point updatedPoint) {
        return pointRepository.findById(id)
                .map(point -> {
                    point.setName(updatedPoint.getName());
                    point.setAddress(updatedPoint.getAddress());
                    point.setType(updatedPoint.getType());
                    pointRepository.save(point);
                    return ResponseEntity.ok(point);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        if (pointRepository.existsById(id)) {
            pointRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 -> deletado com sucesso
        } else {
            return ResponseEntity.notFound().build(); // 404 -> não encontrado
        }
    }

}