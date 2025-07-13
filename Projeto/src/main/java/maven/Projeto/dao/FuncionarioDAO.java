package maven.Projeto.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import maven.Projeto.model.Funcionario;

public class FuncionarioDAO {
	private static final String CAMINHO = "listaDeFuncionarios.json";
    private static List<Funcionario> LISTA_DE_FUNCIONARIOS = new ArrayList<>();
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
    
    private static void buscarArquivo() {
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
    
}
