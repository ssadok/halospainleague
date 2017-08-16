import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SocialAccountComponent } from './social-account.component';
import { SocialAccountDetailComponent } from './social-account-detail.component';
import { SocialAccountPopupComponent } from './social-account-dialog.component';
import { SocialAccountDeletePopupComponent } from './social-account-delete-dialog.component';

export const socialAccountRoute: Routes = [
    {
        path: 'social-account',
        component: SocialAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'social-account/:id',
        component: SocialAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const socialAccountPopupRoute: Routes = [
    {
        path: 'social-account-new',
        component: SocialAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'social-account/:id/edit',
        component: SocialAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'social-account/:id/delete',
        component: SocialAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'halospainleagueApp.socialAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
