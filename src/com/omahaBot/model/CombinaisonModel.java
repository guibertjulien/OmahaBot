package com.omahaBot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.OnePairModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.utils.CardUtils;

/**
 * 5 cards (permutations of 2 hole cards and 3 board cards)
 * 
 * @author Julien
 * 
 */
@Data
public class CombinaisonModel extends CardPackModel implements Comparable<CombinaisonModel> {

	private Rank kicker;

	private final SortedSet<CardModel> permutationHand;

	private final SortedSet<CardModel> permutationBoard;

	private boolean hasFlushDraw;

	public CombinaisonModel(SortedSet<CardModel> permutationHand, SortedSet<CardModel> permutationBoard) {
		this.permutationHand = permutationHand;
		this.permutationBoard = permutationBoard;

		cards = new TreeSet<CardModel>();
		cards.addAll(permutationHand);
		cards.addAll(permutationBoard);

		// initHandPowerType();
	}

	public CombinaisonModel(List<CardModel> permutationHand, List<CardModel> permutationBoard, boolean hasFlushDraw) {
		this.permutationHand = new TreeSet<CardModel>(permutationHand);
		this.permutationBoard = new TreeSet<CardModel>(permutationBoard);
		this.hasFlushDraw = hasFlushDraw;

		cards = new TreeSet<CardModel>();
		cards.addAll(permutationHand);
		cards.addAll(permutationBoard);
	}

	public SortedSet<CardModel> getCards() {
		return cards;
	}

	public void setCards(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	/**
	 * TODO : best practices ?
	 */
	public ArrayList<DrawModel> initDraw() {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		DrawModel drawModel = null;

		if (!cards.isEmpty()) {
			if (hasFlushDraw) {
				listDraw.addAll(searchFlushDraw(4, 5, permutationHand));
			}

			drawModel = searchBestRankDraw();

			if (drawModel != null)
				listDraw.add(drawModel);
		}

		return listDraw;
	}

	public DrawModel searchBestRankDraw() {

		HandCategory boardCategory = HandCategory.UNKNOWN;
		DrawModel drawModel = null;

		String whithoutSuit = this.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutSuit);

		String group1 = "";
		String group2 = "";

		Rank rank1 = Rank.UNKNOWN;
		Rank rank2 = Rank.UNKNOWN;

		if (matcher.find()) {
			group1 = matcher.group(0);
			rank1 = Rank.fromShortName(String.valueOf(group1.charAt(0)));

			if (group1.length() == 8) {

				if (CardUtils.coupleIsPair(permutationHand)) {
					boardCategory = HandCategory.ONE_PAIR;
				}
				else {
					boardCategory = HandCategory.THREE_OF_A_KIND;
				}

				drawModel = new QuadsModel(rank1, boardCategory, permutationHand);
			} else if (group1.length() == 6) {
				if (matcher.find()) {
					group2 = matcher.group(0);
					rank2 = Rank.fromShortName(String.valueOf(group2.charAt(0)));
					drawModel = new FullModel(rank1, rank2, boardCategory, null, null, null, permutationHand);
				}
				else {
					drawModel = new SetModel(rank1, permutationHand);
				}
			} else if (group1.length() == 4) {
				if (matcher.find()) {
					group2 = matcher.group(0);
					rank2 = Rank.fromShortName(String.valueOf(group2.charAt(0)));

					if (group2.length() == 6) {
						drawModel = new FullModel(rank2, rank1, boardCategory, null, null, null, permutationHand);
					}
					else {
						drawModel = new TwoPairModel(rank2, rank1, permutationHand);
					}
				}
				else {
					drawModel = new OnePairModel(rank1, permutationHand);
				}
			}
		}

		return drawModel;
	}

	@Override
	public int compareTo(CombinaisonModel o) {
		return 1;
		// if (this.getHandPowerType().ordinal() <
		// o.getHandPowerType().ordinal()) {
		// return 1;
		// }
		// else if (this.getHandPowerType().ordinal() >
		// o.getHandPowerType().ordinal()) {
		// return -1;
		// }
		// else {
		// return 0;
		// }
	}

	@Override
	public String toString() {
		return "Combinaison : " + cards;
	}
}
