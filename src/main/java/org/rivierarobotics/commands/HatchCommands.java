package org.rivierarobotics.commands;

import javax.inject.Inject;
import javax.inject.Provider;

public class HatchCommands {
    private final Provider<HatchPull> hatchPullProvider;

    @Inject
    public HatchCommands(Provider<HatchPull> hatchPullProvider) {
        this.hatchPullProvider = hatchPullProvider;
    }

    public final HatchPull pull() {
        return hatchPullProvider.get();
    }
}
