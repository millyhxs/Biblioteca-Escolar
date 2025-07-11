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

import maven.Projeto.model.Usuario;

public class UsuarioDAO {
	
    private static final String CAMINHO = "listaDeUsuarios.json";
    private static List<Usuario> LISTA_DE_USUARIOS = new ArrayList<>();
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static void cadastrar(Usuario novoUsuario) {
        buscarArquivo();
        
        if (LISTA_DE_USUARIOS == null) {
            LISTA_DE_USUARIOS = new ArrayList<>();
        }
        
        for (Usuario usuario : LISTA_DE_USUARIOS) {
            if (usuario.getMatricula().equals(novoUsuario.getMatricula())) {
                System.out.println("Erro: Já existe um usuário com a matrícula \"" + novoUsuario.getMatricula() + "\".");
                return;
            }
        }
        
        LISTA_DE_USUARIOS.add(novoUsuario);
        atualizarJson();
    }
    
    public static void excluir(String matricula) {
        buscarArquivo();
        boolean excluiu = LISTA_DE_USUARIOS.removeIf(u -> u.getMatricula().equals(matricula));
        
        if (excluiu) {
            atualizarJson();
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    private static void buscarArquivo() {
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            LISTA_DE_USUARIOS = GSON.fromJson(leitor, tipoLista);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON.");
        }
    }
    
    private static void atualizarJson() {
    	try (FileWriter escritor = new FileWriter(CAMINHO)){
    		GSON.toJson(LISTA_DE_USUARIOS, escritor);
            System.out.println("JSON de usuários atualizado com sucesso!");
    	} catch (IOException e) {
    		System.out.println("Erro ao escrever no arquivo JSON.");
    	}
    }
    
    private static void editarUsuario(String matricula) {
    	buscarArquivo();
    	
    	if (LISTA_DE_USUARIOS == null) {
            LISTA_DE_USUARIOS = new ArrayList<>();
        }
    	
    	for (Usuario usuario : LISTA_DE_USUARIOS) {
            if (usuario.getMatricula().equals(matricula)) {
                return;
            }
        }
    }
  
}
