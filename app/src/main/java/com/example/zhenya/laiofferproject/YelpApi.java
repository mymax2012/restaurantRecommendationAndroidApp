package com.example.zhenya.laiofferproject;

import android.util.Log;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class YelpApi {
    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";


    private static final String CONSUMER_KEY = "rA9sNxa-Zxd92CVdANwFCQ";
    private static final String CONSUMER_SECRET = "4pAT57T1CdrK7aHHhAZs9PO1A4E";
    private static final String TOKEN = "PSWGsHxM1FMi_RIew9L9fQQRq7Eao5IG";
    private static final String TOKEN_SECRET = "vISjxdSc6DUYVMUPvJzNrM7Z9NA";


    private OAuthService service;
    private Token accessToken;


    /**
     * Setup the Yelp API OAuth credentials.
     */
    public YelpApi() {
        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);
    }


    /**
     * File a search request to Yelp API.
     */
    public String searchForBusinessesByLocation(String term, String location, int searchLimit) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("limit", String.valueOf(searchLimit));
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        Log.i("message", response.getBody());
        return response.getBody();
    }
}


