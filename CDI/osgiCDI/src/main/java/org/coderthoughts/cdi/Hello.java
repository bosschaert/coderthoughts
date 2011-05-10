package org.coderthoughts.cdi;

import javax.inject.Inject;

public class Hello {
    @Inject
    World world;

    public String sayHelloWorld() {
        return "Hello " + world.sayWorld();
    }
}
