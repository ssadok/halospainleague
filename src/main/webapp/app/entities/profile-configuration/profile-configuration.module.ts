import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    ProfileConfigurationService,
    ProfileConfigurationPopupService,
    ProfileConfigurationComponent,
    ProfileConfigurationDetailComponent,
    ProfileConfigurationDialogComponent,
    ProfileConfigurationPopupComponent,
    ProfileConfigurationDeletePopupComponent,
    ProfileConfigurationDeleteDialogComponent,
    profileConfigurationRoute,
    profileConfigurationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...profileConfigurationRoute,
    ...profileConfigurationPopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProfileConfigurationComponent,
        ProfileConfigurationDetailComponent,
        ProfileConfigurationDialogComponent,
        ProfileConfigurationDeleteDialogComponent,
        ProfileConfigurationPopupComponent,
        ProfileConfigurationDeletePopupComponent,
    ],
    entryComponents: [
        ProfileConfigurationComponent,
        ProfileConfigurationDialogComponent,
        ProfileConfigurationPopupComponent,
        ProfileConfigurationDeleteDialogComponent,
        ProfileConfigurationDeletePopupComponent,
    ],
    providers: [
        ProfileConfigurationService,
        ProfileConfigurationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueProfileConfigurationModule {}
