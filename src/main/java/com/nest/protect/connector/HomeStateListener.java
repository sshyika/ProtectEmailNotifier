package com.nest.protect.connector;

import com.nest.protect.model.Model;

public interface HomeStateListener {

    void onUpdate(Model newState);

}
