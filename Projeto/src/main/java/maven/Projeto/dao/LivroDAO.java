package maven.Projeto.dao;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

import maven.Projeto.model.Livro;

public class LivroDAO {
	
    private static final String CAMINHO = "listaDeObras.json";
    public static List<Livro> LISTA_DE_OBRAS = new ArrayList<>();
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static void cadastrar(Livro novaObra) {
        buscarArquivo();
        
        if (LISTA_DE_OBRAS == null) {
            LISTA_DE_OBRAS = new ArrayList<>();
        }
        
        for (Livro livro : LISTA_DE_OBRAS) {
            if (livro.getCodigo().equals(novaObra.getCodigo())) {
                System.out
                        .println("Erro: Já existe um livro cadastrado com o código \"" + novaObra.getCodigo()
                                + "\".");
                return;
            }
        }

        JsonObject obj = GSON.toJsonTree(novaObra).getAsJsonObject();
        obj.addProperty("tipo", "Livro");

        JsonArray array = lerJsonArray();
        array.add(obj);
        salvarJson(array);

        System.out.println("Livro cadastrado com sucesso!");
    }
    
    public static void excluir(String codigo) {
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
            System.out.println("Livro excluído");
        } else {
            System.out.println("Livro não encontrado");
        }
    }
    
    public static void buscarArquivo() {
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
    
    private static JsonArray lerJsonArray() {
        try (FileReader leitor = new FileReader(CAMINHO)) {
            JsonElement elem = JsonParser.parseReader(leitor);
            if (elem.isJsonArray()) {
                return elem.getAsJsonArray();
            }
        } catch (IOException e) {
      
        }
        return new JsonArray();
    }

    private static void salvarJson(JsonArray array) {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(array, escritor);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo JSON!");
        }
    }
    
}    