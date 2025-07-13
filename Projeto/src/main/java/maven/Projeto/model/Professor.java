package maven.Projeto.model;

public class Professor extends Leitor {
    public Professor() {
        super();
        super.setTipoDeUsuario("Professor");
    }
    
    public Professor(String nome, String matricula, String telefone, String email) {
        super(nome, matricula, telefone, email);
        super.setTipoDeUsuario("Professor");
    }
}
