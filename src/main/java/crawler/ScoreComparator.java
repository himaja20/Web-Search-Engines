package crawler;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Link>{

	@Override
	public int compare(Link l1, Link l2) {
		if(l1.getScore() > l2.getScore()){
			return -1;
		}

		if(l1.getScore() < l2.getScore()){
			return 1;
		}

		if(l1.getId() > l1.getId()){
			return -1;
		}
		
		if (l1.getId() < l2.getId()){
			return 1;
		}
		return 0;
	}
}

