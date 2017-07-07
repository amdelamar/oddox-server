package com.rant.database;

import java.util.ArrayList;

import com.rant.model.Author;
import com.rant.model.Database;
import com.rant.model.Post;
import com.rant.model.Role;

public class CloudantDatabase extends DatabaseSource {

    public CloudantDatabase(Database database) {
        super(database);
    }

    @Override
    public Post getPost(String uri, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public Post newPost(Post post) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public boolean editPost(Post post) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean deletePost(Post post) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public Author getAuthor(String uri, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public boolean editAuthor(Author author) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteAuthor(Author author) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<Post> getArchiveFeatured() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<String> getArchiveYears() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<String> getArchiveCategories() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<String> getArchiveTags() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<String> getPostUris() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Post> getPosts(int page, int limit, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Author> getAuthors(int page, int limit, boolean includeAdmins) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public Author getUser(String username) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public Author newUser(Author user) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public boolean editUser(Author user) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean loginUser(Author user) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean incrementPageViews(Post post, boolean sessionView) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<Role> getRoles() {
        // TODO Auto-generated method stub
        return null;
    }
}