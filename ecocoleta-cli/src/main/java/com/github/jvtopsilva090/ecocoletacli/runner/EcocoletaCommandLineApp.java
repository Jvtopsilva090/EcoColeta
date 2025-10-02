package com.github.jvtopsilva090.ecocoletacli.runner;

import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointCreateDto;
import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointEditDto;
import com.github.jvtopsilva090.ecocoletacli.dto.CollectionPointOutDto;
import com.github.jvtopsilva090.ecocoletacli.dto.ResidueOutDto;
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
        System.out.println("1 - Listar Todos os Pontos de Coleta");
        System.out.println("2 - Buscar Ponto de Coleta por ID");
        System.out.println("3 - Buscar Ponto de Coleta por Nome");
        System.out.println("4 - Buscar Ponto de Coleta por Tipo de Resíduo");
        System.out.println("5 - Cadastrar Novo Ponto de Coleta");
        System.out.println("6 - Editar Ponto de Coleta");
        System.out.println("7 - Deletar Ponto de Coleta por ID");
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
                listCollectionPointByName(scanner);
                break;
            case 4:
                listCollectionPointByResidueType(scanner);
                break;
            case 5:
                createCollectionPoint(scanner);
                break;
            case 6:
                updateCollectionPoint(scanner);
                break;
            case 7:
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

    private void listCollectionPointByResidueType(Scanner scanner) {
        System.out.println("\nListando Ponto de Coleta por Tipo de Resíduo...\n");
        System.out.print("Insira o Tipo de Resíduo do Ponto de Coleta: ");
        final String residueType = scanner.nextLine();

        final List<CollectionPointOutDto> collectionPointOutDto;

        try {
            collectionPointOutDto = this.ecoColetaApiRepository.getCollectionPointByResidueType(residueType);
            System.out.println();
            collectionPointOutDto.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("\nErro ao Buscar Ponto de Coleta por Tipo de Resíduo. Informe o Tipo de Resíduo de um Ponto de Coleta Existente!");
        }
    }

    private void listCollectionPointByName(Scanner scanner) {
        System.out.println("\nListando Ponto de Coleta por Nome...\n");
        System.out.print("Insira o Nome do Ponto de Coleta: ");
        final String nome = scanner.nextLine();

        final List<CollectionPointOutDto> collectionPointOutDto;

        try {
            collectionPointOutDto = this.ecoColetaApiRepository.getCollectionPointByName(nome);
            System.out.println();
            collectionPointOutDto.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("\nErro ao Buscar Ponto de Coleta por Nome. Informe o Nome de um Ponto de Coleta Existente!");
        }
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
        System.out.print("Insira o ID do Ponto de Coleta: ");
        final Integer id = scanner.nextInt();

        final CollectionPointOutDto collectionPointOutDto;

        try {
            collectionPointOutDto = this.ecoColetaApiRepository.getCollectionPointById(id);
            System.out.println("\n".concat(collectionPointOutDto.toString()));
        } catch (Exception e) {
            System.err.println("\nErro ao Buscar Ponto de Coleta por ID. Informe o ID de um Ponto de Coleta Existente!");
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

        List<Integer> residueIds = printAndGetResidues(scanner);

        try {
            CollectionPointOutDto dto = this.ecoColetaApiRepository
                .createCollectionPoint(
                        new CollectionPointCreateDto(name, formattedAddress, latitude, longitude, residueIds)
                );

            System.out.println("Ponto de Coleta Cadastrado Com Sucesso!!\n");
            System.out.println(dto.toString());
        } catch (Exception e) {
            System.err.println("Erro ao Cadastrar Ponto de Coleta!! (O Conjunto \"Longitude, Latitude\" não podem ter duplicatas): " + e.getMessage());
        }
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

        List<Integer> residueIds = printAndGetResidues(scanner);

        final CollectionPointOutDto updatedCollectionPoint;

        try {
            updatedCollectionPoint = this.ecoColetaApiRepository
                .updateCollectionPoint(new CollectionPointEditDto(
                        id,
                        name.isBlank() ? collectionPointOutDto.name() : name,
                        formattedAddress.isBlank() ? collectionPointOutDto.formattedAddress() : formattedAddress,
                        latitude,
                        longitude,
                        residueIds
                ));
        } catch (Exception e) {
            System.err.println("\nErro ao Editar Ponto de Coleta!! (O Conjunto \"Longitude, Latitude\" não podem ter duplicatas): " + e.getMessage());
            scanner.nextLine();
            return;
        }

        System.out.println("Ponto de Coleta Editado Com Sucesso!!");
        System.out.println();
        System.out.println(updatedCollectionPoint.toString());

        scanner.nextLine();
    }

    private List<Integer> printAndGetResidues(Scanner scanner) {
        System.out.println("\nObtendo Tipos de Resíduo para Ponto de Coleta...");
        System.out.print("Insira o ID do Tipo de Resíduo.\n");
        System.out.println();
        List<ResidueOutDto> residues = this.ecoColetaApiRepository.getAllResiduesType();

        System.out.println("Lista de Tipos de Resíduos: ");
        residues.forEach(System.out::println);
        System.out.println();

        final List<Integer> ids = new ArrayList<>();
        int id = 0;

        do {
            System.out.println("(Insira o valor \"0\" para parar de ler os IDs)");
            System.out.print("Insira o ID do Tipo de Resíduo: ");
            id = scanner.nextInt();
            if (id == 0) break;
            else ids.add(id);
        } while (id > 0);

        System.out.println();

        return ids;
    }
}
