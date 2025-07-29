package maven.Projeto.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import maven.Projeto.model.Devolucao;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe responsável pela manipulação dos dados das Devoluções.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author Hélder
 */
public class DevolucaoDAO extends DAO{
	
	/**
	 * Construtor da superclasse DAO que define o CAMINHO dos registros.
	 */
    public DevolucaoDAO() {
		super("listaDeDevolucoes.json");
		carregarDevolucoes();
	}
    
    /** 
	 * Lista de Devoluções carregadas do arquivo JSON. 
     */
    private List<Devolucao> LISTA_DEVOLUCOES = new ArrayList<>();
    
    /**
     * Instância personalizada de Gson configurada para:
     * 
     * Serializar objetos {@link LocalDate} no formato "dd/MM/yyyy".
     * Desserializar strings nesse formato de volta para {@link LocalDate}.
     * Gerar saída JSON com indentação legível (pretty printing).
     * 
     */
    private final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
        	@Override
        	public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        })
        .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        	@Override
        	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        })
        .setPrettyPrinting().create();
    	
    /**
     * Registra uma nova Devolução na lista de devoluções e salva no arquivo JSON.
     * Verifica se já existe uma Devolução com o mesmo código antes de cadastrar.
     *
     * @param devolucao A nova devolução a ser cadastrado.
     */
    public void registrarDevolucao(Devolucao devolucao) {
        LISTA_DEVOLUCOES.add(devolucao);
        atualizarJson();
    }
    
    /**
     * Retorna uma lista de devoluções realizadas por um usuário específico.
     *
     * @param matricula A matrícula do usuário cujas devoluções devem ser recuperadas.
     * @return Uma lista de objetos {@link Devolucao} associados à matrícula fornecida.
     */
    public List<Devolucao> getDevolucoesPorUsuario(String matricula) {
        List<Devolucao> resultado = new ArrayList<>();
        for (Devolucao d : LISTA_DEVOLUCOES) {
            if (d.getMatriculaUsuario().equals(matricula)) {
                resultado.add(d);
            }
        }
        return resultado;
    }
    
    /**
     * Salva a lista atual de Devoluções no arquivo json.
     */
    private void atualizarJson() {
    	try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DEVOLUCOES, escritor);
        } catch (IOException e) {
            System.out.println("Erro ao salvar devoluções: " + e.getMessage());
        }
    }
    
    /**
     * Carrega a lista de devoluções do arquivo json.
     */
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
    
    // Getters and Setters
    
    public List<Devolucao> getTodasDevolucoes() {
        carregarDevolucoes();
        return LISTA_DEVOLUCOES;
    }
    
}
