import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProfileConfigurationComponent } from './profile-configuration.component';
import { ProfileConfigurationDetailComponent } from './profile-configuration-detail.component';
import { ProfileConfigurationPopupComponent } from './profile-configuration-dialog.component';
import { ProfileConfigurationDeletePopupComponent } from './profile-configuration-delete-dialog.component';

export const profileConfigurationRoute: Routes = [
    {
        path: 'profile-configuration',
        component: ProfileConfigurationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.profileConfiguration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profile-configuration/:id',
        component: ProfileConfigurationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.profileConfiguration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profileConfigurationPopupRoute: Routes = [
    {
        path: 'profile-configuration-new',
        component: ProfileConfigurationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.profileConfiguration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-configuration/:id/edit',
        component: ProfileConfigurationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.profileConfiguration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-configuration/:id/delete',
        component: ProfileConfigurationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.profileConfiguration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
