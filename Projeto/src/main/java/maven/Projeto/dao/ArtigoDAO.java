package maven.Projeto.dao;

//Bibliotecas

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import maven.Projeto.model.Artigo;

public class ArtigoDAO {
	
	private static final String CAMINHO = "listaDeObras.json";
	private static List<Artigo> LISTA_DE_OBRAS = new ArrayList<>();
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static void cadastrar(Artigo novaObra) {
        buscarArquivo();
        
        if (LISTA_DE_OBRAS == null) {
            LISTA_DE_OBRAS = new ArrayList<>();
        }
        
        for (Artigo artigo : LISTA_DE_OBRAS) {
            if (artigo.getCodigo().equals(novaObra.getCodigo())) {
                System.out
                        .println("Erro: Já existe um artigo cadastrado com o código \"" + novaObra.getCodigo()
                                + "\".");
                return;
            }
        }
        
        LISTA_DE_OBRAS.add(novaObra);
        
        atualizarJson();
    }
    
    public static void excluir(String codigo) {
        buscarArquivo();
        boolean excluiu = LISTA_DE_OBRAS.removeIf(artigo -> artigo.getCodigo().equals(codigo));
        
        if (excluiu) {
            atualizarJson();
            System.out.println("Artigo excluído");
        } else {
            System.out.println("Artigo não encontrado");
        }
    }
    
    private static void buscarArquivo() {
        try {
            FileReader leitor = new FileReader(CAMINHO);
            Type tipoLista = new TypeToken<List<Artigo>>() {}.getType();
            
            LISTA_DE_OBRAS = GSON.fromJson(leitor, tipoLista);
        } catch (IOException e) {
            System.out.println("Erro ao adicionar obra no arquivo JSON!");
        }
    }
    
    private static void atualizarJson() {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DE_OBRAS, escritor);
            System.out.println("Arquivo JSON atualizado!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo JSON!");
        }
    }
    public static List<Artigo> getArtigos() {
        buscarArquivo();
        return LISTA_DE_OBRAS;
    }
}
