package org.rw.action.manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;
import org.rw.model.PasswordHash;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
/**
 * New User action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class NewUserAction extends ActionSupport implements UserAware, ModelDriven<User>, ServletResponseAware, ServletRequestAware{
	 
    private static final long serialVersionUID = 1L;
    private User user;
    private Map<String, Object> sessionAttributes = null;
    
    private String username;
    private String uriName;
    private String email;
    private String name;
    private String password;
    private String password2;
    
    @Override
    public String execute(){
    	
    	if(name != null)
    	{
	    	if(user == null && email != null && ApplicationStore.isValidEmail(email))
	    	{
	    		if(password != null && password2 != null && password.equals(password2)) 
	    		{
	    			// passwords match
	    			if(password.length() >= 8)
	    			{
	    				// password is long enough
	    				// check if user exists or not
	    				try {
	    					Connection conn = ApplicationStore.getConnection();
	    					Statement st = conn.createStatement();
	    					ResultSet rs = st.executeQuery("select * from users where email = '"+email+"'");
	    					
	    					if(!rs.next())
	    					{
	    						// user does not already exist.
	    						// add new user
	    						// salt and hash the password
	    						password = PasswordHash.createHash(password);
	    						
	    						int r = st.executeUpdate("insert into users (username,name,uri_name,email,create_date) values "
	    										+"('"+username+"','"+name+"','"+uriName+"','"+email+"','"+ApplicationStore.formatMySQLDate(new Date(System.currentTimeMillis())) + "')");
	    						
	    						ResultSet results = st.executeQuery("select user_id from users where email = '"+email+"'");
	    						String userId = "";
	    						if(results.next())
	    							userId = results.getString("user_id");
	    						
	    						int r2 = st.executeUpdate("insert into passwords (user_id,pwd) values "
	    										+"('"+userId+"','"+password+"')");
	    						
	    						if(r == 1 && r2 == 1 && !userId.isEmpty()) {
	    							
	    							// successfully registered new user!
	    								    							
	    							// get new user eid
	    							rs = st.executeQuery("select * from users where email = '"+email+"'");
	    							rs.next();
	    							// auto-login user
	    							user = new User();
	    				    		user.setEmail(email);
	    				    		user.setName(name);
	    				    		user.setFirstName(name.substring(0,name.indexOf(" ")));
	    				    		user.setUsername(username);
	    				    		user.setUriName(uriName);
	    				    		user.setUserId(userId);
	    				    		
	    				    		addActionMessage("Successfully created new Author.");	    				    		
	    				    		System.out.println("User created new author: "+username);	    							
	    							return SUCCESS;
	    						}
	    					}
	    					else
	    					{
	    						// user is already registered
	    						addActionError("Invalid Email Address.");
	    						System.out.println("User tried to register with a Email that already exists!");
	    					}
	    				} catch (Exception e) {
	    					addActionError(e.getMessage());
	    					e.printStackTrace();
	    				}
	    			}
	    			else
	    			{
	    				// password is too short
	    				addActionError("Password needs to be at least 8 characters long. Please try again.");
	    				System.out.println("User tried to register with a short password.");
	    			}
	    		}
	    		else
	    		{
	    			// passwords do not match
	    			addActionError("Passwords do not match. Please try again.");
	    			System.out.println("User tried to register with non-matching passwords.");
	    		}
	    	}
	    	else
	    	{
	    		// invalid Email Address
	    		addActionError("Invalid Email Address. Please try again.");
	    		System.out.println("User tried to register with an invalid Email Address.");
	    	}
    	}
    	else
    	{
    		// no submit yet
    		System.out.println("User opened register.");
    	}
        return ERROR;
    }
    
    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }
     
    @Override
    public User getModel() {
        return user;
    }

	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = ApplicationStore.removeBadChars(username);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = ApplicationStore.removeNonAsciiChars(name);
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = ApplicationStore.removeAllSpaces(uriName.trim().toLowerCase());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
     
}