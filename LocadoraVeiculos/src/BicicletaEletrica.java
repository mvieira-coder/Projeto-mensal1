class BicicletaEletrica extends Veiculo {
    public BicicletaEletrica(String identificador, String modelo, double precoHora) {
        super(identificador, modelo, precoHora);
    }
    
    @Override
    public void exibirDetalhes() {
        System.out.println("Bicicleta Elétrica - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}