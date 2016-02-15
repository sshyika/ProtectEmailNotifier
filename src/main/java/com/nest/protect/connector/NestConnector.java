package com.nest.protect.connector;

import com.firebase.client.*;
import com.nest.protect.Config;
import com.nest.protect.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class NestConnector {
    private static final Logger LOG = LoggerFactory.getLogger(NestConnector.class);

    private Firebase firebase;

    private NestConnector(Firebase firebase) {
        this.firebase = firebase;
    }


    public static NestConnector build(Config config) {
        Firebase firebase = new Firebase(config.getNestUrl());
        firebase.authWithCustomToken(config.getNestToken(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                LOG.info("Authenticated successfully");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                LOG.error("AuthenticationError {}", firebaseError.getMessage());
            }
        });
        return new NestConnector(firebase);
    }


    @SuppressWarnings("unchecked")
    public void addHomeStateListener(HomeStateListener listener) {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LOG.debug("Received new model, start processing...");
                listener.onUpdate(Model.parse((Map<String, Object>)dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                LOG.warn("onCancelled operation is not supported");
            }
        });
    }

}
