package maven.Projeto.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import maven.Projeto.model.Funcionario;

public class FuncionarioDAO {
	private static final String CAMINHO = "listaDeFuncionarios.json";
    public static List<Funcionario> LISTA_DE_FUNCIONARIOS = new ArrayList<>();
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static void cadastrar(Funcionario novoServidor) {
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
    public static void excluir(String matricula) {
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
    public static void buscarArquivo() {
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Funcionario>>() {}.getType();
            LISTA_DE_FUNCIONARIOS = GSON.fromJson(leitor, tipoLista);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON.");
        }
    }
    
    private static void atualizarJson() {
    	try (FileWriter escritor = new FileWriter(CAMINHO)){
    		GSON.toJson(LISTA_DE_FUNCIONARIOS, escritor);
            System.out.println("JSON de Servidores atualizado com sucesso!");
    	} catch (IOException e) {
    		System.out.println("Erro ao escrever no arquivo JSON.");
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

    public static Funcionario buscarPorIdESenha(String id, String senha) {
        buscarArquivo();
        
        if (LISTA_DE_FUNCIONARIOS != null) {
            for (Funcionario funcionario : LISTA_DE_FUNCIONARIOS) {
                if (funcionario.getId().equals(id) && funcionario.getSenha().equals(senha)) {
                    return funcionario;
                }
            }
        }
        return null;
    }
    
    
}
