public class Professor extends Usuario {
    private static final int LIMITE = 10;
    private static final double MULTA_POR_DIA = 0.5;

    public Professor(String nome) {
        super(nome);
    }

    @Override
    public String getTipo() {
        return "professor";
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
