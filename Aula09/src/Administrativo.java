public class Administrativo extends Assistente {
    private String turno;
    private double adicionalNoturno;

    public Administrativo(String nome, double salario, int matricula, String turno) {
        super(nome, salario, matricula);
        this.turno = turno;
        this.adicionalNoturno = adicionalNoturno;
    }

    @Override
    public void exibirDados() {
        System.out.println("Categoria: Administrativo");
        super.exibirDados();
        System.out.println("Turno: " + turno);
        if (turno.equalsIgnoreCase("noite")) {
            System.out.println("Adicional noturno: " + adicionalNoturno);
            System.out.println("Salario com adicional noturno: " + adicionalNoturno + salario);
        } else {
            System.out.println("Salario: " + salario);
        }
    }
}
