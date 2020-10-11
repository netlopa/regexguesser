package it.lorenzopavez.regexguesser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RegexGuesserTest {

	RegexGuesser regexGuesser = new RegexGuesser();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testItalianPlates() {
		List<String> plates = new ArrayList<String>();
		plates.add("AB123ZZ");
		plates.add("BB742TG");
		plates.add("CF678HG");
		
		String regex = "[A-Z]{2}\\d{3}[A-Z]{2}";
		String guessedRegex = regexGuesser.guessFromStringList(plates);
		
		assertEquals(regex,guessedRegex);
		
	}
	
	@Test
	public void testSomeCodes() {
		List<String> codes = new ArrayList<String>();
		codes.add("AA123");
		codes.add("BA1234");
		codes.add("AB12345");

		String regex = "[A-Z]{2}\\d{3,5}";
		String guessedRegex = regexGuesser.guessFromStringList(codes);
		
		assertEquals(regex,guessedRegex);
		
	}
	
	@Test
	public void testFiscalCodes() {
		List<String> fiscalCodes = new ArrayList<String>();
		fiscalCodes.add("TNTTST80A01F205E");
		fiscalCodes.add("CSALNZ87E11F205A");
		fiscalCodes.add("VCAASD99D01Z511D");
		
		String regex = "[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]";
		String guessedRegex = regexGuesser.guessFromStringList(fiscalCodes);
		
		assertEquals(regex,guessedRegex);
		
	}
	
	@Test
	public void testAnotherCodes() {
		List<String> codes = new ArrayList<String>();
		codes.add("A234BXXAZ");
		codes.add("A1C");
		codes.add("XFAFFFSSF1CUX");

		String regex = "[A-Z]{1,9}\\d{1,3}[A-Z]{1,5}";
		String guessedRegex = regexGuesser.guessFromStringList(codes);
		
		assertEquals(regex,guessedRegex);
	}

}
