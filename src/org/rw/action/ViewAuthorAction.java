package org.rw.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.ArchiveAware;
import org.rw.bean.Post;
import org.rw.bean.RecentViewAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * View Author action class
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class ViewAuthorAction extends ActionSupport implements ArchiveAware, RecentViewAware, UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// post parameters
	private String uri_name;
	private String author;
	private String htmlContent;
	private String thumbnail;
	private String description;
	private String createDate;
	private String modifyDate;
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// /author/person-name-goes-here
		String  uri = servletRequest.getRequestURI();
		if(uri_name == null && uri.startsWith("/author/"))
			uri_name = ApplicationStore.removeBadChars(uri.substring(8,uri.length()));
		
		//System.out.println("URI: "+uri+" (Author = "+uri_name+")");
		
		if(uri_name != null && uri_name.length() > 0)
		{
			// search in db for author by title
			Connection conn = null;
			try {
				conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from users where uri_name = '"+uri_name+"'");
				
				if(rs.first() && uri_name.equals(rs.getString("uri_name"))) {
					// get the author properties
					@SuppressWarnings("unused")
					int user_id = rs.getInt("user_id");
					author = rs.getString("name");
					thumbnail = rs.getString("thumbnail");
					description = rs.getString("description");
					htmlContent = rs.getString("html_content");
					createDate = ApplicationStore.formatReadableDate(rs.getDate("create_date"));
					modifyDate = ApplicationStore.formatReadableDate(rs.getDate("modify_date"));
					
				}
				
				// was author found?
				if(author != null)
				{
					// yes, it is.
					System.out.println("User opened author page: "+uri_name);
					
					// set attributes
					servletRequest.setAttribute("author", author);
					servletRequest.setAttribute("uri_name", uri_name);
					servletRequest.setAttribute("htmlContent", htmlContent);
					servletRequest.setAttribute("description", description);
					servletRequest.setAttribute("createDate", createDate);
					servletRequest.setAttribute("modifyDate", modifyDate);
					
					
					// forward to appropriate JSP page
					//servletRequest.getRequestDispatcher("/WEB-INF/post/post.jsp").forward(servletRequest, servletResponse);
					
					return Action.SUCCESS;
				}
				else
				{
					System.out.println("User tried to open author: "+uri_name);
					addActionError("Author '"+uri_name+"' not found. Please try again.");
					return Action.NONE;
				}
			
			} catch (Exception e) {
				addActionError("Error: "+e.getClass().getName()+". Please try again later.");
				e.printStackTrace();
				return ERROR;
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {/*Do Nothing*/}
			}
		}
		else
		{
			addActionError("Author '"+uri_name+"' not found. Please try again.");
			return Action.NONE;
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

	public String getUri_name() {
		return uri_name;
	}

	public void setUri_name(String uri_name) {
		this.uri_name = uri_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String date) {
		this.createDate = date;
	}
	
	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public void setRecent_view(ArrayList<Post> recent_view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArchive_years(ArrayList<String> archive_years) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArchive_tags(ArrayList<String> archive_tags) {
		// TODO Auto-generated method stub
		
	}
}