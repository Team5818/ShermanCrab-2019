package org.rivierarobotics.commands;

import javax.inject.Inject;
import javax.inject.Provider;

public class HatchCommands {
    private final Provider<HatchPush> hatchPushProvider;

    @Inject
    public HatchCommands(Provider<HatchPush> hatchPushProvider) {
        this.hatchPushProvider = hatchPushProvider;
    }

    public final HatchPush push() {
        return hatchPushProvider.get();
    }
}
