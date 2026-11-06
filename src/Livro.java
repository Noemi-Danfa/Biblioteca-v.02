public class Livro {
    private static int proximoId = 1;

    private final int id;
    private final String titulo;
    private final String autor;
    private boolean disponivel;

    public Livro(String titulo, String autor) {
        this.id = proximoId++;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestar() {
        disponivel = false;
    }

    public void devolver() {
        disponivel = true;
    }
}
