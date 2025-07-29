package maven.Projeto.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Classe "mãe" de todas as classes dao.
 * Possui métodos genéricos que são utilizados nas subclasses.
 * 
 * @author Hélder
 */
public class DAO {
	
	/** 
	 * Caminho do arquivo JSON onde os dados são salvos. 
     */
	protected String CAMINHO;
	
	/** 
	 * Construtor utilizado para modificar o caminho dos daos.
	 * 
	 * @param caminho caminho onde os objetos serão armezanados nos jsons
     */
	public DAO(String caminho) {
        this.CAMINHO = caminho;
    }
	
	/** 
	 * Instância do GSON configurada com formatação bonita (pretty printing). 
     */
    protected Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	/**
     * Lê o conteúdo do arquivo JSON e retorna como um JsonArray.
     * 
     * @return Um JsonArray com os dados lidos do arquivo ou vazio em caso de erro.
     */
    protected JsonArray lerJsonArray() {
        try (FileReader leitor = new FileReader(CAMINHO)) {
            JsonElement elem = JsonParser.parseReader(leitor);
            if (elem.isJsonArray()) {
                return elem.getAsJsonArray();
            }
        } catch (IOException e) {
            
        }
        return new JsonArray();
    }
    
    /**
     * Salva o conteúdo de um JsonArray no arquivo JSON.
     *
     * @param array O JsonArray com os dados a serem salvos.
     */
    protected void salvarJson(JsonArray array) {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(array, escritor);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo JSON!");
        }
    }
}
