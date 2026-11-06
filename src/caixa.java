public class caixa {

    private double total = 0.0;

    public void registrar(double multa) {
        total += multa;
    }

    public double getTotal() {
        return total;
    }
}