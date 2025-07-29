package maven.Projeto.dao;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

import maven.Projeto.model.Revista;

/**
 * Classe responsável pela manipulação dos dados das revistas.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author Millena
 */
public class RevistaDAO extends DAO{
	
	/**
	 * Construtor da superclasse DAO que define o CAMINHO dos registros.
	 */
	public RevistaDAO() {
		super("listaDeObras.json");
	}
    
	/** Lista de "Revistas" carregados do arquivo JSON. 
     */
	private List<Revista> LISTA_DE_OBRAS = new ArrayList<>();
	
	/**
     * Cadastra uma nova Revista na lista de obras e salva no arquivo JSON.
     * Verifica se já existe uma Revista com o mesmo código antes de cadastrar.
     *
     * @param novaObra A nova Revista a ser cadastrada.
     */
	public void cadastrar(Revista novaObra) {
        
        buscarArquivo();
        
        if (LISTA_DE_OBRAS == null) {
            LISTA_DE_OBRAS = new ArrayList<>();
        }
        
        for (Revista revista : LISTA_DE_OBRAS) {
            if (revista.getCodigo().equals(novaObra.getCodigo())) {
                System.out.println("Erro: Já existe uma revista cadastrado com o código \"" + novaObra.getCodigo() + "\".");
                return;
            }
        }
        
        JsonObject obj = GSON.toJsonTree(novaObra).getAsJsonObject();
        obj.addProperty("tipo", "Revista");
        
        JsonArray array = lerJsonArray();
        array.add(obj);
        salvarJson(array);
    }
    
	/**
     * Exclui uma Revista do arquivo JSON com base no seu código.
     *
     * @param codigo O código da Revista a ser excluída.
     */
    public void excluir(String codigo) {
    	JsonArray array = lerJsonArray();
        boolean removido = false;
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Revista".equals(obj.get("tipo").getAsString()) &&
                codigo.equals(obj.get("codigo").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        if (removido) {
        	salvarJson(array);
        } else {
            System.out.println("Revista não encontrada");
        }
    }
    
    /**
     * Carrega as Revistas do arquivo JSON para a lista em memória.
     */
    public void buscarArquivo() {
    	LISTA_DE_OBRAS = new ArrayList<>();
        JsonArray array = lerJsonArray();
        
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("tipo") && "Revista".equals(obj.get("tipo").getAsString())) {
                Revista revista = GSON.fromJson(obj, Revista.class);
                LISTA_DE_OBRAS.add(revista);
            }
        }
    }
    
    // Getters and Setters
    
    public List<Revista> getLISTA_DE_OBRAS() {
		return LISTA_DE_OBRAS;
	}
    
	public void setLISTA_DE_OBRAS(List<Revista> lISTA_DE_OBRAS) {
		LISTA_DE_OBRAS = lISTA_DE_OBRAS;
	}
    
}    