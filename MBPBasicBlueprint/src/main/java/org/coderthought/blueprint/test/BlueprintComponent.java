package org.coderthought.blueprint.test;

public class BlueprintComponent {
    InjectedComponent injected;

    public void activate() {
        System.out.println("*** Activating a Blueprint Component, injected: " + injected);
    }

    public void setInjected(InjectedComponent ic) {
        injected = ic;
    }
}
