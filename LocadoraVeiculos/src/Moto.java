class Moto extends Veiculo {
    public Moto(String identificador, String modelo, double precoHora) {
        super(identificador, modelo, precoHora);
    }
    
    @Override
    public void exibirDetalhes() {
        System.out.println("Moto - ID: " + getIdentificador() + ", Modelo: " + getModelo() + ", Preco por Hora: R$ " + getPrecoHora());
    }
}