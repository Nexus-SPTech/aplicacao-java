package school.sptech.test;

public class App {

    public static void main(String[] args) throws Exception {

        // Substitua pela sua URL de Webhook do Slack
        String webhookUrl = "https://hooks.slack.com/services/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";

        SlackService slack = new SlackService(webhookUrl);
        slack.sendNotification("Olá, Lucas! Esta é uma mensagem de teste do meu projeto.");
    }
}
