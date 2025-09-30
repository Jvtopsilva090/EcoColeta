package com.github.jvtopsilva090.ecocoletacli.runner;

import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointCreateDto;
import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointEditDto;
import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointOutDto;
import com.github.jvtopsilva090.ecocoletacli.repository.EcoColetaApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        System.out.println("1 - Listar Todos os Pontos de Coleta");
        System.out.println("2 - Buscar Ponto de Coleta por ID");
        System.out.println("3 - Cadastrar Novo Ponto de Coleta");
        System.out.println("4 - Editar Ponto de Coleta");
        System.out.println("5 - Deletar Ponto de Coleta por ID");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha uma opção: ");
    }

    private void executeCommand(byte option, final Scanner scanner) throws Exception {
        switch (option) {
            case 1:
                listAllCollectionPoints();
                break;
            case 2:
                listCollectionPointById(scanner);
                break;
            case 3:
                createCollectionPoint(scanner);
                break;
            case 4:
                updateCollectionPoint(scanner);
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
        final List<CollectionPointOutDto> collectionPointOutDto = this.ecoColetaApiRepository.getAllCollectionPoints();

        System.out.println("\nListando Pontos de Coletas...\n");
        collectionPointOutDto.forEach(System.out::println);
    }

    private void listCollectionPointById(final Scanner scanner) {
        System.out.println("\nListando Ponto de Coleta por ID...\n");
        System.out.print("Insira ID do Ponto de Coleta: ");
        final Integer id = scanner.nextInt();
        scanner.nextLine();

        final CollectionPointOutDto collectionPointOutDto;

        try {
            collectionPointOutDto = this.ecoColetaApiRepository.getCollectionPointById(id);
            System.out.println("\n".concat(collectionPointOutDto.toString()));
        } catch (Exception e) {
            System.err.println("\nErro ao Buscar Ponto de Coleta por ID. Informe o ID de um Ponto de Coleta Existente!");
        } finally {
            scanner.nextLine();
        }
    }

    private void deleteCollectionPoint(final Scanner scanner) {
        System.out.println("\nDeletando Ponto de Coleta...\n");
        System.out.print("Insira ID do Ponto de Coleta: ");
        final Integer id = scanner.nextInt();
        scanner.nextLine();
        this.ecoColetaApiRepository.deleteCollectionPoint(id);
        System.out.println("\nPonto de Coleta Deletado com Sucesso!!");
    }

    private void createCollectionPoint(final Scanner scanner) {
        final String name;
        final String formattedAddress;
        final BigDecimal latitude;
        final BigDecimal longitude;

        System.out.println("\nCriando Novo Ponto de Coleta...\n");

        System.out.print("Inserir Nome do Ponto de Coleta: ");
        name = scanner.nextLine();

        System.out.print("Inserir Endereco Formatado: ");
        formattedAddress = scanner.nextLine();

        System.out.print("Inserir Latitude: ");
        latitude = scanner.nextBigDecimal().setScale(8, RoundingMode.HALF_UP);

        System.out.print("Inserir Longitude: ");
        longitude = scanner.nextBigDecimal().setScale(8, RoundingMode.HALF_UP);

        this.ecoColetaApiRepository
            .createCollectionPoint(
                new CollectionPointCreateDto(name, formattedAddress, latitude, longitude)
            );
    }

    private void updateCollectionPoint(final Scanner scanner) {
        System.out.println("\nEditando Ponto de Coleta...\n");
        System.out.print("Insira ID do Ponto de Coleta: ");
        final Integer id = scanner.nextInt();
        scanner.nextLine();

        final CollectionPointOutDto collectionPointOutDto;

        try {
            collectionPointOutDto = this.ecoColetaApiRepository.getCollectionPointById(id);
            System.out.println("\n".concat(collectionPointOutDto.toString()));
        } catch (Exception e) {
            System.err.println("\nErro ao Buscar Ponto de Coleta por ID. Informe o ID de um Ponto de Coleta Existente!");
            scanner.nextLine();
            return;
        }

        System.out.println("Por Favor, Informe os Campos a serem Editados. (Deixar em Branco os Campos que não Devem ser Editados)");

        final String name;
        final String formattedAddress;
        final BigDecimal latitude;
        final BigDecimal longitude;

        System.out.print("\nInserir Nome do Ponto de Coleta: ");
        name = scanner.nextLine();

        System.out.print("Inserir Endereco Formatado: ");
        formattedAddress = scanner.nextLine();

        System.out.print("Inserir Latitude (Necessário Informar este Campo): ");
        latitude = scanner.nextBigDecimal().setScale(8, RoundingMode.HALF_UP);

        System.out.print("Inserir Longitude (Necessário Informar este Campo): ");
        longitude = scanner.nextBigDecimal().setScale(8, RoundingMode.HALF_UP);

        final CollectionPointOutDto updatedCollectionPoint;

        try {
            updatedCollectionPoint = this.ecoColetaApiRepository
                .updateCollectionPoint(new CollectionPointEditDto(
                        id,
                        name.isBlank() ? collectionPointOutDto.name() : name,
                        formattedAddress.isBlank() ? collectionPointOutDto.formattedAddress() : formattedAddress,
                        latitude,
                        longitude
                ));
        } catch (Exception e) {
            System.err.println("\nErro ao Editar Ponto de Coleta!! " + e.getMessage());
            scanner.nextLine();
            return;
        }

        System.out.println("Ponto de Coleta Editado Com Sucesso!!");
        System.out.println(updatedCollectionPoint.toString());

        scanner.nextLine();
    }
}
