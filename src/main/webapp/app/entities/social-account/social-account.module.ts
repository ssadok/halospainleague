import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    SocialAccountService,
    SocialAccountPopupService,
    SocialAccountComponent,
    SocialAccountDetailComponent,
    SocialAccountDialogComponent,
    SocialAccountPopupComponent,
    SocialAccountDeletePopupComponent,
    SocialAccountDeleteDialogComponent,
    socialAccountRoute,
    socialAccountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...socialAccountRoute,
    ...socialAccountPopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SocialAccountComponent,
        SocialAccountDetailComponent,
        SocialAccountDialogComponent,
        SocialAccountDeleteDialogComponent,
        SocialAccountPopupComponent,
        SocialAccountDeletePopupComponent,
    ],
    entryComponents: [
        SocialAccountComponent,
        SocialAccountDialogComponent,
        SocialAccountPopupComponent,
        SocialAccountDeleteDialogComponent,
        SocialAccountDeletePopupComponent,
    ],
    providers: [
        SocialAccountService,
        SocialAccountPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueSocialAccountModule {}
