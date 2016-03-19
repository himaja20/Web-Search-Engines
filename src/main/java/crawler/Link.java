package crawler;

import java.net.URL;

public class Link {
	
	private int score;
	private final URL url;
	private final String anchorText;
	private final int id;
	private static int counter = 0;
	
	Link(URL url, String anchorText){
		score = 0;
		this.url = url;
		this.anchorText = anchorText;
	  this.id = ++counter;
	}
		
	public int getScore(){
		return this.score;
	}
	
	public URL getURL(){
		return this.url;
	}
	
	public String getAnchorText(){
		return this.anchorText;
	}
	
	
	public int getId(){
		return this.id;
	}
	public void setScore(int score){
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
