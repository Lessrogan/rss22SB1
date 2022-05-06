package fr.univrouen.rss22.client;

public enum SpecialCharacter {

	EACUTE("&eacute;", "é"), EGRAVE("&egrave;", "è"), ECAPSACUTE("&Eacute;", "É"), ECAPSGRAVE("&Egrave;", "È"),
	EUML("&euml;", "ë"), ECAPSUML("&Euml;", "Ë"), ECIRC("&ecirc;", "ê"), ECAPSCIRC("&Ecirc;", "Ê"),
	AGRAVE("&agrave;", "à"), ACAPSGRAVE("&Agrave;", "À"), IUML("&iuml;", "ï"), ICAPSUML("&Iuml;", "Ï"),
	UGRAVE("&ugrave;", "ù"), UCAPSGRAVE("&Ugrave;", "Ù"); 
	
	private String from;
	private String to;
	
	SpecialCharacter(String html, String character) {
		from = html;
		to = character;
	}
	
	public static String replaceHTMLCharactersInString(String str) {
		String text = str;
		for (SpecialCharacter specialCharacter : SpecialCharacter.values()) {
			text = text.replace(specialCharacter.from, specialCharacter.to);
		}
		return text;
	}
}
