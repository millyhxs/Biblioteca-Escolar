package maven.Projeto.model;

/**
 * Representa um leitor do tipo Professor no sistema da biblioteca.
 * 
 * Essa classe herda de {@link Leitor} e define automaticamente o tipo de usuário como "Professor".
 * 
 * @author Millena
 */
public class Professor extends Leitor {
    
	/**
     * Construtor que cria um professor com os dados informados.
     * Define o tipo de usuário automaticamente como "Professor".
     * 
     * @param nome | Nome do professor
     * @param matricula | Matrícula do professor
     * @param telefone | Telefone de contato
     * @param email | Endereço de e-mail
     */
    public Professor(String nome, String matricula, String telefone, String email) {
        super(nome, matricula, telefone, email);
        super.setTipoDeUsuario("Professor");
    }
    
    /**
     * Construtor padrão. Define o tipo de usuário como "Professor". 
     * Utilizado para leitura de dados via GSON ou frameworks.
     */
    public Professor() {
        super();
        super.setTipoDeUsuario("Professor");
    }
    
}
