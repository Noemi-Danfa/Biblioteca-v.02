public interface operacoesBiblioteca {

    String emprestar(int idUsuario, int idLivro);

    String devolver(int idUsuario, int idLivro, int diasAtraso);
}
    

