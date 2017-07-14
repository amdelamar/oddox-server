package com.rant.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.model.Post;

/**
 * Tag action class
 * 
 * @author Austin Delamar
 * @date 3/19/2017
 */
public class TagAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private ArrayList<Post> posts;
    private String tag;
    private int page;
    private boolean nextPage;
    private boolean prevPage;

    public String execute() {

        // /tag

        // this shows the most recent blog posts by tag
        try {
            // jump to page if provided
            String pageTemp = servletRequest.getRequestURI().toLowerCase();
            if (pageTemp.startsWith("/tag/") && pageTemp.contains("/page/")) {
                tag = Utils.removeBadChars(pageTemp.substring(5, pageTemp.indexOf("/page")));
                pageTemp = Utils.removeBadChars(
                        pageTemp.substring(pageTemp.indexOf("/page/") + 6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else if (pageTemp.startsWith("/tag/")) {
                tag = Utils.removeBadChars(pageTemp.substring(5, pageTemp.length()));
                page = 1;
            }

            // gather posts
            posts = Application.getDatabaseSource().getPostsByTag(page, Application.getInt("limit"), tag, false);

            // determine pagination
            nextPage = posts.size() >= Application.getInt("limit");
            prevPage = page > 1;

            // set attributes
            servletRequest.setAttribute("posts", posts);
            servletRequest.setAttribute("page", page);
            servletRequest.setAttribute("nextPage", nextPage);
            servletRequest.setAttribute("prevPage", prevPage);

            return SUCCESS;

        } catch (NumberFormatException nfe) {
            return NONE;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = Utils.removeBadChars(tag);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isPrevPage() {
        return prevPage;
    }

    public void setPrevPage(boolean prevPage) {
        this.prevPage = prevPage;
    }
}