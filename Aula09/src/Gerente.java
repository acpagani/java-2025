public class Gerente extends Funcionario {
    public Gerente(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public void exibirDados() {
        System.out.println("Cargo: Gerente");
        super.exibirDados();
    }
}
