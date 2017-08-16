import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ProfileConfiguration } from './profile-configuration.model';
import { ProfileConfigurationService } from './profile-configuration.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-profile-configuration',
    templateUrl: './profile-configuration.component.html'
})
export class ProfileConfigurationComponent implements OnInit, OnDestroy {
profileConfigurations: ProfileConfiguration[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private profileConfigurationService: ProfileConfigurationService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.profileConfigurationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.profileConfigurations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProfileConfigurations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProfileConfiguration) {
        return item.id;
    }
    registerChangeInProfileConfigurations() {
        this.eventSubscriber = this.eventManager.subscribe('profileConfigurationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
