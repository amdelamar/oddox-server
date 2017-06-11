package org.rw.action.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.UserAware;
import org.rw.config.Application;
import org.rw.config.Utils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Edit Author action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class EditUserAction extends ActionSupport
        implements
            UserAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private Author user;

    // post parameters
    private Author author;
    private int id;
    private String uri;

    // updated values
    private String name;
    private String uriName;

    private String thumbnail;
    private String description;
    private String htmlContent;

    public String execute() {

        // /manage/editpost/user-name-goes-here
        // this allows blog posts to be shown without parameter arguments (i.e. ?uri=foobar&test=123
        // )
        String uriTemp = servletRequest.getRequestURI();
        if (uri == null && uriTemp.startsWith("/manage/edituser/")) {
            uri = Utils.removeBadChars(uriTemp.substring(17, uriTemp.length()));
        }

        if (servletRequest.getParameter("delete") != null) {
            // they've requested to delete a user
            try {
                author = new Author(id);
                if (Application.getDatabaseSource().deleteAuthor(author)) {
                    // Success
                    System.out.println("User " + user.getUsername() + " deleted user: " + uri);
                    addActionMessage("The author was deleted!");
                    return SUCCESS;
                } else {
                    // failed to delete user
                    addActionError("Oops. Failed to delete the user. Please try again.");
                    return ERROR;
                }

            } catch (Exception e) {
                addActionError("An error occurred: " + e.getMessage());
                e.printStackTrace();
                return ERROR;
            }
        } else if (servletRequest.getParameter("submitForm") != null) {
            // they've submitted an edit on a user
            try {
                // check if uri exists or not
                Author existingUser = Application.getDatabaseSource().getAuthor(uriName);

                if (existingUser.getId() != id) {
                    // URI was not unique. Please try again.
                    addActionError(
                            "URI is not unique. Its being used by another author. Please change it, and try again.");
                    System.out.println("URI was not unique.");
                    return ERROR;
                }

                // save fields into object
                author = new Author(id);
                author.setUriName(uriName);
                author.setName(name);
                author.setDescription(description);
                author.setThumbnail(thumbnail);
                author.setHtmlContent(htmlContent);

                // update author in database
                if (Application.getDatabaseSource().editAuthor(author)) {
                    // Success
                    System.out.println("User " + user.getUsername()
                            + " saved changes to the author: " + uriName);
                    addActionMessage("Successfully saved changes to the author.");
                    return SUCCESS;
                }
                {
                    // failed to update
                    addActionError("Oops. Failed to update author. Please try again.");
                    System.out.println("Failed to update author. " + uriName);
                    return ERROR;
                }
            } catch (Exception e) {
                addActionError("An error occurred: " + e.getMessage());
                e.printStackTrace();
                return ERROR;
            }
        } else {
            if (uri != null && uri.length() > 0) {
                // search in db for user by name
                try {
                    author = Application.getDatabaseSource().getAuthor(uri);

                    // was author found
                    if (author != null) {
                        // set attributes
                        servletRequest.setAttribute("author", author);

                        System.out.println(
                                "User " + user.getUsername() + " opened author to edit: " + uri);
                        return Action.INPUT;
                    } else {
                        System.err.println("Author '" + uri + "' not found. Please try again.");
                        return Action.NONE;
                    }

                } catch (Exception e) {
                    addActionError(
                            "Error: " + e.getClass().getName() + ". Please try again later.");
                    e.printStackTrace();
                    return ERROR;
                }
            } else {
                System.err.println("User '" + uri + "' not found. Please try again.");
                return Action.NONE;
            }
        }
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected HttpServletResponse servletResponse;

    protected HttpServletRequest servletRequest;

    @Override
    public void setUser(Author user) {
        this.user = user;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title.trim();
    }

    public String getUriName() {
        return uriName;
    }

    public void setUriName(String uriName) {
        this.uriName = Utils.removeAllSpaces(uriName.trim().toLowerCase());
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = Utils.removeNonAsciiChars(thumbnail.trim());
    }

    public String getDescription() {
        return description.trim();
    }

    public void setDescription(String description) {
        this.description = Utils.removeNonAsciiChars(description.trim());
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}