import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TeamComponent } from './team.component';
import { TeamDetailComponent } from './team-detail.component';
import { TeamPopupComponent } from './team-dialog.component';
import { TeamDeletePopupComponent } from './team-delete-dialog.component';

export const teamRoute: Routes = [
    {
        path: 'team',
        component: TeamComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.team.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'team/:id',
        component: TeamDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.team.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamPopupRoute: Routes = [
    {
        path: 'team-new',
        component: TeamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.team.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team/:id/edit',
        component: TeamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.team.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team/:id/delete',
        component: TeamDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.team.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
