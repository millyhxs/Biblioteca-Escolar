package maven.Projeto.model;

/**
 * Representa um leitor do tipo Aluno no sistema da biblioteca.
 * 
 * Essa classe herda de {@link Leitor} e define automaticamente o tipo de usuário como "Aluno".
 * 
 * @author
 */
public class Aluno extends Leitor {
    
	/**
     * Construtor que cria um aluno com os dados informados.
     * Define o tipo de usuário automaticamente como "Aluno".
     * 
     * @param nome Nome do aluno
     * @param matricula Matrícula do aluno
     * @param telefone Telefone de contato
     * @param email Endereço de e-mail
     */
    public Aluno(String nome, String matricula, String telefone, String email) {
        super(nome, matricula, telefone, email);
        super.setTipoDeUsuario("Aluno");
    }
    
    /**
     * Construtor padrão. Define o tipo de usuário como "Aluno". 
     * Utilizado para leitura de dados via GSON ou frameworks.
     */
    public Aluno() {
        super();
        super.setTipoDeUsuario("Aluno");
    }
}
