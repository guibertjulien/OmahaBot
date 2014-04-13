package com.omaha.service.ai;

import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.omahaBot.enums.HandType;
import com.omahaBot.enums.PowerHandRank;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.HandModel;

public class handAnalyserServiceImplTest {

	@Test
	public void test1() {
		
		String handSorted = "256A";
		
		// 5c6dAs2h
		CardModel c1 = new CardModel(Rank.FIVE, Suit.CLUB);
		CardModel c2 = new CardModel(Rank.SIX, Suit.DIAMOND);
		CardModel c3 = new CardModel(Rank.ACE, Suit.SPADE);
		CardModel c4 = new CardModel(Rank.TWO, Suit.HEART);
		
		SortedSet<CardModel> cards = new TreeSet<CardModel>();
		
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
	
		HandModel handModel = new HandModel(cards);
		
		assertTrue(handModel.handRank().equals(handSorted));
		
		PowerHandRank powerHandRank = PowerHandRank.fromHand(handModel.handRank());
		
		assertTrue(powerHandRank.equals(PowerHandRank.NO_POWER));
	}
	
	@Test
	public void test2() {
		
		String handSorted = "22KK";
		
		// 5c6dAs2h
		CardModel c1 = new CardModel(Rank.TWO, Suit.CLUB);
		CardModel c2 = new CardModel(Rank.TWO, Suit.DIAMOND);
		CardModel c3 = new CardModel(Rank.KING, Suit.SPADE);
		CardModel c4 = new CardModel(Rank.KING, Suit.HEART);
		
		SortedSet<CardModel> cards = new TreeSet<CardModel>();
		
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
	
		HandModel handModel = new HandModel(cards);
		
		assertTrue(handModel.handRank().equals(handSorted));
		
		PowerHandRank powerHandRank = PowerHandRank.fromTypeAndHand(HandType.TWO_PAIR, handModel.handRank());
		
		assertTrue(powerHandRank.equals(PowerHandRank.TWO_PAIR_MEDIUM));
	}
	

	@Test
	public void test3() {
		boolean b = Pattern.matches("..AA", "22AA");
		assertTrue(b);
		
		b = Pattern.matches(".3AA", "22AA");
		assertTrue(!b);
		
		b = Pattern.matches("3.AA", "22AA");
		assertTrue(!b);

		Pattern p = Pattern.compile("(55){2}");
	    Matcher m = p.matcher("2556557");

	    assertTrue(m.find());
	     
		p = Pattern.compile("55");
	    m = p.matcher("2556");
		
	    assertTrue(m.find());
	}
}
