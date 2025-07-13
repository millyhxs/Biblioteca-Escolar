package maven.Projeto.model;

public class Leitor {
	
	protected String nome;
	protected String matricula;
	protected String tipoDeUsuario;
	protected String telefone;
	protected String email;

		
        public Leitor() {
        	
        }
        
        public Leitor(String nome, String matricula, String telefone, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.telefone = telefone;
        this.email = email;
        }
        
        
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
