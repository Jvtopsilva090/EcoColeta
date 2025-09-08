package Shelby.grup.repository;

import Shelby.grup.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByName(String name);

    List<Point> findByNameContainingIgnoreCase(String name);
}
