package school.sptech.notification;

public class App {

    public static void main(String[] args) throws Exception {
        SlackClients slackClients = new SlackClients("Mensagem");
        slackClients.sendNotification();

        SlackLogs slackLogs = new SlackLogs("Oi");
    }
}
