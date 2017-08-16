import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    ResultMatchService,
    ResultMatchPopupService,
    ResultMatchComponent,
    ResultMatchDetailComponent,
    ResultMatchDialogComponent,
    ResultMatchPopupComponent,
    ResultMatchDeletePopupComponent,
    ResultMatchDeleteDialogComponent,
    resultMatchRoute,
    resultMatchPopupRoute,
} from './';

const ENTITY_STATES = [
    ...resultMatchRoute,
    ...resultMatchPopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ResultMatchComponent,
        ResultMatchDetailComponent,
        ResultMatchDialogComponent,
        ResultMatchDeleteDialogComponent,
        ResultMatchPopupComponent,
        ResultMatchDeletePopupComponent,
    ],
    entryComponents: [
        ResultMatchComponent,
        ResultMatchDialogComponent,
        ResultMatchPopupComponent,
        ResultMatchDeleteDialogComponent,
        ResultMatchDeletePopupComponent,
    ],
    providers: [
        ResultMatchService,
        ResultMatchPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueResultMatchModule {}
