package maven.Projeto.model;

/**
 * Representa um funcionário do sistema da biblioteca.
 * 
 * Esta classe contém os dados básicos de um funcionário, como ID, nome, senha,
 * tipo (função) e status de atividade.
 * Pode ser utilizada tanto para controle de acesso quanto para gerenciamento de usuários do sistema.
 * 
 * @author
 */
public class Funcionario {
	
	/**
     * id | ID de um Funcionário.
     * senha | Senha de login do Usuário.
     * nome | Nome do Funcionário.
     * tipo | Tipo de Funcionário.
     * ativo | Estado de atividade.
     */
	private String id;
    private String senha;
    private String nome;
    private String tipo;
    private boolean ativo;
	
    /**
     * Construtor com parâmetros para criação de um funcionário completo.
     *
     * @param id Identificador único do funcionário
     * @param senha Senha de acesso
     * @param nome Nome completo
     * @param tipo Tipo de função do funcionário (ex: "Administrador")
     */
    public Funcionario(String id, String senha, String nome, String tipo) {
    	this.id = id;
    	this.senha = senha;
    	this.nome = nome;
    	this.tipo = tipo;
    }
    
    /**
     * Construtor padrão. Utilizado para leitura de dados via GSON ou frameworks.
     */
    public Funcionario() {
    	
    }
    
    /**
     * Define o status de atividade do funcionário.
     * 
     * @param ativo {@code true} para ativar, {@code false} para desativar
     */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
    
    // Getters and Setters
    
    public String getId() {
		return id;
	}
    
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
