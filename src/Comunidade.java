public class Comunidade extends Usuario {
    private static final int LIMITE = 1;
    private static final double MULTA_POR_DIA = 2.0;

    public Comunidade(String nome) {
        super(nome);
    }

    @Override
    public String getTipo() {
        return "comunidade";
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
