import java.util.Scanner;

public class Menu {
    private final Biblioteca biblioteca;
    private final Scanner sc = new Scanner(System.in);

    public Menu(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void executar() {
        int opcao = -1;
        while (opcao != 0) {
            imprimirOpcoes();
            String linha = sc.nextLine();
            try {
                opcao = Integer.parseInt(linha);
            } catch (Exception e) {
                System.out.println("Opcao invalida.");
                continue;
            }
            if (opcao == 1) {
                listarLivros();
            } else if (opcao == 2) {
                listarUsuarios();
            } else if (opcao == 3) {
                emprestar();
            } else if (opcao == 4) {
                devolver();
            } else if (opcao == 5) {
                System.out.println("Total de multas arrecadadas: R$ " + biblioteca.getTotalMultasArrecadadas());
            } else if (opcao == 0) {
                System.out.println("Ate logo.");
            } else {
                System.out.println("Opcao invalida.");
            }
        }
    }

    private void imprimirOpcoes() {
        System.out.println();
        System.out.println("=== BIBLIOTECA ===");
        System.out.println("1 - Listar livros");
        System.out.println("2 - Listar usuarios");
        System.out.println("3 - Emprestar livro");
        System.out.println("4 - Devolver livro");
        System.out.println("5 - Relatorio de multas");
        System.out.println("0 - Sair");
        System.out.print("Opcao: ");
    }

    private void listarLivros() {
        for (Livro livro : biblioteca.getLivros()) {
            System.out.println("[ID " + livro.getId() + "] - " + livro.getTitulo() + " (" + livro.getAutor() + ") ["
                    + (livro.isDisponivel() ? "disponivel" : "emprestado") + "]");
        }
    }

    private void listarUsuarios() {
        for (Usuario usuario : biblioteca.getUsuarios()) {
            System.out.println("[ID " + usuario.getId() + "] - " + usuario.getNome() + " [" + usuario.getTipo() + "]");
        }
    }

    private void emprestar() {
        Integer idUsuario = lerInteiro("ID do usuario: ");
        if (idUsuario == null) {
            System.out.println("ID invalido.");
            return;
        }
        // Como na v0, o ID do livro so e pedido se o usuario existir.
        if (!biblioteca.existeUsuario(idUsuario)) {
            System.out.println("Usuario nao encontrado.");
            return;
        }
        Integer idLivro = lerInteiro("ID do livro: ");
        if (idLivro == null) {
            System.out.println("ID invalido.");
            return;
        }
        System.out.println(biblioteca.emprestar(idUsuario, idLivro));
    }

    private void devolver() {
        Integer idUsuario = lerInteiro("ID do usuario: ");
        if (idUsuario == null) {
            System.out.println("ID invalido.");
            return;
        }
        Integer idLivro = lerInteiro("ID do livro: ");
        if (idLivro == null) {
            System.out.println("ID invalido.");
            return;
        }
        Integer diasAtraso = lerInteiro("Dias em atraso: ");
        if (diasAtraso == null) {
            diasAtraso = 0;
        }
        System.out.println(biblioteca.devolver(idUsuario, idLivro, diasAtraso));
    }

    // Devolve null quando a entrada nao e um numero inteiro.
    private Integer lerInteiro(String rotulo) {
        System.out.print(rotulo);
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return null;
        }
    }
}
