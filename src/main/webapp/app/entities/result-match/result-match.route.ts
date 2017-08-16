import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ResultMatchComponent } from './result-match.component';
import { ResultMatchDetailComponent } from './result-match-detail.component';
import { ResultMatchPopupComponent } from './result-match-dialog.component';
import { ResultMatchDeletePopupComponent } from './result-match-delete-dialog.component';

export const resultMatchRoute: Routes = [
    {
        path: 'result-match',
        component: ResultMatchComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.resultMatch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'result-match/:id',
        component: ResultMatchDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.resultMatch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resultMatchPopupRoute: Routes = [
    {
        path: 'result-match-new',
        component: ResultMatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.resultMatch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'result-match/:id/edit',
        component: ResultMatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.resultMatch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'result-match/:id/delete',
        component: ResultMatchDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.resultMatch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
