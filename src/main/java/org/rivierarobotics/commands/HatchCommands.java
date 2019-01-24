package org.rivierarobotics.commands;

import javax.inject.Inject;
import javax.inject.Provider;

public class HatchCommands {
    private final Provider<HatchPullRetract> hatchPullProvider;

    @Inject
    public HatchCommands(Provider<HatchPullRetract> hatchPullProvider) {
        this.hatchPullProvider = hatchPullProvider;
    }

    public final HatchPullRetract pull() {
        return hatchPullProvider.get();
    }
}
