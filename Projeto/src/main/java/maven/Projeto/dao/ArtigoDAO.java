package maven.Projeto.dao;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

import maven.Projeto.model.Artigo;

/**
 * Classe responsável pela manipulação dos dados dos Artigos.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author
 */
public class ArtigoDAO extends DAO{
	
	public ArtigoDAO() {
		super("listaDeObras.json");
	}
	/** Lista de "Arquivos" carregados do arquivo JSON. 
     */
    private List<Artigo> LISTA_DE_OBRAS = new ArrayList<>();

	/**
     * Cadastra um novo arquivo na lista de obras e salva no arquivo JSON.
     * Verifica se já existe um arquivo com o mesmo código antes de cadastrar.
     *
     * @param novaObra O novo Arquivo a ser cadastrado.
     */
    public void cadastrar(Artigo novaObra) {
        
        buscarArquivo();
        
        if (LISTA_DE_OBRAS == null) {
            LISTA_DE_OBRAS = new ArrayList<>();
        }
        
        for (Artigo artigo : LISTA_DE_OBRAS) {
            if (artigo.getCodigo().equals(novaObra.getCodigo())) {
                System.out.println("Erro: Já existe um artigo cadastrado com o código \"" + novaObra.getCodigo() + "\".");
                return;
            }
        }
        
        JsonObject obj = GSON.toJsonTree(novaObra).getAsJsonObject();
        obj.addProperty("tipo", "Artigo");
        
        JsonArray array = lerJsonArray();
        array.add(obj);
        salvarJson(array);
        
    }
    
    /**
     * Exclui um "arquivo" do arquivo JSON com base no seu código.
     *
     * @param codigo O código do arquivo a ser excluído.
     */
    public void excluir(String codigo) {
    	JsonArray array = lerJsonArray();
        boolean removido = false;
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Artigo".equals(obj.get("tipo").getAsString()) &&
                codigo.equals(obj.get("codigo").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        if (removido) {
        	salvarJson(array);
        } else {
            System.out.println("Artigo não encontrado");
        }
    }
    
    /**
     * Carrega os "Arquivos" do arquivo JSON para a lista em memória.
     */
    public void buscarArquivo() {
    	LISTA_DE_OBRAS = new ArrayList<>();
        JsonArray array = lerJsonArray();
        
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("tipo") && "Artigo".equals(obj.get("tipo").getAsString())) {
                Artigo artigo = GSON.fromJson(obj, Artigo.class);
                LISTA_DE_OBRAS.add(artigo);
            }
        }
    } 
    
    public List<Artigo> getLISTA_DE_OBRAS() {
		return LISTA_DE_OBRAS;
	}
    
	public void setLISTA_DE_OBRAS(List<Artigo> lISTA_DE_OBRAS) {
		LISTA_DE_OBRAS = lISTA_DE_OBRAS;
	}
	
}    