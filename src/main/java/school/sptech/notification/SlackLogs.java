package school.sptech.notification;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SlackLogs extends Slack {

    public SlackLogs() {
        this.webhookUrl = "https://hooks.slack.com/services/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";
    }

    public void sendNotification(String mensagem) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(webhookUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            String payload = buildPayload(mensagem);

            // Envio do payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Processamento da resposta
            Integer responseCode = connection.getResponseCode();
            handleResponse(responseCode, connection);

        } catch (MalformedURLException e) {
            System.err.println("URL inválida para o webhook: " + webhookUrl);
        } catch (IOException e) {
            System.err.println("Erro na conexão ou ao enviar dados para o Slack: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String buildPayload(String message) {
        // Constrói o payload no formato JSON
        return "{\"text\": \"" + escapeJson(message) + "\"}";
    }

    private void handleResponse(int responseCode, HttpURLConnection connection) throws IOException {
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Mensagem enviada com sucesso ao Slack.");
        } else {
            System.err.println("Erro ao enviar mensagem ao Slack. Código de resposta: " + responseCode);

            // Leitura opcional do corpo da resposta de erro, se disponível
            try (InputStream errorStream = connection.getErrorStream()) {
                if (errorStream != null) {
                    String errorMessage = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));
                    System.err.println("Detalhes do erro: " + errorMessage);
                }
            }
        }
    }

    private String escapeJson(String message) {
        return message.replace("\"", "\\\"");
    }

}
