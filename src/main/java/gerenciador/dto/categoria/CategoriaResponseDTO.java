package gerenciador.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {

    private Integer id;
    private String nome;

    @Override
    public String toString() {
        return id + " - " + nome;
    }


}
