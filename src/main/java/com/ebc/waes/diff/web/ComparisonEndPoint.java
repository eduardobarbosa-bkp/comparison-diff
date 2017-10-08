package com.ebc.waes.diff.web;

import com.ebc.waes.diff.business.ComparisonBusiness;
import com.ebc.waes.diff.domain.dto.ComparisonDiffDTO;
import com.ebc.waes.diff.domain.dto.SourceDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This class is responsible to provide the endpoints for comparison
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
@Path("/diff")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComparisonEndPoint {

    @Inject
    private ComparisonBusiness business;

    /**
     * Add the left side of a comparison
     * @param id the identifier of a comparison
     * @param source the {@link SourceDTO} bind by json with the content data Base64 encoded
     */
    @POST
    @Path("/{id}/left")
    public void addDiffLeftSide(@PathParam("id") String id, SourceDTO source){
       business.setDiffLeftSide(id, source);
    }

    /**
     * Add the right side of a comparison
     * @param id the identifier of a comparison
     * @param source the {@link SourceDTO} json with the content data base64 encoded
     */
    @POST
    @Path("/{id}/right")
    public void addDiffRightSide(@PathParam("id") String id, SourceDTO source){
        business.setDiffRightSide(id, source);
    }

    /**
     * Provide the result of a comparison of the left and right side content
     * @param id the identifier of a comparison
     * @return the {@link ComparisonDiffDTO} with the diffs results
     */
    @GET
    @Path("/{id}")
    public ComparisonDiffDTO provideDiffResults(@PathParam("id") String id){
        return business.getDiffResults(id);
    }

}
