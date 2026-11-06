public abstract class Usuario {
    private static int proximoId = 1;

    private final int id;
    private final String nome;

    public Usuario(String nome) {
        this.id = proximoId++;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract String getTipo();

    public abstract int getLimiteEmprestimos();

    public abstract double getMultaPorDia();
}
