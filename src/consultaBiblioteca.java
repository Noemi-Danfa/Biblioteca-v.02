import java.util.List;

public interface consultaBiblioteca {

    List<Livro> getLivros();

    List<Usuario> getUsuarios();

    double getTotalMultasArrecadadas();

    boolean existeUsuario(int id);
}
    

