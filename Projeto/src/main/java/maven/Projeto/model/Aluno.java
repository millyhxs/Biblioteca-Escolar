package maven.Projeto.model;

public class Aluno extends Usuario {

    public Aluno() {
        super();
        super.setTipoDeUsuario("Aluno");
    }

    public Aluno(String nome, String matricula, String telefone, String email) {
        super(nome, matricula, telefone, email);
        super.setTipoDeUsuario("Aluno");
    }
}
