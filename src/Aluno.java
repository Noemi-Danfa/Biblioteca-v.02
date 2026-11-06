public class Aluno extends Usuario {
    private static final int LIMITE = 3;
    private static final double MULTA_POR_DIA = 1.0;

    public Aluno(String nome) {
        super(nome);
    }

    @Override
    public String getTipo() {
        return "aluno";
    }

    @Override
    public int getLimiteEmprestimos() {
        return LIMITE;
    }

    @Override
    public double getMultaPorDia() {
        return MULTA_POR_DIA;
    }
}
