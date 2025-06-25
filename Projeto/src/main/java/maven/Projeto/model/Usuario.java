package maven.Projeto.model;

public class Usuario {
	
	//Atributos
	
	protected String nome;
	protected String matricula;
	protected String tipoDeUsuario;
	protected String telefone;
	protected String email;
	
        // construtor padrão
	
        public Usuario() {}

        // construtor parametrizado
        public Usuario(String nome, String matricula, String telefone, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.telefone = telefone;
        this.email = email;
    }
	
}
