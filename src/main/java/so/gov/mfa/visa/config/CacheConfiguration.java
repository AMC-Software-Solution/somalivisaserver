package so.gov.mfa.visa.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, so.gov.mfa.visa.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, so.gov.mfa.visa.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, so.gov.mfa.visa.domain.User.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.Authority.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.User.class.getName() + ".authorities");
            createCache(cm, so.gov.mfa.visa.domain.Applicant.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.ApplicantTravelDocument.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.ApplicantContactInfo.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.VisaApplication.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.VisaApplicationStay.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.ApplicationComment.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.PaymentTransaction.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.ApplicationFee.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.AppSetting.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.SystemSetting.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.Country.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.ElectronicVisa.class.getName());
            createCache(cm, so.gov.mfa.visa.domain.Employee.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
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
