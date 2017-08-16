import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { SocialAccount } from './social-account.model';
import { SocialAccountService } from './social-account.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-social-account',
    templateUrl: './social-account.component.html'
})
export class SocialAccountComponent implements OnInit, OnDestroy {
socialAccounts: SocialAccount[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private socialAccountService: SocialAccountService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.socialAccountService.query().subscribe(
            (res: ResponseWrapper) => {
                this.socialAccounts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSocialAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SocialAccount) {
        return item.id;
    }
    registerChangeInSocialAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('socialAccountListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
