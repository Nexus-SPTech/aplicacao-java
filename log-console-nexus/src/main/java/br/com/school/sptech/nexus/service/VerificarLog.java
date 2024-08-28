package br.com.school.sptech.nexus.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class VerificarLog {
    void logarUsuario() {
        // Pegando o horário e a data de agora
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Variável para formatar a data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Formatando a data
        String dataFormatada = formatter.format(timestamp);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual é o usuário a ser logado?");

        String usuarioLogado = scanner.nextLine();

        System.out.println("""
                ***************-***************
                 Usuário logado com sucesso!
                 Usuário logado: %s
                 Momento do log: %s
                ***************-***************
                """.formatted(usuarioLogado, dataFormatada));

        scanner.close();
    }

}
