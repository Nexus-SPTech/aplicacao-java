package school.sptech;

import school.sptech.notification.SlackClients;
import school.sptech.notification.SlackLogs;

public class Teste {
    public static void main(String[] args) {
        SlackClients slackClients = new SlackClients();
        slackClients.sendNotification("oi");

        SlackLogs slackLogs = new SlackLogs();
// opa

        slackLogs.sendNotification("aaaaaaaaaaaa");
        slackClients.sendNotification("oi");
    }
}
