package crawler;

import java.net.URL;

public class Link {
	
	private int score;
	private URL url;
	private String anchorText;
	
	Link(URL url, String anchorText){
		score = 0;
		this.url = url;
		this.anchorText = anchorText;
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
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void setAnchorText(String anchorText){
		this.anchorText = anchorText;
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
