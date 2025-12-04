package br.com.bikeshop.model;

public class Peca extends Produto {
    private static final long serialVersionUID = 1L;
    
    private String categoria; // Ex: "Freio", "Guidão", "Quadro"
    private String medida;
    private String material;

    public Peca(String codigo, String descricao, double preco, int estoqueMinimo, String categoria, String medida, String material) {
        super(codigo, descricao, preco, estoqueMinimo, descricao);
        this.categoria = categoria;
        this.medida = medida;
        this.material = material;
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
 
    public String getMedida() { return medida; }
	public void setMedida(String medida) { this.medida = medida; }

	public String getMaterial() { return material; }
	public void setMaterial(String material) { this.material = material; }

	@Override
    public String toString() {
        return getDescricao() + " (" + categoria + ")";
    }
}