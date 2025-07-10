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

import maven.Projeto.model.Livro;

public class LivroDAO {
	
	private static final String CAMINHO = "listaDeLivros.json";
	
    public static void cadastrar(Livro novaObra) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Livro> listaDeObras = new ArrayList<>();
        
        // lê o conteúdo existente do arquivo JSON (se existir)
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Livro>>() {}.getType();
            listaDeObras = gson.fromJson(leitor, tipoLista);
            
            if (listaDeObras == null) {
                listaDeObras = new ArrayList<>();
            }
            
        } catch (IOException e) {
            // se o arquivo não existir ainda, criar um novo.
            listaDeObras = new ArrayList<>();
        }
       
        boolean codigoRepetido = false;
        for (Livro livro : listaDeObras) {
            if (livro.getCodigo().equals(novaObra.getCodigo())) {
                codigoRepetido = true;
                break;
            }
        }
        
        if (codigoRepetido) {
            System.out.println("Erro: Já existe um livro cadastrado com o código \"" + novaObra.getCodigo() + "\".");
            return;
        }
     
        listaDeObras.add(novaObra);
        
        // grava a lista atualizada de volta no JSON
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            gson.toJson(listaDeObras, escritor);
            System.out.println("Cadastro concluído!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo JSON!");
        }
    }
    
    public static void excluir(String codigo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Livro> listaDeObras = new ArrayList<>();
        
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Livro>>() {}.getType();
            listaDeObras = gson.fromJson(leitor, tipoLista);
            
            if (listaDeObras == null) {
                listaDeObras = new ArrayList<>();
            } else {
                listaDeObras.removeIf(r -> r == null || r.getCodigo() == null);
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo. Nenhum livro foi carregado.");
            return;
        }
        
        // Verifica se a revista com o código existe e remove
        boolean removido = listaDeObras.removeIf(livro -> codigo.equals(livro.getCodigo()));
        
        if (removido) {
            // Salva a lista atualizada
            try (FileWriter escritor = new FileWriter(CAMINHO)) {
                gson.toJson(listaDeObras, escritor);
                System.out.println("Livro com código \"" + codigo + "\" foi excluído com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo JSON!");
            }
        } else {
            System.out.println("Erro: Nenhum livro encontrada com o código \"" + codigo + "\".");
        }
    }
}
