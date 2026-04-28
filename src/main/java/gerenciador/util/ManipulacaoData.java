package gerenciador.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ManipulacaoData {

	public static String formatoData = "dd/MM/yyyy";

	public static boolean verificaSeADataEPosterior(String data) {
		try {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoData);
			LocalDate dataFormatada = LocalDate.parse(data, formato);
			LocalDate dataAtual = LocalDate.now();

			return dataFormatada.isAfter(dataAtual);
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public static boolean verificaFormatoData(String stringData) {
		try {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoData);
			LocalDate.parse(stringData, formato);

			return true;
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public static LocalDate retornaLocalDate(String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoData);

		return LocalDate.parse(data, formato);
	}

}
