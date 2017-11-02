package hr.infobip.urlservice.urls.resources;

import javax.servlet.http.HttpServletResponse;

public class Url {
		
	private final String url;
		
	private final int redirectType;
	
	public Url() {
		this.url = null;
		this.redirectType = 0;
	}

	public Url(String url, int code) {
		this.url = url;
		this.redirectType = code == HttpServletResponse.SC_MOVED_PERMANENTLY ? 
				HttpServletResponse.SC_MOVED_PERMANENTLY :
				HttpServletResponse.SC_FOUND;
	}

	public String getUrl() {
		return url;
	}

	public int getRedirectType() {
		return redirectType;
	}

	@Override
	public String toString() {
		return "UrlRegisterRequest [url=" + url + ", redirectType=" + redirectType + "]";
	}
	
}
