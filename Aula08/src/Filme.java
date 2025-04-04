public class Filme {
    protected String titulo;
    protected String diretor;
    protected double avaliacao;

    public Filme(String titulo, String diretor) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.avaliacao = 0.0;
    }
    
    protected void exibirInfo() {
        System.out.println("Nome do filme: " + titulo);
        System.out.println("Diretor do filme: " + diretor);
        System.out.println(avaliacao == 0.0 ? "Ainda não avaliado" : avaliacao + "/5");
    }
    
    public void avaliarFilme (double nota) {
        if (nota >= 0 && nota <= 5) {
            avaliacao = nota;
            System.out.printf("O filme %s recebeu nota: %.2f\n", titulo, nota);
        }
        else {
            System.out.println("A nota deve ser entre 0 e 5!");
        }
    }
}
