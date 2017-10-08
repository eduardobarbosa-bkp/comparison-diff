package com.ebc.waes.diff.web;

import com.ebc.waes.diff.business.ComparisonBusiness;
import com.ebc.waes.diff.model.DifferEntity;
import com.ebc.waes.diff.model.SourceEntity;

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
     * @param sourceEntity the {@link SourceEntity} bind by json with the content data Base64 encoded
     */
    @POST
    @Path("/{id}/left")
    public void addDiffLeftSide(@PathParam("id") String id, SourceEntity sourceEntity){
       business.setDiffLeftSide(id, sourceEntity);
    }

    /**
     * Add the right side of a comparison
     * @param id the identifier of a comparison
     * @param sourceEntity the {@link SourceEntity} json with the content data base64 encoded
     */
    @POST
    @Path("/{id}/right")
    public void addDiffRightSide(@PathParam("id") String id, SourceEntity sourceEntity){
        business.setDiffRightSide(id, sourceEntity);
    }

    /**
     * Provide the result of a comparison of the left and right side content
     * @param id the identifier of a comparison
     * @return the {@link DifferEntity} with the diffs results
     */
    @GET
    @Path("/{id}")
    public DifferEntity provideDiffResults(@PathParam("id") String id){
        return business.getDiffResults(id);
    }

}
