package com.nest.protect.connector;

import com.nest.protect.model.Model;

public interface ModelListener {

    void onUpdate(Model newState);

}
