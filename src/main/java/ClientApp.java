import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

// Cliente simples em Java para consumir a API do servidor
public class ClientApp {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Método auxiliar GET
    private static String sendGetRequest(String endpoint) throws Exception {
        URL url = URI.create(endpoint).toURL(); // moderno, evita deprecation
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } finally {
            con.disconnect();
        }
    }

    // Método auxiliar POST
    private static String sendPostRequest(String endpoint, String jsonInput) throws Exception {
        URL url = URI.create(endpoint).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } finally {
            con.disconnect();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) { // também com try-with-resources
            int opcao;
            do {
                System.out.println("\n===== Cliente - Sistema de Pontos de Coleta =====");
                System.out.println("1 - Listar todos os pontos");
                System.out.println("2 - Buscar ponto por nome");
                System.out.println("3 - Cadastrar novo ponto");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                String endpoint;
                String resposta;

                switch (opcao) {
                    case 1 -> {
                        endpoint = "http://localhost:8080/api/points";
                        resposta = sendGetRequest(endpoint);
                        System.out.println("\n--- Lista de Pontos ---");
                        System.out.println(gson.toJson(gson.fromJson(resposta, Object.class)));
                    }
                    case 2 -> {
                        System.out.print("Digite o nome (ou parte do nome): ");
                        String name = scanner.nextLine();
                        endpoint = "http://localhost:8080/api/points/search?name=" + name;
                        resposta = sendGetRequest(endpoint);
                        System.out.println("\n--- Resultado da Busca ---");
                        System.out.println(gson.toJson(gson.fromJson(resposta, Object.class)));
                    }
                    case 3 -> {
                        System.out.print("Nome do ponto: ");
                        String nome = scanner.nextLine();
                        System.out.print("Endereço: ");
                        String endereco = scanner.nextLine();
                        System.out.print("Tipo (Plástico, papel, vidro...): ");
                        String tipo = scanner.nextLine();

                        String jsonInput = String.format(
                                "{\"name\":\"%s\", \"address\":\"%s\", \"type\":\"%s\"}",
                                nome, endereco, tipo);

                        endpoint = "http://localhost:8080/api/points";
                        resposta = sendPostRequest(endpoint, jsonInput);

                        System.out.println("\n--- Ponto cadastrado com sucesso ---");
                        System.out.println(gson.toJson(gson.fromJson(resposta, Object.class)));
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } while (opcao != 0); // <-- aqui o loop termina corretamente

        } catch (Exception e) {
            System.err.println("Erro ao consumir a API: " + e.getMessage());
        }
    }
}