public class FilmeTerror extends Filme {
    private int ano;
    private String categoria;

    public FilmeTerror(String titulo, String diretor, int ano, String categoria) {
        super(titulo, diretor);
        this.ano = ano;
        this.categoria = categoria;
    }

    @Override
    protected void exibirInfo() {
        super.exibirInfo();
        System.out.printf("Filme: %s\nAno: %d\nCategoria: %s", titulo, ano, categoria);
    }
}
