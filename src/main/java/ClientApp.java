import Shelby.grup.UrlUtils;
import com.google.gson.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

class CollectionPoint {
    private Long id;
    private String name;
    private String address;
    private String type;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + name + " | Endereço: " + address + " | Tipo: " + type;
    }
}

// Cliente simples em Java para consumir a API do servidor
public class ClientApp {
    private static final String BASE_URL = "http://localhost:8080/api/collection-points";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Método auxiliar GET
    private static String sendGetRequest(String endpoint) throws Exception {
        URL url = URI.create(endpoint).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) content.append(inputLine);
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
            while ((responseLine = in.readLine()) != null) response.append(responseLine.trim());
            return response.toString();
        } finally {
            con.disconnect();
        }
    }

    // Método auxiliar PUT
    private static String sendPutRequest(String endpoint, String jsonInput) throws Exception {
        URL url = URI.create(endpoint).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8), 0, jsonInput.getBytes(StandardCharsets.UTF_8).length);
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line.trim());
            return response.toString();
        } finally {
            con.disconnect();
        }
    }

    // Método para imprimir pontos formatados em português
    private static void imprimirPontos(String respostaJson) {
        try {
            JsonElement element = JsonParser.parseString(respostaJson);

            // Se a resposta for um objeto único, converte em array de 1 elemento
            JsonArray jsonArray;
            if (element.isJsonArray()) {
                jsonArray = element.getAsJsonArray();
            } else {
                jsonArray = new JsonArray();
                jsonArray.add(element.getAsJsonObject());
            }

            if (jsonArray.size() == 0) {
                System.out.println("Nenhum ponto encontrado com esse nome. Tente novamente!");
                return;
            }

            for (JsonElement elem : jsonArray) {
                JsonObject obj = elem.getAsJsonObject();
                System.out.println("\n--- Ponto de Coleta ---");
                System.out.println("ID: " + obj.get("id").getAsString());
                System.out.println("Nome da empresa: " + obj.get("name").getAsString());
                System.out.println("Endereço: " + obj.get("address").getAsString());
                System.out.println("Tipo de material: " + obj.get("type").getAsString());
            }
        } catch (Exception e) {
            System.err.println("Erro ao interpretar resposta da API: " + e.getMessage());
        }
    }
    private static String deleteRequest(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        return readResponse(con);
    }

    private static String readResponse(HttpURLConnection con) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line.trim());
            }
            return response.toString();
        } finally {
            con.disconnect();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;
            do {
                System.out.println("\n===== Cliente - Sistema de Pontos de Coleta =====");
                System.out.println("1 - Listar todos os pontos");
                System.out.println("2 - Buscar ponto por nome");
                System.out.println("3 - Cadastrar novo ponto");
                System.out.println("4 - Editar ponto existente");
                System.out.println("5 - Deletar ponto");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                String endpoint;
                String resposta;

                switch (opcao) {
                    case 1 -> {
                        endpoint = "http://localhost:8080/api/collection-points";
                        resposta = sendGetRequest(endpoint);
                        imprimirPontos(resposta);
                    }
                    case 2 -> {
                        System.out.print("Digite o nome (ou parte do nome): ");
                        String name = scanner.nextLine();
                        Map<String, String> params = Map.of("name", name);
                        endpoint = UrlUtils.buildUrlWithParams("http://localhost:8080/api/collection-points/search", params);
                        System.out.println("URL final: " + endpoint);
                        resposta = sendGetRequest(endpoint);
                        imprimirPontos(resposta);
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

                        endpoint = "http://localhost:8080/api/collection-points";
                        resposta = sendPostRequest(endpoint, jsonInput);

                        System.out.println("\n--- Ponto cadastrado com sucesso ---");
                        imprimirPontos(resposta);
                    }
                    case 4 -> {
                        System.out.print("Digite o nome do ponto que deseja editar: ");
                        String inputName = scanner.nextLine().trim();

                        // Busca pontos que contenham a string digitada
                        Map<String, String> params = Map.of("name", inputName);
                        String searchEndpoint = UrlUtils.buildUrlWithParams("http://localhost:8080/api/collection-points/search", params);
                        System.out.println("Buscando ponto...");

                        String searchResponse = sendGetRequest(searchEndpoint);
                        JsonArray foundPoints = JsonParser.parseString(searchResponse).getAsJsonArray();

                        if (foundPoints.size() == 0) {
                            System.out.println("Nenhum ponto encontrado com esse nome.");
                            break;
                        }

                        // Escolhe o ponto correto
                        JsonObject point;
                        if (foundPoints.size() == 1) {
                            point = foundPoints.get(0).getAsJsonObject();
                        } else {
                            System.out.println("Foram encontrados vários pontos:");
                            for (int i = 0; i < foundPoints.size(); i++) {
                                JsonObject p = foundPoints.get(i).getAsJsonObject();
                                System.out.println(i + " - " + p.get("name").getAsString() + " (ID: " + p.get("id").getAsString() + ")");
                            }
                            System.out.print("Escolha o índice do ponto a editar: ");
                            int index = Integer.parseInt(scanner.nextLine());
                            point = foundPoints.get(index).getAsJsonObject();
                        }

                        System.out.println("Digite os novos valores ou pressione ENTER para manter o atual.");
                        System.out.println("Digite CANCEL a qualquer momento para abortar a edição.");

                        System.out.print("Novo nome (" + point.get("name").getAsString().trim() + "): ");
                        String newName = scanner.nextLine().trim();
                        if (newName.equalsIgnoreCase("CANCEL")) { System.out.println("Edição cancelada."); break; }
                        if (newName.isEmpty()) newName = point.get("name").getAsString().trim();

                        System.out.print("Novo endereço (" + point.get("address").getAsString().trim() + "): ");
                        String newAddress = scanner.nextLine().trim();
                        if (newAddress.equalsIgnoreCase("CANCEL")) { System.out.println("Edição cancelada."); break; }
                        if (newAddress.isEmpty()) newAddress = point.get("address").getAsString().trim();

                        System.out.print("Novo tipo (" + point.get("type").getAsString().trim() + "): ");
                        String newType = scanner.nextLine().trim();
                        if (newType.equalsIgnoreCase("CANCEL")) { System.out.println("Edição cancelada."); break; }
                        if (newType.isEmpty()) newType = point.get("type").getAsString().trim();

                        // JSON usando ID do ponto
                        String jsonInput = String.format(
                                "{\"name\":\"%s\", \"address\":\"%s\", \"type\":\"%s\"}",
                                newName, newAddress, newType
                        );

                        // Atualiza usando ID do ponto
                        String updateEndpoint = BASE_URL + "/" + point.get("id").getAsString();

                        try {
                            String respostaNova = sendPutRequest(updateEndpoint, jsonInput);
                            System.out.println("Ponto atualizado com sucesso!");
                            imprimirPontos(respostaNova);
                        } catch (Exception e) {
                            System.err.println("Erro ao atualizar o ponto: " + e.getMessage());
                        }
                    }
                    case 5 -> {
                        System.out.print("Digite o ID do ponto a deletar: ");
                        String id = scanner.nextLine();
                        resposta = deleteRequest(BASE_URL + "/" + id);
                        System.out.println("Resposta do servidor: " + resposta);
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } while (opcao != 0);

        } catch (Exception e) {
            System.err.println("Erro ao consumir a API: " + e.getMessage());
        }
    }
}