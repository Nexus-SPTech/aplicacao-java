package school.sptech.notification;

public abstract class Slack {
    protected String webhookUrl;

    public Slack() {
    }

    public abstract void sendNotification(String mensagem);
}
