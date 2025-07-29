package maven.Projeto.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import maven.Projeto.model.Leitor;

/**
 * Classe responsável pela manipulação dos dados dos Leitores.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author Hélder
 */
public class LeitorDAO extends DAO{
	
	/**
	 * Construtor da superclasse DAO que define o CAMINHO dos registros.
	 */
    public LeitorDAO() {
		super("listaDeUsuarios.json");
	}
    
    /** 
     * Lista de Leitores carregados do arquivo JSON. 
     */
    private List<Leitor> LISTA_DE_USUARIOS = new ArrayList<>();
    
    /**
     * Cadastra um novo Leitor na lista de leitores e salva no arquivo JSON.
     * Verifica se já existe um arquivo com o mesmo código antes de cadastrar.
     *
     * @param novoUsuario O novo Leitor a ser cadastrado.
     */
	public void cadastrar(Leitor novoUsuario) {
        buscarArquivo();
        
        if (LISTA_DE_USUARIOS == null) {
            LISTA_DE_USUARIOS = new ArrayList<>();
        }
        
        for (Leitor usuario : LISTA_DE_USUARIOS) {
            if (usuario.getMatricula().equals(novoUsuario.getMatricula())) {
                System.out.println("Erro: Já existe um usuário com a matrícula \"" + novoUsuario.getMatricula() + "\".");
                return;
            }
        }
        
        JsonObject obj = GSON.toJsonTree(novoUsuario).getAsJsonObject();
        
        JsonArray array = lerJsonArray();
        array.add(obj);
        salvarJson(array);
        
        System.out.println("Leitor cadastrado com sucesso!");
    }
    
	/**
     * Exclui um Leitor do arquivo JSON com base na sua matricula.
     *
     * @param matricula A matricula do Leitor a ser excluído.
     */
    public void excluir(String matricula) {
    	JsonArray array = lerJsonArray();
        boolean removido = false;
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Aluno".equals(obj.get("tipoDeUsuario").getAsString()) &&
                matricula.equals(obj.get("matricula").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Professor".equals(obj.get("tipoDeUsuario").getAsString()) &&
                matricula.equals(obj.get("matricula").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Servidor".equals(obj.get("tipoDeUsuario").getAsString()) &&
                matricula.equals(obj.get("matricula").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        if (removido) {
        	salvarJson(array);
            System.out.println("Leitor excluído");
        } else {
            System.out.println("Leitor não encontrado");
        }
    }
    
    /**
     * Carrega os Leitores do arquivo JSON para a lista em memória.
     */
    public void buscarArquivo() {
    	LISTA_DE_USUARIOS = new ArrayList<>();
        JsonArray array = lerJsonArray();
        
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("tipoDeUsuario")) {
                Leitor leitor = GSON.fromJson(obj, Leitor.class);
                LISTA_DE_USUARIOS.add(leitor);
            }
        }
    }
    
    /**
     * Atualiza o conteúdo da LISTA_DE_USUARIOS no arquivo JSON.
     */
    private void atualizarJson() {
    	try (FileWriter escritor = new FileWriter(CAMINHO)){
    		GSON.toJson(LISTA_DE_USUARIOS, escritor);
            System.out.println("JSON de usuários atualizado com sucesso!");
    	} catch (IOException e) {
    		System.out.println("Erro ao escrever no arquivo JSON.");
    	}
    }
    
    /**
     * Método que permite a edição de um leitor que está no arquivo Json.
     * 
     * @param matricula Matricula do Leitor utilizada para achar qual Leitor será editado
     * @param novosDados Novos dados
     */
    public void editarUsuario(String matricula, Leitor novosDados) {
        buscarArquivo();
        
        if (LISTA_DE_USUARIOS == null) {
            LISTA_DE_USUARIOS = new ArrayList<>();
        }
        
        boolean encontrou = false;
        
        for (Leitor leitor : LISTA_DE_USUARIOS) {
            if (leitor.getMatricula().equals(matricula)) {
                leitor.setMatricula(novosDados.getMatricula());
            	leitor.setNome(novosDados.getNome());
                leitor.setTipoDeUsuario(novosDados.getTipoDeUsuario());
                leitor.setTelefone(novosDados.getTelefone());
                leitor.setEmail(novosDados.getEmail());
                encontrou = true;
                break;
            }
        } if (encontrou) {
            atualizarJson();
            System.out.println("Leitor atualizado com sucesso.");
        } else {
            System.out.println("Leitor com matrícula \"" + matricula + "\" não encontrado.");
        }
    }
    
    /**
     * Método que verifica se a matricula de um leitor for repetida.
     * 
     * @param matricula Matricula para ser usada como verificação
     * @return Retorna true caso a matricula parametro for repetida
     */
    public boolean verificarMatricula(String matricula) {
    	buscarArquivo();
    	
    	for (Leitor usuario : LISTA_DE_USUARIOS) {
            if (usuario.getMatricula().equals(matricula)) {
                return true;
            }
        }
    	return false;
    }
    
    // Getters and Setters
    
    public List<Leitor> getLISTA_DE_USUARIOS() {
		return LISTA_DE_USUARIOS;
	}
    
	public void setLISTA_DE_USUARIOS(List<Leitor> lISTA_DE_USUARIOS) {
		LISTA_DE_USUARIOS = lISTA_DE_USUARIOS;
	}
}
