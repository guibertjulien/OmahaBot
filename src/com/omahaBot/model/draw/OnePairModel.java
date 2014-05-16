package com.omahaBot.model.draw;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.Rank;

public @Data
class OnePairModel extends DrawModel {
	private final Rank rank;

	public OnePairModel(Rank rank) {
		super(DrawType.BEST_TWO_PAIR_DRAW, false);
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "One pairs of " + rank;
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof OnePairModel) {
			OnePairModel drawCompare = (OnePairModel) o;

			// compare rank
			if (this.rank.ordinal() > drawCompare.rank.ordinal()) {
				return -1;
			}
			else if (this.rank.ordinal() < drawCompare.rank.ordinal()) {
				return 1;
			} else {
				return 0;
			}
		}
		else {
			return super.compareTo(o);
		}
	}
}
