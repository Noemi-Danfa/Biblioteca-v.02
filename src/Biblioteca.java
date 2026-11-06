import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private final List<Livro> livros = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private final  caixa caixa = new caixa();

    public Livro cadastrarLivro(String titulo, String autor) {
        Livro livro = new Livro(titulo, autor);
        livros.add(livro);
        return livro;
    }

    public Usuario cadastrarUsuario(String nome, String tipo) {
        Usuario usuario;
        switch (tipo) {
            case "aluno":
                usuario = new Aluno(nome);
                break;
            case "professor":
                usuario = new Professor(nome);
                break;
            case "comunidade":
                usuario = new Comunidade(nome);
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario desconhecido: " + tipo);
        }
        usuarios.add(usuario);
        return usuario;
    }

    public List<Livro> getLivros() {
        return List.copyOf(livros);
    }

    public List<Usuario> getUsuarios() {
        return List.copyOf(usuarios);
    }
    
    public double getTotalMultasArrecadadas() {
          return caixa.getTotal();
   }

    public boolean existeUsuario(int id) {
        return buscarUsuario(id) != null;
    }

    public String emprestar(int idUsuario, int idLivro) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario == null) {
            return "Usuario nao encontrado.";
        }
        Livro livro = buscarLivro(idLivro);
        if (livro == null) {
            return "Livro nao encontrado.";
        }
        if (!livro.isDisponivel()) {
            return "Livro indisponivel.";
        }
        if (contarEmprestimos(usuario) >= usuario.getLimiteEmprestimos()) {
            return "Limite de emprestimos atingido para o tipo " + usuario.getTipo() + ".";
        }
        livro.emprestar();
        emprestimos.add(new Emprestimo(usuario, livro));
        return "Emprestimo realizado.";
    }

    public String devolver(int idUsuario, int idLivro, int diasAtraso) {
        if (buscarUsuario(idUsuario) == null) {
            return "Usuario nao encontrado.";
        }
        Emprestimo emprestimo = buscarEmprestimo(idUsuario, idLivro);
        if (emprestimo == null) {
            return "Emprestimo nao encontrado.";
        }
       double multa = emprestimo.encerrar(diasAtraso);
        emprestimos.remove(emprestimo);
        caixa.registrar(multa);
        return "Devolucao realizada. Multa: R$ " + multa;
    }

    private Usuario buscarUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    private Livro buscarLivro(int id) {
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                return livro;
            }
        }
        return null;
    }

    private Emprestimo buscarEmprestimo(int idUsuario, int idLivro) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.refereSe(idUsuario, idLivro)) {
                return emprestimo;
            }
        }
        return null;
    }

    private int contarEmprestimos(Usuario usuario) {
        int quantidade = 0;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getUsuario() == usuario) {
                quantidade++;
            }
        }
        return quantidade;
    }
}
