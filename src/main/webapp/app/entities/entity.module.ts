import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HalospainleagueGameModule } from './game/game.module';
import { HalospainleagueTournamentModule } from './tournament/tournament.module';
import { HalospainleagueDivisionModule } from './division/division.module';
import { HalospainleagueTeamModule } from './team/team.module';
import { HalospainleagueMatchModule } from './match/match.module';
import { HalospainleagueMapModule } from './map/map.module';
import { HalospainleagueResultMatchModule } from './result-match/result-match.module';
import { HalospainleaguePlayerModule } from './player/player.module';
import { HalospainleagueCountryModule } from './country/country.module';
import { HalospainleagueSocialAccountModule } from './social-account/social-account.module';
import { HalospainleagueAchievementModule } from './achievement/achievement.module';
import { HalospainleagueProfileConfigurationModule } from './profile-configuration/profile-configuration.module';
import { HalospainleagueNotificationModule } from './notification/notification.module';
import { HalospainleagueMessageRoomModule } from './message-room/message-room.module';
import { HalospainleagueMessageModule } from './message/message.module';
import { HalospainleagueThemeModule } from './theme/theme.module';
import { HalospainleagueMatchModeModule } from './match-mode/match-mode.module';
import { HalospainleagueTeamListModule } from './team-list/team-list.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HalospainleagueGameModule,
        HalospainleagueTournamentModule,
        HalospainleagueDivisionModule,
        HalospainleagueTeamModule,
        HalospainleagueMatchModule,
        HalospainleagueMapModule,
        HalospainleagueResultMatchModule,
        HalospainleaguePlayerModule,
        HalospainleagueCountryModule,
        HalospainleagueSocialAccountModule,
        HalospainleagueAchievementModule,
        HalospainleagueProfileConfigurationModule,
        HalospainleagueNotificationModule,
        HalospainleagueMessageRoomModule,
        HalospainleagueMessageModule,
        HalospainleagueThemeModule,
        HalospainleagueMatchModeModule,
        HalospainleagueTeamListModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueEntityModule {}
