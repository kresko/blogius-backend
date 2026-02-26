package com.blogius.resource;

import com.blogius.entity.Post;
import com.blogius.service.PostService;
import jakarta.validation.Valid;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
    @Inject
    PostService postService;

    @GET
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GET
    @Path("/{id}")
    public Response getPost(@PathParam("id") Long id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(post).build();
    }

    @POST
    public Response createPost(@Valid Post post) {
        Post created = postService.createPost(post);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePost(@PathParam("id") Long id, @Valid Post post) {
        Post updated = postService.updatePost(id, post);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePost(@PathParam("id") Long id) {
        boolean deleted = postService.deletePost(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
