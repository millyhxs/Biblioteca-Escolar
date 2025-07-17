package maven.Projeto.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import maven.Projeto.model.Emprestimo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {
	
    private static final String CAMINHO = "listaDeEmprestimos.json";
    private static List<Emprestimo> LISTA_DE_EMPRESTIMOS = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder()
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
    	    .setPrettyPrinting()
    	    .create();
    
    public static void cadastrar(Emprestimo emprestimo) {
        buscarArquivo();
        
        if (LISTA_DE_EMPRESTIMOS == null) {
            LISTA_DE_EMPRESTIMOS = new ArrayList<>();
        }
        
        LISTA_DE_EMPRESTIMOS.add(emprestimo);
        atualizarJson();
    }
    
    public static List<Emprestimo> getEmprestimos() {
        buscarArquivo();
        if (LISTA_DE_EMPRESTIMOS == null) {
            LISTA_DE_EMPRESTIMOS = new ArrayList<>();
        }
        return LISTA_DE_EMPRESTIMOS;
    }
    
    private static void buscarArquivo() {
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
    
    private static void atualizarJson() {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DE_EMPRESTIMOS, escritor);
            System.out.println("Lista de empréstimos atualizada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar lista de empréstimos.");
        }
    }
    
    public static void limparTodosOsEmprestimos() {
        LISTA_DE_EMPRESTIMOS.clear();
        atualizarJson();
    }
    
    public static void excluirPorCodigo(String codigoObra) {
        buscarArquivo();
        boolean excluiu = LISTA_DE_EMPRESTIMOS.removeIf(e -> e.getCodigoObra().equals(codigoObra));
        if (excluiu) {
            atualizarJson();
            System.out.println("Empréstimo removido.");
        } else {
            System.out.println("Código não encontrado nos empréstimos.");
        }
    }
    
    public static void verificarMultaParaEmprestimo(Emprestimo emprestimo, int diasPermitidos) {
        LocalDate dataEmprestimo = emprestimo.getDataEmprestimo();
        LocalDate dataAtual = LocalDate.now();
        
        long diasPassados = java.time.temporal.ChronoUnit.DAYS.between(dataEmprestimo, dataAtual);
        
        if (diasPassados > diasPermitidos) {
            long diasAtraso = diasPassados - diasPermitidos;
            float multaPorDia = emprestimo.getTaxaDaMulta();
            emprestimo.setTaxaDaMulta(diasAtraso * multaPorDia);
        } else {
            emprestimo.setTaxaDaMulta(0f); 
        }
    }
}
