public class Emprestimo {
    private final Usuario usuario;
    private final Livro livro;

    public Emprestimo(Usuario usuario, Livro livro) {
        this.usuario = usuario;
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public boolean refereSe(int idUsuario, int idLivro) {
        return usuario.getId() == idUsuario && livro.getId() == idLivro;
    }

    public double calcularMulta(int diasAtraso) {
        return diasAtraso * usuario.getMultaPorDia();
    }

     public double encerrar(int diasAtraso) {
        livro.devolver();
        return calcularMulta(diasAtraso);
    }
}

