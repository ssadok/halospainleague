import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HalospainleagueSharedModule } from '../../shared';
import {
    MessageRoomService,
    MessageRoomPopupService,
    MessageRoomComponent,
    MessageRoomDetailComponent,
    MessageRoomDialogComponent,
    MessageRoomPopupComponent,
    MessageRoomDeletePopupComponent,
    MessageRoomDeleteDialogComponent,
    messageRoomRoute,
    messageRoomPopupRoute,
} from './';

const ENTITY_STATES = [
    ...messageRoomRoute,
    ...messageRoomPopupRoute,
];

@NgModule({
    imports: [
        HalospainleagueSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MessageRoomComponent,
        MessageRoomDetailComponent,
        MessageRoomDialogComponent,
        MessageRoomDeleteDialogComponent,
        MessageRoomPopupComponent,
        MessageRoomDeletePopupComponent,
    ],
    entryComponents: [
        MessageRoomComponent,
        MessageRoomDialogComponent,
        MessageRoomPopupComponent,
        MessageRoomDeleteDialogComponent,
        MessageRoomDeletePopupComponent,
    ],
    providers: [
        MessageRoomService,
        MessageRoomPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HalospainleagueMessageRoomModule {}
