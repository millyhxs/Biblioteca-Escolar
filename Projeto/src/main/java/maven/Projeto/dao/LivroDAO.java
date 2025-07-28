package maven.Projeto.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import maven.Projeto.model.Livro;

/**
 * Classe responsável pela manipulação dos dados dos livros.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author
 */
public class LivroDAO extends DAO{
	
	public LivroDAO() {
		super("listaDeObras.json");
		// TODO Auto-generated constructor stub
	}
	
	/** Lista de livros carregados do arquivo JSON. 
     */
	private List<Livro> LISTA_DE_OBRAS = new ArrayList<>();
	
	/**
     * Cadastra um novo livro na lista de obras e salva no arquivo JSON.
     * Verifica se já existe um livro com o mesmo código antes de cadastrar.
     *
     * @param novaObra O novo livro a ser cadastrado.
     */
    public void cadastrar(Livro novaObra) {
		buscarArquivo();
        
        if (LISTA_DE_OBRAS == null) {
            LISTA_DE_OBRAS = new ArrayList<>();
        }
        
        for (Livro livro : LISTA_DE_OBRAS) {
            if (livro.getCodigo().equals(novaObra.getCodigo())) {
                System.out.println("Erro: Já existe um livro cadastrado com o código \"" + novaObra.getCodigo() + "\".");
                return;
            }
        }
        
        JsonObject obj = GSON.toJsonTree(novaObra).getAsJsonObject();
        obj.addProperty("tipo", "Livro");
        
        JsonArray array = lerJsonArray();
        array.add(obj);
        salvarJson(array);
    }
    
    /**
     * Exclui um livro do arquivo JSON com base no seu código.
     *
     * @param codigo O código do livro a ser excluído.
     */
    public void excluir(String codigo) {
    	JsonArray array = lerJsonArray();
        boolean removido = false;
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Livro".equals(obj.get("tipo").getAsString()) &&
                codigo.equals(obj.get("codigo").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        if (removido) {
        	salvarJson(array);
        } else {
            System.out.println("Livro não encontrado");
        }
    }
    
    /**
     * Carrega os livros do arquivo JSON para a lista em memória.
     */
    public void buscarArquivo() {
        LISTA_DE_OBRAS = new ArrayList<>();
        JsonArray array = lerJsonArray();
        
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("tipo") && "Livro".equals(obj.get("tipo").getAsString())) {
                Livro livro = GSON.fromJson(obj, Livro.class);
                LISTA_DE_OBRAS.add(livro);
            }
        }
    }   
    
    public List<Livro> getLISTA_DE_OBRAS() {
		return LISTA_DE_OBRAS;
	}
    
	public void setLISTA_DE_OBRAS(List<Livro> lISTA_DE_OBRAS) {
		LISTA_DE_OBRAS = lISTA_DE_OBRAS;
	}
}
