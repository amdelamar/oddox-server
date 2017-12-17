package org.oddox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Blog list action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class BlogAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private List<Post> posts = null;
    private int page;
    private int nextPage;
    private int prevPage;

    /**
     * Returns list of blog posts.
     * 
     * @return Action String
     */
    public String execute() {

        // /blog

        // this shows the most recent blog posts
        try {
            // jump to page if provided
            String pageTemp = servletRequest.getRequestURI().toLowerCase();
            if (pageTemp.startsWith("/blog/page/")) {
                pageTemp = Utils.removeBadChars(pageTemp.substring(11, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else {
                page = 1;
            }

            // gather posts
            posts = Application.getDatabaseService().getPosts(page,
                    Application.getInt("default.limit"), false);

            // already sorted newest first

            // determine pagination
            if (posts != null) {
            	if(posts.size() >= Application.getInt("default.limit")) {
            		nextPage = page + 1;
            	} else {
            		nextPage = page;
            	}
            	if(page > 1) {
            		prevPage = page - 1;	
            	} else {
            		prevPage = page;
            	}
            }

            // set attributes
            servletRequest.setAttribute("posts", posts);
            servletRequest.setAttribute("page", page);
            servletRequest.setAttribute("nextPage", nextPage);
            servletRequest.setAttribute("prevPage", prevPage);

            return SUCCESS;

        } catch (NumberFormatException e) {
            System.err.println("Page not found. Please try again.");
            return Action.NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
}