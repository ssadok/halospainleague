import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MatchModeComponent } from './match-mode.component';
import { MatchModeDetailComponent } from './match-mode-detail.component';
import { MatchModePopupComponent } from './match-mode-dialog.component';
import { MatchModeDeletePopupComponent } from './match-mode-delete-dialog.component';

export const matchModeRoute: Routes = [
    {
        path: 'match-mode',
        component: MatchModeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.matchMode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'match-mode/:id',
        component: MatchModeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.matchMode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchModePopupRoute: Routes = [
    {
        path: 'match-mode-new',
        component: MatchModePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.matchMode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-mode/:id/edit',
        component: MatchModePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.matchMode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-mode/:id/delete',
        component: MatchModeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.matchMode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
