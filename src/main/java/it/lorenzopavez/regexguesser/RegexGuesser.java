package it.lorenzopavez.regexguesser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.lorenzopavez.regexguesser.entities.Token;
import it.lorenzopavez.regexguesser.entities.TokenType;

public class RegexGuesser {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Input filename is missing, please insert it as an argument");
			return;
		}
		try {
			File myObj = new File(args[0]);
			Scanner myReader = new Scanner(myObj);
			
			List<String> lines = new ArrayList<String>();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				lines.add(data);
			}
			myReader.close();
			
			RegexGuesser rg = new RegexGuesser();
			String regex = rg.guessFromStringList(lines);
			System.out.println("Regex guessed is:");
			System.out.println(regex);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred on retrieving the file.");
			e.printStackTrace();
		}

	}

	public String guessFromStringList(List<String> items) {

		/*
L'algoritmo non deve avere alcuna conoscenza a priori sul formato dei codici in input per cui
deve basarsi su quanche forma di analisi dell'input.

Ulteriori dati sui codici di input:
- sono composti da sole lettere [A-Z] e cifre \d
- hanno effettivamente una regolare alternanza di lettere/cifre, cioè non sono stringhe
  casuali o irregolari
- iniziano sempre con una lettera

		 */

		List<List<Token>> allTokens = new ArrayList<List<Token>>();

		for (String item: items) {
			List<Token> tokens = new ArrayList<Token>();	
			Token currToken = null;
			for (int i=0; i < item.length() ; i++ ) {

				if (Character.isLetter(item.charAt(i))) {
					//I need to evaluate the current token
					if (currToken == null || currToken.getTokenType().equals(TokenType.NUMERIC)) {
						currToken = new Token();
						currToken.setCharNum(1);
						currToken.setTokenType(TokenType.CHAR);
						tokens.add(currToken);
					}
					else {
						currToken.setCharNum(currToken.getCharNum()+1);
					}
				}
				else {
					if (currToken == null || currToken.getTokenType().equals(TokenType.CHAR)) {
						currToken = new Token();
						currToken.setCharNum(1);
						currToken.setTokenType(TokenType.NUMERIC);
						tokens.add(currToken);
					}
					else {
						currToken.setCharNum(currToken.getCharNum()+1);
					}
				}
			}

			allTokens.add(tokens);

		}

		// Since it's regular alternance of numbers / chars , I assume that all the strings has the same number of tokens (even if it's containing different amount of chars/num)
		List<Token> firstList = allTokens.get(0);
		String regex = "";
		for(int i=0; i < firstList.size() ; i++) {
			Integer minOccur = null;
			Integer maxOccur = null;
			for ( List<Token> consideredTokens : allTokens) {
				int num = consideredTokens.get(i).getCharNum();
				if (minOccur == null || num < minOccur) minOccur = num;
				if (maxOccur == null || num > maxOccur) maxOccur = num;
			}

			String regexPart="";

			//Eval if the chars are letter or num
			if (firstList.get(i).getTokenType().equals(TokenType.CHAR)) regexPart="[A-Z]";
			else regexPart = "\\d";

			//Eval if we have always the same amount of symbols (chars or num)
			if (minOccur == maxOccur) {
				if (minOccur > 1) regexPart=regexPart+"{"+minOccur+"}";
			}
			else regexPart=regexPart+ "{" + minOccur + "," + maxOccur + "}";

			regex=regex+regexPart;

		}

		return regex;
	}

}
