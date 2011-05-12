package org.coderthoughts.demo.cdiosgi.web;

import javax.inject.Inject;

public class Hello {
    @Inject
    World world;

    public String sayHelloWorld() {
        return "Hello " + world.sayWorld();
    }
}
