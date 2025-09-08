package Shelby.grup.service;

import Shelby.grup.model.Point;
import Shelby.grup.repository.PointRepository;
import Shelby.grup.exception.PointNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    public Point updatePoint(String oldName, String newName, String newAddress, String newType) {
        Optional<Point> optionalPoint = pointRepository.findByName(oldName);
        if (optionalPoint.isEmpty()) {
            throw new PointNotFoundException("Ponto com nome '" + oldName + "' não encontrado.");
        }

        Point point = optionalPoint.get();
        point.setName(newName);
        point.setAddress(newAddress);
        point.setType(newType);

        return pointRepository.save(point);
    }
}