package com.omahaBot.model.hand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.enums.preFlop.PreFlopPairLevel;
import com.omahaBot.enums.preFlop.PreFlopStraightLevel;
import com.omahaBot.enums.preFlop.PreFlopSuitLevel;
import com.omahaBot.model.StringPattern;

@Data
public class HandPreFlopPower {

	private static String PROPERTY_FILE_NAME = "config.properties";

	private final HandModel handModel;

	private ArrayList<HandPair> pairs = new ArrayList<HandPair>();

	private ArrayList<HandSuit> suits = new ArrayList<HandSuit>();

	private int bestHandLevel = -1;

	private int power = -1;

	private SuitedType suitedType;

	private PairType pairType;

	private PreFlopPairLevel preFlopPairLevel = PreFlopPairLevel.NO_PAIR;

	private PreFlopSuitLevel preFlopSuitLevel = PreFlopSuitLevel.UNSUITED;

	private PreFlopStraightLevel preFlopStraightLevel = PreFlopStraightLevel.NO_CONNECTOR;

	public enum PairType {
		NO_PAIR, ONE_PAIR, DOUBLE_PAIR
	}

	public enum SuitedType {
		UNSUITED, ONE_SUIT, DOUBLE_SUITED
	}

	public HandPreFlopPower(HandModel handModel) {
		super();
		this.handModel = handModel;

		analyse();
	}

	private void analyse() {

		initPairs();
		initSuits();
		preFlopStraightLevel = PreFlopStraightLevel.fromTypeAndHand(handModel.toRankString());

		checkTrashHand();

		if (!isTrashHand()) {
			checkIfBestHand();
		}

		power = preFlopPairLevel.getPowerPoint().getPoint() + preFlopSuitLevel.getPowerPoint().getPoint()
				+ preFlopStraightLevel.getPowerPoint().getPoint();
	}

	private void initPairs() {
		String whithoutSuit = handModel.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutSuit);

		while (matcher.find()) {
			String group = matcher.group(0);

			if (group.length() == 4) {
				Rank rank = Rank.fromShortName(group.charAt(0));
				pairs.add(new HandPair(rank));
			}
			else {
				// TODO
			}
		}

		if (pairs.size() == 2) {
			pairType = PairType.DOUBLE_PAIR;
			preFlopPairLevel = PreFlopPairLevel.fromTypeAndHand(pairType, handModel.toRankString());
		} else if (pairs.size() == 1) {
			pairType = PairType.ONE_PAIR;
			preFlopPairLevel = PreFlopPairLevel.fromTypeAndHand(pairType, handModel.toRankString());
		} else {
			pairType = PairType.NO_PAIR;
			preFlopPairLevel = PreFlopPairLevel.NO_PAIR;
		}
	}

	private void initSuits() {
		String whithoutRank = handModel.toStringBySuit().replaceAll("[^shdc]", ".");

		Pattern pattern = Pattern.compile("(.\\w)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutRank);

		while (matcher.find()) {
			String group = matcher.group(0);
			String drawString = handModel.toStringBySuit().substring(matcher.start(), matcher.end());

			if (group.length() >= 4) {
				Suit suit = Suit.fromShortName(group.charAt(1));
				Rank kicker = Rank.fromShortName(drawString.charAt(drawString.length()-2));
				suits.add(new HandSuit(suit, kicker));
			}
			else {
				// TODO
			}
		}

		if (suits.size() == 2) {
			suitedType = SuitedType.DOUBLE_SUITED;
			preFlopSuitLevel = PreFlopSuitLevel.fromTypeAndKicker(suitedType, suits);
		} else if (suits.size() == 1) {
			suitedType = SuitedType.ONE_SUIT;
			preFlopSuitLevel = PreFlopSuitLevel.fromTypeAndKicker(suitedType, suits);
		} else {
			suitedType = SuitedType.UNSUITED;
			preFlopSuitLevel = PreFlopSuitLevel.UNSUITED;
		}
	}

	private void checkIfBestHand() {

		try {
			Properties properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));

			List<String> bestHands = new ArrayList<String>(
					Arrays.asList(properties.getProperty("bestHands").split(",")));

			List<StringPattern> bestHandsPattern = new ArrayList<StringPattern>();
			for (String pattern : bestHands) {
				bestHandsPattern.add(new StringPattern(pattern));
			}

			StringPattern rankString = new StringPattern(handModel.toRankString());

			Predicate<StringPattern> isBestHand = (p) -> p.equals(rankString);

			java.util.Optional<StringPattern> handExist =
					bestHandsPattern.stream()
							.filter(isBestHand)
							.findAny();

			if (handExist.isPresent()) {
				for (int i = 0; i < bestHandsPattern.size(); i++) {
					if (bestHandsPattern.get(i).equals(rankString)) {
						bestHandLevel = i + 1;
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	private void checkTrashHand() {
		if ((handModel.isFourOfAKind() || handModel.isThreeOfAKind())
				|| (preFlopPairLevel.equals(PreFlopPairLevel.NO_PAIR)
						&& preFlopSuitLevel.equals(PreFlopSuitLevel.UNSUITED)
						&& preFlopStraightLevel.equals(PreFlopStraightLevel.NO_CONNECTOR))) {
			bestHandLevel = 0;
		}
	}

	public boolean isBestHand() {
		return (bestHandLevel > 0);
	}

	public boolean isTrashHand() {
		return (bestHandLevel == 0);
	}

	public boolean isDoubledSuited() {
		return (suitedType.equals(SuitedType.DOUBLE_SUITED));
	}
}
