import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MessageRoomComponent } from './message-room.component';
import { MessageRoomDetailComponent } from './message-room-detail.component';
import { MessageRoomPopupComponent } from './message-room-dialog.component';
import { MessageRoomDeletePopupComponent } from './message-room-delete-dialog.component';

export const messageRoomRoute: Routes = [
    {
        path: 'message-room',
        component: MessageRoomComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.messageRoom.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'message-room/:id',
        component: MessageRoomDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.messageRoom.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messageRoomPopupRoute: Routes = [
    {
        path: 'message-room-new',
        component: MessageRoomPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.messageRoom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-room/:id/edit',
        component: MessageRoomPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.messageRoom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-room/:id/delete',
        component: MessageRoomDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.messageRoom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
