package net.anzix.kogutowicz.samples;

import java.io.File;

class Sample {

    public Sample() {
    }

    public Sample(File configFile, String description) {
        this.configFile = configFile;
        this.description = description;
    }

    public Sample(String id, File configFile, String description) {
        this.id = id;
        this.configFile = configFile;
        this.description = description;
    }
    protected String id;

    protected File configFile;

    protected String description;

}
