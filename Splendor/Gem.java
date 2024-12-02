package Splendor;

public class Gem implements Comparable<Gem> {
    private final String gemType;
    public Gem(String gemType) {
        this.gemType = gemType;
    }
    public String getGemType() {
        return gemType;
    }
    
	@Override
	public int compareTo(Gem o) {
		int orderNum = 0; // 0 = white, 1 = blue, 2 = green, 3 = red, 4 = black, 5 = wild
		switch(gemType) {
			case "White":
				orderNum = 0;
				break;
			case "Blue":
				orderNum = 1;
				break;
			case "Green":
				orderNum = 2;
				break;
			case "Red":
				orderNum = 3;
				break;
			case "Black":
				orderNum = 4;
				break;
			case "Wild":
				orderNum = 5;
				break;
		}
		
		int otherNum = 0;
		switch(o.getGemType()) {
			case "White":
				otherNum = 0;
				break;
			case "Blue":
				otherNum = 1;
				break;
			case "Green":
				otherNum = 2;
				break;
			case "Red":
				otherNum = 3;
				break;
			case "Black":
				otherNum = 4;
				break;
			case "Wild":
				otherNum = 5;
				break;
		}
		
		if (orderNum > otherNum) {
			return 1;
		} else if (orderNum < otherNum) {
			return -1;
		} else {
			return 0;
		}
	}
}