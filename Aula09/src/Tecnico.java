public class Tecnico extends Assistente {

    private double bonusSalarial;

    public Tecnico(String nome, double salario, int matricula, double bonusSalarial) {
        super(nome, salario, matricula);
        this.bonusSalarial = bonusSalarial;
    }

    @Override
    public void exibirDados() {
        System.out.println("Categoria: Técnico");
        super.exibirDados();
        System.out.println("Bônus salarial: " + bonusSalarial);
        System.out.println("Salário com bônus: " + salario + bonusSalarial);
    }
}
