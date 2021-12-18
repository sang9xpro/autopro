package com.sang9xpro.autopro.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.sang9xpro.autopro.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.sang9xpro.autopro.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.sang9xpro.autopro.domain.User.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Authority.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.User.class.getName() + ".authorities");
            createCache(cm, com.sang9xpro.autopro.domain.Loggers.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Loggers.class.getName() + ".loggersValues");
            createCache(cm, com.sang9xpro.autopro.domain.LoggersFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.LoggersFields.class.getName() + ".loggersValues");
            createCache(cm, com.sang9xpro.autopro.domain.LoggersValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.History.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.History.class.getName() + ".historyValues");
            createCache(cm, com.sang9xpro.autopro.domain.HistoryFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.HistoryFields.class.getName() + ".historyValues");
            createCache(cm, com.sang9xpro.autopro.domain.HistoryValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Accounts.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Accounts.class.getName() + ".accountValues");
            createCache(cm, com.sang9xpro.autopro.domain.AccountFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.AccountFields.class.getName() + ".accountValues");
            createCache(cm, com.sang9xpro.autopro.domain.AccountValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Tasks.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Tasks.class.getName() + ".taskValues");
            createCache(cm, com.sang9xpro.autopro.domain.TaskFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.TaskFields.class.getName() + ".taskValues");
            createCache(cm, com.sang9xpro.autopro.domain.TaskValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Applications.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Applications.class.getName() + ".applicationsValues");
            createCache(cm, com.sang9xpro.autopro.domain.Applications.class.getName() + ".accounts");
            createCache(cm, com.sang9xpro.autopro.domain.Applications.class.getName() + ".tasks");
            createCache(cm, com.sang9xpro.autopro.domain.Applications.class.getName() + ".comments");
            createCache(cm, com.sang9xpro.autopro.domain.ApplicationsFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.ApplicationsFields.class.getName() + ".applicationsValues");
            createCache(cm, com.sang9xpro.autopro.domain.ApplicationsValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Devices.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Devices.class.getName() + ".deviceValues");
            createCache(cm, com.sang9xpro.autopro.domain.DevicesFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.DevicesFields.class.getName() + ".deviceValues");
            createCache(cm, com.sang9xpro.autopro.domain.DeviceValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.SchedulerTask.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.SchedulerTask.class.getName() + ".schedulerTaskValues");
            createCache(cm, com.sang9xpro.autopro.domain.SchedulerTaskFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.SchedulerTaskFields.class.getName() + ".schedulerTaskValues");
            createCache(cm, com.sang9xpro.autopro.domain.SchedulerTaskValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Facebook.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Facebook.class.getName() + ".facebookValues");
            createCache(cm, com.sang9xpro.autopro.domain.FacebookFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.FacebookFields.class.getName() + ".facebookValues");
            createCache(cm, com.sang9xpro.autopro.domain.FacebookValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.MostOfContent.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.MostOfContent.class.getName() + ".mostOfContValues");
            createCache(cm, com.sang9xpro.autopro.domain.MostOfContFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.MostOfContFields.class.getName() + ".mostOfContValues");
            createCache(cm, com.sang9xpro.autopro.domain.MostOfContValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Comments.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Comments.class.getName() + ".commentsValues");
            createCache(cm, com.sang9xpro.autopro.domain.CommentsFields.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.CommentsFields.class.getName() + ".commentsValues");
            createCache(cm, com.sang9xpro.autopro.domain.CommentsValues.class.getName());
            createCache(cm, com.sang9xpro.autopro.domain.Country.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
