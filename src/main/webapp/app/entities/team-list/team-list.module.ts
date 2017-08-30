import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    TeamListService,
    TeamListPopupService,
    TeamListComponent,
    TeamListDetailComponent,
    TeamListDialogComponent,
    TeamListPopupComponent,
    TeamListDeletePopupComponent,
    TeamListDeleteDialogComponent,
    teamListRoute,
    teamListPopupRoute,
} from './';

const ENTITY_STATES = [
    ...teamListRoute,
    ...teamListPopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TeamListComponent,
        TeamListDetailComponent,
        TeamListDialogComponent,
        TeamListDeleteDialogComponent,
        TeamListPopupComponent,
        TeamListDeletePopupComponent,
    ],
    entryComponents: [
        TeamListComponent,
        TeamListDialogComponent,
        TeamListPopupComponent,
        TeamListDeleteDialogComponent,
        TeamListDeletePopupComponent,
    ],
    providers: [
        TeamListService,
        TeamListPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueTeamListModule {}
