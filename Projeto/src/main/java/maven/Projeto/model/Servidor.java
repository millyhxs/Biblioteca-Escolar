package maven.Projeto.model;

/**
 * Representa um leitor do tipo Servidor no sistema da biblioteca.
 * 
 * Essa classe herda de {@link Leitor} e define automaticamente o tipo de usuário como "Servidor".
 * 
 * @author Millena
 */
public class Servidor extends Leitor {
	
    /**
     * Construtor que cria um Servidor com os dados informados.
     * Define o tipo de usuário automaticamente como "Servidor".
     * 
     * @param nome | Nome do Servidor
     * @param matricula | Matrícula do Servidor
     * @param telefone | Telefone de contato
     * @param email | Endereço de e-mail
     */
    public Servidor(String nome, String matricula, String telefone, String email) {
        super(nome, matricula, telefone, email);
        super.setTipoDeUsuario("Servidor");
    }
    
    /**
     * Construtor padrão. Define o tipo de usuário como "Servidor". 
     * Utilizado para leitura de dados via GSON ou frameworks.
     */
    public Servidor() {
        super();
        super.setTipoDeUsuario("Servidor");
    }
    
}
