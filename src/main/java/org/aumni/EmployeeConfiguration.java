package org.aumni;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EmployeeConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    private LiquibaseConfiguration liquibase;

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @JsonProperty("liquibase")
    public LiquibaseConfiguration getLiquibaseConfiguration() {
        return liquibase;
    }

    @JsonProperty("liquibase")
    public void setLiquibaseConfiguration(LiquibaseConfiguration liquibaseConfiguration) {
        this.liquibase = liquibaseConfiguration;
    }
}
