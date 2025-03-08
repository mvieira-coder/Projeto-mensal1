class Barco extends Veiculo {
    public Barco(String identificador, String modelo, double precoHora) {
        super(identificador, modelo, precoHora);
    }
    
    @Override
    public void exibirDetalhes() {
        System.out.println("Barco - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}