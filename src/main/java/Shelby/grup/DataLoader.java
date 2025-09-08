package Shelby.grup;

import Shelby.grup.model.Point;
import Shelby.grup.repository.PointRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PointRepository pointRepository;

    public DataLoader(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pointRepository.count() == 0) { // só insere se estiver vazio
            pointRepository.save(new Point("EcoPonto Central", "Rua A, 123", "Plástico"));
            pointRepository.save(new Point("Coleta Verde", "Av. B, 456", "Papel"));
            pointRepository.save(new Point("Vidro Recicla", "Praça C, 789", "Vidro"));
            System.out.println("Pontos iniciais carregados no banco!");
        }
    }
}
