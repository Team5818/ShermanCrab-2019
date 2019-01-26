package org.rivierarobotics.commands;

import javax.inject.Inject;
import javax.inject.Provider;

public class HatchCommands {
    private final Provider<HatchPushExtend> hatchPushProvider;

    @Inject
    public HatchCommands(Provider<HatchPushExtend> hatchPushProvider) {
        this.hatchPushProvider = hatchPushProvider;
    }

    public final HatchPushExtend push() {
        return hatchPushProvider.get();
    }
}
