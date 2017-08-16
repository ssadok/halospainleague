import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TournamentComponent } from './tournament.component';
import { TournamentDetailComponent } from './tournament-detail.component';
import { TournamentPopupComponent } from './tournament-dialog.component';
import { TournamentDeletePopupComponent } from './tournament-delete-dialog.component';

export const tournamentRoute: Routes = [
    {
        path: 'tournament',
        component: TournamentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tournament/:id',
        component: TournamentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tournamentPopupRoute: Routes = [
    {
        path: 'tournament-new',
        component: TournamentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tournament/:id/edit',
        component: TournamentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tournament/:id/delete',
        component: TournamentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
