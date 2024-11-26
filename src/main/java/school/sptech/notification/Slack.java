package school.sptech.notification;

public abstract class Slack {
    protected String webhookUrl;
    protected String mensagem;

    public Slack() {
        this.mensagem = "Nenhuma mensagem carregada!";
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public abstract void sendNotification();
}
