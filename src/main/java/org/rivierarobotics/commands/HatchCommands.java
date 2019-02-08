package org.rivierarobotics.commands;

import javax.inject.Inject;
import javax.inject.Provider;

public class HatchCommands {
    private final Provider<HatchPush> hatchPushProvider;
    private HatchSystemDeployCreator hatchSystemDeployCreator;

    @Inject
    public HatchCommands(Provider<HatchPush> hatchPushProvider, HatchSystemDeployCreator hatchSystemDeployCreator) {
        this.hatchPushProvider = hatchPushProvider;
        this.hatchSystemDeployCreator = hatchSystemDeployCreator;
    }

    public final HatchPush push() {
        return hatchPushProvider.get();
    }

    public final HatchSystemDeploy deploy() {
        return hatchSystemDeployCreator.create();
    }
}
