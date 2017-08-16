import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ThemeComponent } from './theme.component';
import { ThemeDetailComponent } from './theme-detail.component';
import { ThemePopupComponent } from './theme-dialog.component';
import { ThemeDeletePopupComponent } from './theme-delete-dialog.component';

export const themeRoute: Routes = [
    {
        path: 'theme',
        component: ThemeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'theme/:id',
        component: ThemeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const themePopupRoute: Routes = [
    {
        path: 'theme-new',
        component: ThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme/:id/edit',
        component: ThemePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'theme/:id/delete',
        component: ThemeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.theme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
