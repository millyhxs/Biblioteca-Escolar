package maven.Projeto.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import maven.Projeto.model.Devolucao;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DevolucaoDAO extends DAO{
	
    public DevolucaoDAO() {
		super("listaDeDevolucoes.json");
		carregarDevolucoes();
	}

    private List<Devolucao> LISTA_DEVOLUCOES = new ArrayList<>();
    
    private final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        })
        .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        })
        .setPrettyPrinting()
        .create();
    
    public void registrarDevolucao(Devolucao devolucao) {
        LISTA_DEVOLUCOES.add(devolucao);
        atualizarJson();
    }
    
    public List<Devolucao> getTodasDevolucoes() {
        carregarDevolucoes();
        return LISTA_DEVOLUCOES;
    }
    
    public List<Devolucao> getDevolucoesPorUsuario(String matricula) {
        List<Devolucao> resultado = new ArrayList<>();
        for (Devolucao d : LISTA_DEVOLUCOES) {
            if (d.getMatriculaUsuario().equals(matricula)) {
                resultado.add(d);
            }
        }
        return resultado;
    }
    
    private void atualizarJson() {
    	try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DEVOLUCOES, escritor);
        } catch (IOException e) {
            System.out.println("Erro ao salvar devoluções: " + e.getMessage());
        }
    }
    
    private void carregarDevolucoes() {
        File arquivo = new File(CAMINHO);
        if (!arquivo.exists()) {
            LISTA_DEVOLUCOES = new ArrayList<>();
            return;
        }
        
        try (FileReader leitor = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<Devolucao>>() {}.getType();
            LISTA_DEVOLUCOES = GSON.fromJson(leitor, tipoLista);
            if (LISTA_DEVOLUCOES == null) LISTA_DEVOLUCOES = new ArrayList<>();
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar devoluções: " + e.getMessage());
            LISTA_DEVOLUCOES = new ArrayList<>();
        }
    }
}
