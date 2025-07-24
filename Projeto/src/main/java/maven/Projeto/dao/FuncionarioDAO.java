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
import maven.Projeto.model.Leitor;

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
    
    public static void editarUsuario(String id, Funcionario novosDados) {
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
    
    public static void excluir(String matricula) {
    	JsonArray array = lerJsonArray();
        boolean removido = false;
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Administrador".equals(obj.get("tipo").getAsString()) &&
                matricula.equals(obj.get("id").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Estagiário".equals(obj.get("tipo").getAsString()) &&
                matricula.equals(obj.get("id").getAsString())) {
                array.remove(i);
                removido = true;
                break;
            }
        }
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            if ("Bibliotecário".equals(obj.get("tipo").getAsString()) &&
                matricula.equals(obj.get("id").getAsString())) {
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
                	funcionario.setAtivo(true);
                    atualizarJson();
                    return funcionario;
                }
            }
        }
        return null;
    }
    
    public static Funcionario buscarFuncionarioAtivo() {
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
    
    public static void deslogarFuncionarioAtivo() {
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
    
}
