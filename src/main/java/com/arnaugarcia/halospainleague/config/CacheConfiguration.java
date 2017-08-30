package com.arnaugarcia.halospainleague.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.arnaugarcia.halospainleague.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Game.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Game.class.getName() + ".maps", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Game.class.getName() + ".tournaments", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Tournament.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Tournament.class.getName() + ".matches", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Tournament.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Division.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Team.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Team.class.getName() + ".players", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Team.class.getName() + ".teamLists", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Team.class.getName() + ".torunaments", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Match.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Map.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Map.class.getName() + ".matches", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.ResultMatch.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Player.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Player.class.getName() + ".socialAccounts", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Player.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Player.class.getName() + ".achievements", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Player.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Player.class.getName() + ".messageRooms", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Country.class.getName() + ".players", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.SocialAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Achievement.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Achievement.class.getName() + ".players", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.ProfileConfiguration.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.ProfileConfiguration.class.getName() + ".themes", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.MessageRoom.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.MessageRoom.class.getName() + ".themes", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.MessageRoom.class.getName() + ".players", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Message.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Theme.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.MatchMode.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.TeamList.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.TeamList.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Division.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.halospainleague.domain.Match.class.getName() + ".matchModes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
