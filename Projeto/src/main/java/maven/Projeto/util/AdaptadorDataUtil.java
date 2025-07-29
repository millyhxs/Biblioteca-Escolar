package maven.Projeto.util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária que adapta o formato de datas (LocalDate) para serialização e desserialização com Gson.
 * Converte LocalDate para o formato "dd/MM/yyyy" ao salvar em JSON, e reconverte ao ler o JSON.
 * 
 * @author Hélder
 */
public class AdaptadorDataUtil implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
	
	 // Define o formato de data utilizado no projeto: dia/mês/ano
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Converte um objeto LocalDate em uma string no formato definido, para ser salva no JSON.
     */
    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(localDate.format(formatter));
    }
    
    /**
     * Converte uma string de data presente no JSON em um objeto LocalDate.
     */
    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(jsonElement.getAsString(), formatter);
    }
}