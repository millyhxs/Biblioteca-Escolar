package maven.Projeto.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import maven.Projeto.model.Devolucao;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ContadorController {
	public static void main(String[] args) {
        try {
        	Gson gson = new GsonBuilder()
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
        		    .create();

            Type tipoLista = new TypeToken<List<Devolucao>>() {}.getType();
            FileReader leitor = new FileReader("listaDeDevolucoes.json");
            
            List<Devolucao> devolucoes = gson.fromJson(leitor, tipoLista);
            
            Map<String, Integer> contador = new HashMap<>();
            
            for (Devolucao d : devolucoes) {
                String codigo = d.getCodigoObra();
                contador.put(codigo, contador.getOrDefault(codigo, 0) + 1);
            }
            
            for (Map.Entry<String, Integer> entrada : contador.entrySet()) {
                System.out.println("Código da Obra: " + entrada.getKey() + " | Quantidade: " + entrada.getValue());
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao contar códigos: " + e.getMessage());
        }
    }
}
