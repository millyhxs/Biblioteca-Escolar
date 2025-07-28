package maven.Projeto.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import maven.Projeto.model.PagamentoMulta;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MultaDAO extends DAO{
    public MultaDAO() {
		super("listaDePagamentosDeMultas.json");
        carregarPagamentos();
	}
    
    private List<PagamentoMulta> LISTA_DE_PAGAMENTOS = new ArrayList<>();
    private int contadorId = 1;
    
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
            .setPrettyPrinting()
            .create();
    
    public void registrarPagamento(PagamentoMulta pagamento) {
        if (!LISTA_DE_PAGAMENTOS.contains(pagamento)) {
            if (pagamento.getId() == 0) {
                pagamento.setId(contadorId++);
            }
            
            LISTA_DE_PAGAMENTOS.add(pagamento);
            atualizarJson();
        }
    }
    
    public List<PagamentoMulta> getTodosPagamentos() {
        return new ArrayList<>(LISTA_DE_PAGAMENTOS); 
    }
    
    public List<PagamentoMulta> getPagamentosPorUsuario(String matricula) {
        List<PagamentoMulta> resultado = new ArrayList<>();
        for (PagamentoMulta p : LISTA_DE_PAGAMENTOS) {
            if (p.getMatriculaUsuario().equals(matricula)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    private void atualizarJson() {
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            GSON.toJson(LISTA_DE_PAGAMENTOS, escritor);
            System.out.println("Pagamentos de multa atualizados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar pagamentos de multa: " + e.getMessage());
        }
    }
    
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
}
