import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    MatchModeService,
    MatchModePopupService,
    MatchModeComponent,
    MatchModeDetailComponent,
    MatchModeDialogComponent,
    MatchModePopupComponent,
    MatchModeDeletePopupComponent,
    MatchModeDeleteDialogComponent,
    matchModeRoute,
    matchModePopupRoute,
} from './';

const ENTITY_STATES = [
    ...matchModeRoute,
    ...matchModePopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MatchModeComponent,
        MatchModeDetailComponent,
        MatchModeDialogComponent,
        MatchModeDeleteDialogComponent,
        MatchModePopupComponent,
        MatchModeDeletePopupComponent,
    ],
    entryComponents: [
        MatchModeComponent,
        MatchModeDialogComponent,
        MatchModePopupComponent,
        MatchModeDeleteDialogComponent,
        MatchModeDeletePopupComponent,
    ],
    providers: [
        MatchModeService,
        MatchModePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueMatchModeModule {}
