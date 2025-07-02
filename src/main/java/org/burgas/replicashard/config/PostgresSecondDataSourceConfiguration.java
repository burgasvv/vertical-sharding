package org.burgas.replicashard.config;

import jakarta.persistence.EntityManager;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.RollbackOn;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement(rollbackOn = RollbackOn.ALL_EXCEPTIONS)
@EnableJpaRepositories(
        basePackages = "org.burgas.replicashard.repository.second",
        entityManagerFactoryRef = "postgresSecondEntityManager",
        transactionManagerRef = "postgresSecondTransactionManager"
)
public class PostgresSecondDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSourceProperties postgresSecondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgresSecondDataSource() {
        return this.postgresSecondDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresSecondEntityManager() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setEntityManagerInterface(EntityManager.class);
        entityManagerFactoryBean.setDataSource(this.postgresSecondDataSource());
        entityManagerFactoryBean.setPackagesToScan("org.burgas.replicashard.entity.second");
        entityManagerFactoryBean.setJpaPropertyMap(
                Map.of(
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                        "hibernate.hbm2ddl.auto", "none",
                        "hibernate.show_sql", "true",
                        "hibernate.format_sql", "true",
                        "hibernate.highlight_sql", "true"
                )
        );

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager postgresSecondTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(this.postgresSecondEntityManager().getObject());
        jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());
        jpaTransactionManager.setRollbackOnCommitFailure(true);
        return jpaTransactionManager;
    }

    @Bean
    public SpringLiquibase postgresSecondLiquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(this.postgresSecondDataSource());
        springLiquibase.setChangeLog("db/changelog/second/db.changelog-second-master.yaml");
        return springLiquibase;
    }
}
