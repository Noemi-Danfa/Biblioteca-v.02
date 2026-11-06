public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        seed(biblioteca);
        new Menu(biblioteca).executar();
    }

    private static void seed(Biblioteca biblioteca) {
        biblioteca.cadastrarLivro("Engenharia de Software Moderna", "Marco Tulio");
        biblioteca.cadastrarLivro("Clean Code", "Robert Martin");
        biblioteca.cadastrarLivro("Refactoring", "Martin Fowler");
        biblioteca.cadastrarLivro("O Cortico", "Aluisio Azevedo");
        biblioteca.adicionarUsuario(new Aluno("Ana"));
        biblioteca.adicionarUsuario(new Professor("Prof. Bruno"));
        biblioteca.adicionarUsuario(new Comunidade("Carlos"));
}
}