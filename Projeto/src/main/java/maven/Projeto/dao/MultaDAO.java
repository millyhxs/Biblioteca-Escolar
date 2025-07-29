package maven.Projeto.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import maven.Projeto.model.PagamentoMulta;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe responsável pela manipulação dos dados das Devoluções.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author Millena
 */
public class MultaDAO extends DAO{
    
	/**
	 * Construtor da superclasse DAO que define o CAMINHO dos registros.
	 */
	public MultaDAO() {
		super("listaDePagamentosDeMultas.json");
        carregarPagamentos();
	}
	
	/** 
	 * Lista de Pagamentos carregados do arquivo JSON. 
     */
    private List<PagamentoMulta> LISTA_DE_PAGAMENTOS = new ArrayList<>();
    
    /**
     * Contador responsável pelo ID dos pagamentos.
     */
    private int contadorId = 1;
    
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
     * Registra um novo Pagamento na lista de Pagamentos e salva no arquivo JSON.
     * Verifica se já existe um Pagamento com o mesmo código antes de cadastrar.
     *
     * @param pagamento O novo Pagamento a ser registrado.
     */
    public void registrarPagamento(PagamentoMulta pagamento) {
        if (!LISTA_DE_PAGAMENTOS.contains(pagamento)) {
            if (pagamento.getId() == 0) {
                pagamento.setId(contadorId++);
            }
            
            LISTA_DE_PAGAMENTOS.add(pagamento);
            atualizarJson();
        }
    }
    
    /**
     * Retorna todos os pagamentos de multa realizados por um usuário específico.
     *
     * @param matricula A matrícula do usuário cujos pagamentos devem ser recuperados.
     * @return Uma lista de objetos {@link PagamentoMulta} associados à matrícula fornecida.
     */
    public List<PagamentoMulta> getPagamentosPorUsuario(String matricula) {
        List<PagamentoMulta> resultado = new ArrayList<>();
        for (PagamentoMulta p : LISTA_DE_PAGAMENTOS) {
            if (p.getMatriculaUsuario().equals(matricula)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    /**
     * Salva a lista atual de Pagamentos no arquivo json.
     */
    private void atualizarJson() {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DE_PAGAMENTOS, escritor);
            System.out.println("Pagamentos de multa atualizados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar pagamentos de multa: " + e.getMessage());
        }
    }
    
    /**
     * Carrega a lista de Pagamentos do arquivo json.
     */
    private void carregarPagamentos() {
        File arquivo = new File(CAMINHO);
        if (!arquivo.exists()) {
            LISTA_DE_PAGAMENTOS = new ArrayList<>();
            return;
        }
        
        try (FileReader leitor = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<PagamentoMulta>>() {}.getType();
            LISTA_DE_PAGAMENTOS = GSON.fromJson(leitor, tipoLista);
            if (LISTA_DE_PAGAMENTOS == null) LISTA_DE_PAGAMENTOS = new ArrayList<>();
            
            for (PagamentoMulta p : LISTA_DE_PAGAMENTOS) {
                if (p.getId() >= contadorId) {
                    contadorId = p.getId() + 1;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar pagamentos de multa: " + e.getMessage());
            LISTA_DE_PAGAMENTOS = new ArrayList<>();
        }
    }
    
    // Getters and Setters
    
    public List<PagamentoMulta> getTodosPagamentos() {
        return new ArrayList<>(LISTA_DE_PAGAMENTOS); 
    }
}
