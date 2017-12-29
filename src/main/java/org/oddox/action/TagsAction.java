package org.oddox.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.objects.Tag;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Tags action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class TagsAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private List<Tag> tags = null;

    /**
     * Returns list of tags.
     * 
     * @return Action String
     */
    public String execute() {

        // /tag
        try {
            // gather tags
            tags = Application.getDatabaseService()
                    .getTags();

            // sort alphabetically
            if (tags != null && !tags.isEmpty()) {
                Collections.sort(tags);
            } else {
                tags = null;
                throw new NullPointerException("No tags found");
            }

            // set attributes
            servletRequest.setAttribute("tags", tags);

            return SUCCESS;

        } catch (NullPointerException nfe) {
            return NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}