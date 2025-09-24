package com.github.jvtopsilva090.ecocoletacli.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointCreateDto;
import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointOutDto;
import com.github.jvtopsilva090.ecocoletacli.repository.EcoColetaApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class EcocoletaCommandLineApp implements CommandLineRunner {

    private final EcoColetaApiRepository ecoColetaApiRepository;

    @Override
    public void run(String... args) throws Exception {
        try (var scanner = new Scanner(System.in)) {
            System.out.println("╔════════════════════════╗");
            System.out.println("║   🌱 EcoColeta CLI     ║");
            System.out.println("╚════════════════════════╝");

            byte option = 0;
            boolean hasRunnedOnce = false;

            do {
                if (hasRunnedOnce) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }

                if (!hasRunnedOnce) hasRunnedOnce = true;

                this.listAllOptions();
                option = scanner.nextByte();
                scanner.nextLine();
                this.executeCommand(option, scanner);
            } while(option > 0);
        }
    }

    private void listAllOptions() {
        System.out.println("\n===== Cliente - Sistema de Pontos de Coleta =====\n");
        System.out.println("1 - Listar todos os pontos");
        System.out.println("2 - Buscar ponto por nome");
        System.out.println("3 - Cadastrar novo ponto");
        System.out.println("4 - Editar ponto existente");
        System.out.println("5 - Deletar ponto");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha uma opção: ");
    }

    private void executeCommand(byte option, final Scanner scanner) throws Exception {
        switch (option) {
            case 1:
                listAllCollectionPoints();
                break;
            case 2:
                getCollectionPointByName(scanner);
                break;
            case 3:
                createCollectionPoint(scanner);
                break;
            case 5:
                deleteCollectionPoint(scanner);
                break;
            case 0:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção não encontrada ou não implementada. Fechando aplicação...");
                System.exit(0);
        }

        pause(scanner);
    }

    private void pause(final Scanner scanner) {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void listAllCollectionPoints() throws Exception {
        final ObjectMapper mapper = new ObjectMapper() {{ enable(SerializationFeature.INDENT_OUTPUT); }};
        final List<CollectionPointOutDto> collectionPointOutDto = this.ecoColetaApiRepository.getAllCollectionPoints();

        System.out.println("\nListando pontos de coletas...");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(collectionPointOutDto));
    }

    private void getCollectionPointByName(final Scanner scanner) throws Exception {
        System.out.println("\nBuscando Ponto de coleta por nome...");
        System.out.print("\nInsira nome do Ponto de coleta: ");
        final String name = scanner.next();
        scanner.nextLine();
        final ObjectMapper mapper = new ObjectMapper() {{ enable(SerializationFeature.INDENT_OUTPUT); }};
        final List<CollectionPointOutDto> collectionPointOutDto = this.ecoColetaApiRepository.getCollectionPointByName(name);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(collectionPointOutDto));
    }

    private void deleteCollectionPoint(final Scanner scanner) {
        System.out.println("\nDeletando Ponto de coleta...");
        System.out.print("\nInsira ID do Ponto de coleta: ");
        final Integer id = scanner.nextInt();
        scanner.nextLine();
        this.ecoColetaApiRepository.deleteCollectionPoint(id);
    }

    private void createCollectionPoint(final Scanner scanner) {
        final String name;
        final String formattedAddress;
        final BigDecimal latitude;
        final BigDecimal longitude;

        System.out.println("\nCriando novo Ponto de coleta...");

        System.out.print("Inserir nome do Ponto de coleta: ");
        name = scanner.nextLine();

        System.out.print("Inserir endereco formatado: ");
        formattedAddress = scanner.nextLine();

        System.out.print("Inserir latitude: ");
        latitude = scanner.nextBigDecimal().setScale(8, RoundingMode.HALF_UP);

        System.out.print("Inserir longitude: ");
        longitude = scanner.nextBigDecimal().setScale(8, RoundingMode.HALF_UP);

        this.ecoColetaApiRepository.createCollectionPoint(new CollectionPointCreateDto(
                name, formattedAddress, latitude, longitude, new ArrayList<>()
        ));
    }
}
