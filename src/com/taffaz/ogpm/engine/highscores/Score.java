package com.taffaz.ogpm.engine.highscores;

import java.io.Serializable;

public class Score  implements Serializable, Comparable<Score> {
        private int score;
        private String name;

        public int getScore() {
                return score;
        }

        public String getName() {
                return name;
        }

        public Score(String name, int score) {
                this.score = score;
                this.name = name;
        }

		@Override
		public int compareTo(Score score1) {
			return ((Integer)(score1.getScore())).compareTo(getScore());
		}
}