package maven.Projeto.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import maven.Projeto.model.Emprestimo;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Classe responsável pela manipulação dos dados das Devoluções.
 * Utiliza a biblioteca GSON para persistência em um arquivo JSON.
 * 
 * @author Millena
 */
public class EmprestimoDAO extends DAO{
	
	/**
	 * Construtor da superclasse DAO que define o CAMINHO dos registros.
	 */
    public EmprestimoDAO() {
		super("listaDeEmprestimos.json");
	}
    
    /** 
	 * Lista de Emprestimos carregados do arquivo JSON. 
     */
    private List<Emprestimo> LISTA_DE_EMPRESTIMOS = new ArrayList<>();
    
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
	 * Cadastra um novo empréstimo na lista e atualiza o arquivo JSON com a nova entrada.
	 *
	 * @param emprestimo O objeto {@link Emprestimo} a ser adicionado.
	 */
    public void cadastrar(Emprestimo emprestimo) {
        buscarArquivo();
        
        if (LISTA_DE_EMPRESTIMOS == null) {
            LISTA_DE_EMPRESTIMOS = new ArrayList<>();
        }
        
        LISTA_DE_EMPRESTIMOS.add(emprestimo);
        atualizarJson();
    }
    
    /**
     * Lê o conteúdo do arquivo JSON de empréstimos e atualiza a lista interna.
     * Se o arquivo não existir ou estiver vazio, inicializa uma lista vazia.
     */
    public void buscarArquivo() {
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Emprestimo>>() {}.getType();
            LISTA_DE_EMPRESTIMOS = GSON.fromJson(leitor, tipoLista);
            
            if (LISTA_DE_EMPRESTIMOS == null) {
                LISTA_DE_EMPRESTIMOS = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Arquivo de empréstimo não encontrado ou vazio.");
            LISTA_DE_EMPRESTIMOS = new ArrayList<>();
        }
    }
    
    /**
     * Salva a lista atual de empréstimos no arquivo JSON.
     */
    private void atualizarJson() {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DE_EMPRESTIMOS, escritor);
        } catch (IOException e) {
            System.out.println("Erro ao salvar lista de empréstimos.");
        }
    }
    
    /**
     * Remove todos os empréstimos da lista e atualiza o arquivo JSON.
     */
    public void limparTodosOsEmprestimos() {
        LISTA_DE_EMPRESTIMOS.clear();
        atualizarJson();
    }
    
    /**
     * Remove um empréstimo da lista com base no código da obra fornecido.
     *
     * @param codigoObra O código da obra cujo empréstimo deve ser removido.
     */
    public void excluirPorCodigo(String codigoObra) {
        buscarArquivo();
        boolean excluiu = LISTA_DE_EMPRESTIMOS.removeIf(e -> e.getCodigoObra().equals(codigoObra));
        if (excluiu) {
            atualizarJson();
        } else {
            System.out.println("Código não encontrado nos empréstimos.");
        }
    }
    
    /**
     * Calcula a multa associada a um empréstimo, com base nos dias permitidos e na taxa por dia.
     *
     * @param emprestimo O objeto {@link Emprestimo} para o qual a multa será calculada.
     * @param diasPermitidos O número de dias permitidos para o empréstimo sem multa.
     * @return O valor da multa calculada, retorna 0 caso não haja atraso.
     */
    public float calcularMultaParaEmprestimo(Emprestimo emprestimo, int diasPermitidos) {
        LocalDate dataEmprestimo = emprestimo.getDataEmprestimo();
        LocalDate dataAtual = LocalDate.now();
        
        long diasPassados = ChronoUnit.DAYS.between(dataEmprestimo, dataAtual);
        if (diasPassados > diasPermitidos) {
            long diasAtraso = diasPassados - diasPermitidos;
            float multaPorDia = emprestimo.getTaxaDaMulta();
            float valorMulta = diasAtraso * multaPorDia;
            return valorMulta;
        } else {
            return 0f;
        }
    }
    
    // Getters and Setters
    
    public List<Emprestimo> getLISTA_DE_EMPRESTIMOS() {
		return LISTA_DE_EMPRESTIMOS;
	}
    
	public void setLISTA_DE_EMPRESTIMOS(List<Emprestimo> lISTA_DE_EMPRESTIMOS) {
		LISTA_DE_EMPRESTIMOS = lISTA_DE_EMPRESTIMOS;
	}
	
	public List<Emprestimo> getEmprestimos() {
        buscarArquivo();
        if (LISTA_DE_EMPRESTIMOS == null) {
            LISTA_DE_EMPRESTIMOS = new ArrayList<>();
        }
        return LISTA_DE_EMPRESTIMOS;
    }
	
}
