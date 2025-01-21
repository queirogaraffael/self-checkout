package com.gerenciador_estoque_fluxo_caixa.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ManipulacaoData {

	public static boolean verificaSeADataEPosterior(String data) {
		try {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dataFormatada = LocalDate.parse(data, formato);
			LocalDate dataAtual = LocalDate.now();

			return dataFormatada.isAfter(dataAtual);
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public static boolean verificaFormatoData(String stringData, String formatoData) {
		try {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoData);
			LocalDate.parse(stringData, formato);

			return true;
		} catch (DateTimeParseException e) {
			return false;
		}

	}

	public static LocalDate retornaLocalDate(String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return LocalDate.parse(data, formato);
	}

}
