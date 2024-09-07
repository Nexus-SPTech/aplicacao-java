package br.com.school.sptech.nexus.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class logUsuarios {
    private final List<Usuarios> listaUsuarios = new ArrayList<>();
    private Usuarios usuarioLogado = null;  // Variável para manter o controle do usuário logado

    public String pegarHorarioAtual(){
        // Pegando o horário e a data de agora
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Variável para formatar a data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Formatando a data
        String dataFormatada = formatter.format(timestamp);

        return dataFormatada;
    }

    public void cadastrarUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Qual o nome de usuário?");
        String usuario = scanner.nextLine();

        System.out.println("Qual seu email?");
        String email = scanner.nextLine();

        System.out.println("Qual sua senha?");
        String senha = scanner.nextLine();

        // Instanciando um objeto Usuario com as informações inseridas pelo usuário no Console
        // com a data do momento do cadastro no console
        Usuarios usuarios = new Usuarios(usuario, email, senha, pegarHorarioAtual());
        listaUsuarios.add(usuarios);

        System.out.println("Usuário cadastrado com sucesso, Horário de cadastro: " + pegarHorarioAtual());
    }

    public void logarUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu email:");
        String email = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String senha = scanner.nextLine();

        Boolean usuarioEncontrado = false;
        for (Usuarios usuario : listaUsuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                usuarioEncontrado = true;
                System.out.println("Login bem-sucedido! Bem-vindo(a), " + usuario.getUsuario()
                + ", Horário de login: " + pegarHorarioAtual());
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("Email ou senha incorretos. Horário da tentativa: " + pegarHorarioAtual());
        }
    }

    public void sairUsuario() {
        if (usuarioLogado != null) {
            System.out.println("Usuário " + usuarioLogado.getUsuario() + " saiu da conta, Horário de saida: "
                    + pegarHorarioAtual());
            usuarioLogado = null;
        } else {
            System.out.println("Nenhum usuário está logado.");
        }
    }

    public void receberSaudacoes() {
        if (usuarioLogado != null) {
            System.out.println("Olá, " + usuarioLogado.getUsuario() + "! Bem-vindo(a) de volta!");
        } else {
            System.out.println("Nenhum usuário está logado. Faça login para receber saudações.");
        }
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                1 - Cadastrar nova conta
                2 - Logar conta
                3 - Sair da conta
                4 - Receber saudações

                0 - Sair da aplicação
                """);
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1: {
                    cadastrarUsuario();
                    break;
                }
                case 2: {
                    logarUsuario();
                    break;
                }
                case 3: {
                    sairUsuario();
                    break;
                }
                case 4: {
                    receberSaudacoes();
                    break;
                }
                case 0: {
                    try {
                        System.out.println("Saindo do sistema...");
                        Thread.sleep(1500);
                        System.out.println("Sistema encerrado com sucesso!");
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                default: {
                    System.out.println("Opção inválida, tente novamente.");
                    break;
                }
            }
        }
    }

}
