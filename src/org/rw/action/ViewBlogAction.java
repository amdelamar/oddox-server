package org.rw.action;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.Post;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionSupport;

/**
 * View Blog list action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class ViewBlogAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// results
	private ArrayList<Post> posts = new ArrayList<Post>();
	
	public String execute() {
				
		// /blog
		
		// this shows the most recent blog posts
		try {
			// gather posts
			posts = ApplicationStore.getDatabaseSource().getPosts(1, 10, false);
			
			// set attributes
			servletRequest.setCharacterEncoding("UTF-8");
			servletRequest.setAttribute("posts", posts);
			
			return SUCCESS;
		
		} catch (Exception e) {
			addActionError("Error: "+e.getClass().getName()+". Please try again later.");
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * Return a cookie's value by its given name.
	 * @param cookieName
	 * @return Cookie
	 */
	public Cookie getCookie(String cookieName) {
		Cookie cookies[] = servletRequest.getCookies();
		Cookie myCookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					myCookie = cookies[i];
					break;
				}
			}
		}
		return myCookie;
	}
	
	/**
	 * Sets a cookie's value for the given name.
	 * @param cookieName
	 * @param cookieValue
	 */
	public void setCookie(String cookieName, String cookieValue) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		// cookie will last 1 year
		cookie.setMaxAge(60 * 60 * 24 * 365);
		servletResponse.addCookie(cookie);
	}

	protected HttpServletResponse servletResponse;

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	protected HttpServletRequest servletRequest;

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
}