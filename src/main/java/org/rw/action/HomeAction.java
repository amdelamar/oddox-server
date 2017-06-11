package org.rw.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.Post;
import org.rw.config.Application;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Home action class
 * 
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class HomeAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private ArrayList<Post> posts;
    private ArrayList<Author> authors;
    private static final int PAGE = 1;
    private static final int LIMIT = 7;

    public String execute() {

        // /home

        // this shows the most recent blog posts
        try {
            // gather posts
            posts = Application.getDatabaseSource().getPosts(PAGE, LIMIT, false);

            // gather authors
            authors = Application.getDatabaseSource().getAuthors(PAGE, LIMIT, true);

            // set attributes
            servletRequest.setAttribute("posts", posts);
            servletRequest.setAttribute("authors", authors);

            return SUCCESS;

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

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }
}