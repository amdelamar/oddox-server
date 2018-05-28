package org.oddox.action;

import org.oddox.MainVerticle;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * View Post action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class PostAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(PostAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private Post post;
    private String uri;

    /**
     * Returns blog post details.
     */
    @Override
    public void handle(RoutingContext context) {

        // /blog/post-name
        String templateFile = "blog/post.ftl";
        String uriTemp = context.normalisedPath()
                .toLowerCase();
        if (uri == null && uriTemp.startsWith("/blog/post/")) {
            // /blog/post/post-name-goes-here
            uri = Utils.removeBadChars(uriTemp.substring(11, uriTemp.length()));
        } else if (uri == null && uriTemp.startsWith("/blog/")) {
            // /blog/post-name-goes-here
            uri = Utils.removeBadChars(uriTemp.substring(6, uriTemp.length()));
        }

        if (uri != null && uri.length() > 0) {
            // lower-case no matter what
            uri = uri.toLowerCase();

            // search in db for post by uri
            try {
                post = Application.getDatabaseService()
                        .getPost(uri, false);

                // was post found?
                if (post != null) {

                    // check against previously viewed posts
                    /*
                    boolean newViewFromSession = false;
                    if (servletRequest.getSession(false) != null) {
                        Session session = (Session) context.session();
                        HashSet<String> viewedPages = (HashSet<String>) session.getAttribute("viewedPages");
                    
                        if (viewedPages == null) {
                            viewedPages = new HashSet<String>();
                        }
                    
                        newViewFromSession = viewedPages.add(post.getUri());
                        session.setAttribute("viewedPages", viewedPages);
                    } */

                    // update page views
                    post.getView()
                            .setCount(post.getView()
                                    .getCount() + 1);
                    post.getView()
                            .setSession(post.getView()
                                    .getSession() + 1);

                    Application.getDatabaseService()
                            .editView(post.getView());
                } else {
                    logger.error("Post '" + uri + "' not found. Please try again.");
                    templateFile = "blog/post.ftl";
                }

            } catch (Exception e) {
                logger.error("Error: " + e.getClass()
                        .getName() + ". Please try again later.", e);
                templateFile = "/error/error.ftl";
            }
        } else {
            logger.error("Post '" + uri + "' not found. Please try again.");
            templateFile = "blog/post.ftl";
        }

        // Render template response
        ENGINE.render(context, MainVerticle.TEMPLATES_DIR, templateFile, res -> {
            context.response().putHeader("content-type", "text/html;charset=UTF-8");
            if (res.succeeded()) {
                context.response()
                        .end(res.result());
            } else {
                context.fail(res.cause());
            }
        });
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
