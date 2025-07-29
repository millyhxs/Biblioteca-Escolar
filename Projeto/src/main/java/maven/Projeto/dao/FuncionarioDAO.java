package maven.Projeto.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import maven.Projeto.model.Funcionario;

/**
 * Classe responsável pela manipulação dos dados dos Funcionários.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author Hélder
 */
public class FuncionarioDAO extends DAO{
	
	/**
	 * Construtor da superclasse DAO que define o CAMINHO dos registros.
	 */
	public FuncionarioDAO() {
		super("listaDeFuncionarios.json");
		
	}
	
	/** 
	 * Lista de Funcionários carregados do arquivo JSON. 
     */
    private List<Funcionario> LISTA_DE_FUNCIONARIOS = new ArrayList<>();
    
    /**
     * Cadastra um novo Funcionário na lista de funcionários e salva no arquivo JSON.
     * Verifica se já existe um arquivo com o mesmo código antes de cadastrar.
     * 
     * @param novoServidor O novo Funcionário a ser cadastrado.
     */
	public void cadastrar(Funcionario novoServidor) {
        buscarArquivo();
        
        if (LISTA_DE_FUNCIONARIOS == null) {
            LISTA_DE_FUNCIONARIOS = new ArrayList<>();
        }
        
        for (Funcionario funcionario : LISTA_DE_FUNCIONARIOS) {
            if (funcionario.getId().equals(novoServidor.getId())) {
                System.out.println("Erro: Já existe um servidor com o ID \"" + novoServidor.getId() + "\".");
                return;
            }
        }
        
        LISTA_DE_FUNCIONARIOS.add(novoServidor);
        atualizarJson();
    }
    
	/**
     * Exclui um Funcionário do arquivo JSON com base na sua matricula.
     *
     * @param id O ID do Funcionário a ser excluído.
     */
    public void excluir(String id) {
    	JsonArray array = lerJsonArray();
        boolean removido = false;
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Administrador".equals(obj.get("tipo").getAsString()) &&
                id.equals(obj.get("id").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Estagiário".equals(obj.get("tipo").getAsString()) &&
                id.equals(obj.get("id").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Bibliotecário".equals(obj.get("tipo").getAsString()) &&
                id.equals(obj.get("id").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        if (removido) {
        	salvarJson(array);
        } else {
            System.out.println("Leitor não encontrado");
        }
    }
    
    /**
     * Carrega os Leitores do arquivo JSON para a lista em memória.
     */
    public void buscarArquivo() {
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Funcionario>>() {}.getType();
            LISTA_DE_FUNCIONARIOS = GSON.fromJson(leitor, tipoLista);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON.");
        }
    }
    
    /**
     * Atualiza o conteúdo da LISTA_DE_FUNCIONARIOS no arquivo JSON.
     */
    private void atualizarJson() {
    	try (FileWriter escritor = new FileWriter(CAMINHO)){
    		GSON.toJson(LISTA_DE_FUNCIONARIOS, escritor);
    	} catch (IOException e) {
    		System.out.println("Erro ao escrever no arquivo JSON.");
    	}
    }
    
    /**
     * Método que permite a edição de um leitor que está no arquivo Json.
     * 
     * @param id ID do Funcionário utilizada para achar qual Funcionário será editado
     * @param novosDados Novos dados
     */
    public void editarUsuario(String id, Funcionario novosDados) {
        buscarArquivo();
        
        if (LISTA_DE_FUNCIONARIOS == null) {
        	LISTA_DE_FUNCIONARIOS = new ArrayList<>();
        }
        
        boolean encontrou = false;
        
        for (Funcionario funcionario : LISTA_DE_FUNCIONARIOS) {
            if (funcionario.getId().equals(id)) {
            	funcionario.setId(novosDados.getId());
            	funcionario.setNome(novosDados.getNome());
            	funcionario.setSenha(novosDados.getSenha());
                funcionario.setTipo(novosDados.getTipo());
            	encontrou = true;
                break;
            }
        } if (encontrou) {
            atualizarJson();
        } else {
            System.out.println("Funcionario com id \"" + id + "\" não encontrado.");
        }
    }
    
    /**
     * Método que busca na lista json o Funcionário dea acordo com a matricula e senha.
     * 
     * @param id ID exclusivo do Funcionário
     * @param senha Senha exclusiva do Funcionário
     * @return Retorna o Funcionário encontrado
     */
    public Funcionario buscarPorIdESenha(String id, String senha) {
        buscarArquivo();
        
        if (LISTA_DE_FUNCIONARIOS != null) {
            for (Funcionario funcionario : LISTA_DE_FUNCIONARIOS) {
                if (funcionario.getId().equals(id) && funcionario.getSenha().equals(senha)) {
                	funcionario.setAtivo(true);
                    atualizarJson();
                    return funcionario;
                }
            }
        }
        return null;
    }
    
    /**
     * Método que retorna o Funcionário que está ativo na lista json.
     * 
     * @return Retorna o Funcionário que está ativo
     */
    public Funcionario buscarFuncionarioAtivo() {
        buscarArquivo();
        
        if (LISTA_DE_FUNCIONARIOS != null) {
            for (Funcionario funcionario : LISTA_DE_FUNCIONARIOS) {
                if (funcionario.isAtivo()) {
                    return funcionario;
                }
            }
        }
        return null;
    }
    /**
     * Método que busca um Funcionário que está ativo no json e modifica ele para que fique falso.
     */
    public void deslogarFuncionarioAtivo() {
        try (FileReader reader = new FileReader(CAMINHO)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                if (obj.has("ativo") && obj.get("ativo").getAsBoolean()) {
                    obj.addProperty("ativo", false);
                }
            }
            
            try (FileWriter writer = new FileWriter(CAMINHO)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(array, writer);
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao deslogar funcionário: " + e.getMessage());
        }
    }
    
    // Getters and Setters
    
    public List<Funcionario> getLISTA_DE_FUNCIONARIOS() {
		return LISTA_DE_FUNCIONARIOS;
	}
    
	public void setLISTA_DE_FUNCIONARIOS(List<Funcionario> lISTA_DE_FUNCIONARIOS) {
		LISTA_DE_FUNCIONARIOS = lISTA_DE_FUNCIONARIOS;
	}
	
}
