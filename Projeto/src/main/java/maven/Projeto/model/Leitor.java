package maven.Projeto.model;

/**
 * Representa um leitor (usuário) do sistema da biblioteca.
 * 
 * Um leitor possui informações básicas como nome, matrícula, telefone,
 * e-mail e o tipo de usuário (ex: "Aluno", "Professor", "Visitante").
 * 
 * Essa classe é usada para identificar quem pode realizar empréstimos de obras.
 * 
 * @author
 */
public class Leitor {
	
	/**
     * nome | Nome do Leitor.
     * matricula | Matricula do Leitor.
     * tipoDeUsuario | Tipo de Usuário.
     * telefone | Telefone do Leitor.
     * email | Email do Leitor.
     */
	private String nome;
	private String matricula;
	private String tipoDeUsuario;
	private String telefone;
	private String email;
	
	/**
     * Construtor com parâmetros para criação de um leitor.
     * 
     * @param nome Nome completo
     * @param matricula Matrícula única do leitor
     * @param telefone Telefone de contato
     * @param email E-mail de contato
     */
    public Leitor(String nome, String matricula, String telefone, String email) {
    this.nome = nome;
    this.matricula = matricula;
    this.telefone = telefone;
    this.email = email;
    }
    
    /**
    * Construtor vazio. Utilizado para leitura de dados via GSON ou frameworks.
    */
    public Leitor() {
    	
    }
    
    // Getters and Setters
    
    public String getNome() {
		return nome;
	}
    
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
		
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTipoDeUsuario() {
		return tipoDeUsuario;
	}
	
	public void setTipoDeUsuario(String tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}
	
}
