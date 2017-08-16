import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { NotificationComponent } from './notification.component';
import { NotificationDetailComponent } from './notification-detail.component';
import { NotificationPopupComponent } from './notification-dialog.component';
import { NotificationDeletePopupComponent } from './notification-delete-dialog.component';

export const notificationRoute: Routes = [
    {
        path: 'notification',
        component: NotificationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'notification/:id',
        component: NotificationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notificationPopupRoute: Routes = [
    {
        path: 'notification-new',
        component: NotificationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification/:id/edit',
        component: NotificationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification/:id/delete',
        component: NotificationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];