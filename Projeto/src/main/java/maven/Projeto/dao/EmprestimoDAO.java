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
        } else {
            System.out.println("Código não encontrado nos empréstimos.");
        }
    }
    
    public static float verificarMultaParaEmprestimo(Emprestimo emprestimo, int diasPermitidos) {
        LocalDate dataEmprestimo = emprestimo.getDataEmprestimo();
        LocalDate dataAtual = LocalDate.now();
        
        long diasPassados = ChronoUnit.DAYS.between(dataEmprestimo, dataAtual);
        
        if (diasPassados > diasPermitidos) {
            long diasAtraso = diasPassados - diasPermitidos;
            float multaPorDia = emprestimo.getTaxaDaMulta(); // continua sendo a taxa fixa por dia
            float valorMulta = diasAtraso * multaPorDia;
            return valorMulta;
        } else {
            return 0f; // Sem multa
        }
    }
}
