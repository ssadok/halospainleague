import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    MapService,
    MapPopupService,
    MapComponent,
    MapDetailComponent,
    MapDialogComponent,
    MapPopupComponent,
    MapDeletePopupComponent,
    MapDeleteDialogComponent,
    mapRoute,
    mapPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mapRoute,
    ...mapPopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MapComponent,
        MapDetailComponent,
        MapDialogComponent,
        MapDeleteDialogComponent,
        MapPopupComponent,
        MapDeletePopupComponent,
    ],
    entryComponents: [
        MapComponent,
        MapDialogComponent,
        MapPopupComponent,
        MapDeleteDialogComponent,
        MapDeletePopupComponent,
    ],
    providers: [
        MapService,
        MapPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueMapModule {}
