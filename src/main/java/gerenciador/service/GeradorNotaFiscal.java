package gerenciador.service;


import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class GeradorNotaFiscal {

	public static void geradorNotaFiscal(Venda venda, Set<ItemVenda> listaCompras, String caminho) {
		String codigo = String.valueOf(venda.getCodigo()) + ".txt";

		StringBuilder sb = new StringBuilder();

		sb.append(venda.toStringSemPreco()).append("\n");

		sb.append(ItemVendaService.geraRelatorioItemVenda(listaCompras));

		double total = venda.getTotal();

		sb.append("Total: ").append(total);

		File diretorio = new File(caminho);

		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}

		File arquivo = new File(diretorio, codigo);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
			bw.write(sb.toString());

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Diretorio não encontrado: " + caminho);
		}

	}

}
