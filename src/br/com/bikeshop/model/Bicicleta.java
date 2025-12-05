package br.com.bikeshop.model;

public class Bicicleta extends Produto {
    private static final long serialVersionUID = 1L;

    // Atributos exclusivos de bicicleta
    private int aro;
    private String material;
    private String tamanhoQuadro;
    private String faixaEtaria;
    private String finalidade;

    // O construtor precisa receber os dados do Pai (Produto) + os do Filho (Bicicleta)
    public Bicicleta(String codigo, String descricao, double preco, int estoqueMinimo, 
                     int aro, String material, String tamanhoQuadro, String faixaEtaria, String finalidade) {
        super(codigo, descricao, preco, estoqueMinimo, descricao); // Passa para a classe pai
        this.aro = aro;
        this.material = material;
        this.tamanhoQuadro = tamanhoQuadro;
        this.faixaEtaria = faixaEtaria;
        this.finalidade = finalidade;
    }
    
    // toString ajuda muito na hora de debugar ou mostrar em combobox
    @Override
    public String toString() {
        return super.getDescricao() + " (Aro " + aro + ")";
    }

    // Getters e Setters específicos
    public int getAro() { return aro; }
    public void setAro(int aro) { this.aro = aro; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getTamanhoQuadro() { return tamanhoQuadro; }
    public void setTamanhoQuadro(String tamanhoQuadro) { this.tamanhoQuadro = tamanhoQuadro; }
	public String getFaixaEtaria() { return faixaEtaria;}
	public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }
	public String getFinalidade() { return finalidade; }
	public void setFinalidade(String finalidade) { this.finalidade = finalidade; }
}
