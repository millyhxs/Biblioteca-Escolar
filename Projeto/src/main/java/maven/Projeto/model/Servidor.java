package maven.Projeto.model;

public class Servidor extends Leitor {
    public Servidor() {
        super();
        super.setTipoDeUsuario("Servidor");
    }
    
    public Servidor(String nome, String matricula, String telefone, String email) {
        super(nome, matricula, telefone, email);
        super.setTipoDeUsuario("Servidor");
    }
}
