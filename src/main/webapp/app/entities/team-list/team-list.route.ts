import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TeamListComponent } from './team-list.component';
import { TeamListDetailComponent } from './team-list-detail.component';
import { TeamListPopupComponent } from './team-list-dialog.component';
import { TeamListDeletePopupComponent } from './team-list-delete-dialog.component';

export const teamListRoute: Routes = [
    {
        path: 'team-list',
        component: TeamListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.teamList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'team-list/:id',
        component: TeamListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.teamList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamListPopupRoute: Routes = [
    {
        path: 'team-list-new',
        component: TeamListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.teamList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team-list/:id/edit',
        component: TeamListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.teamList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team-list/:id/delete',
        component: TeamListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.teamList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
