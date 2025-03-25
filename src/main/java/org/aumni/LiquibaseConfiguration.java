package org.aumni;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquibaseConfiguration {

    @JsonProperty
    private String changeLogFile;

    @JsonProperty
    private String url;

    @JsonProperty
    private String driver;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private String loggingLevel;

    // Getters and setters
    public String getChangeLogFile() {
        return changeLogFile;
    }

    public void setChangeLogFile(String changeLogFile) {
        this.changeLogFile = changeLogFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoggingLevel() {
        return loggingLevel;
    }

    public void setLoggingLevel(String loggingLevel) {
        this.loggingLevel = loggingLevel;
    }
}
