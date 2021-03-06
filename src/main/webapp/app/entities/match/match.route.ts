import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MatchComponent } from './match.component';
import { MatchDetailComponent } from './match-detail.component';
import { MatchPopupComponent } from './match-dialog.component';
import { MatchDeletePopupComponent } from './match-delete-dialog.component';

export const matchRoute: Routes = [
    {
        path: 'match',
        component: MatchComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'match/:id',
        component: MatchDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchPopupRoute: Routes = [
    {
        path: 'match-new',
        component: MatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match/:id/edit',
        component: MatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match/:id/delete',
        component: MatchDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
