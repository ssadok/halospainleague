import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { MatchMode } from './match-mode.model';
import { MatchModeService } from './match-mode.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-match-mode',
    templateUrl: './match-mode.component.html'
})
export class MatchModeComponent implements OnInit, OnDestroy {
matchModes: MatchMode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private matchModeService: MatchModeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.matchModeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.matchModes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMatchModes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MatchMode) {
        return item.id;
    }
    registerChangeInMatchModes() {
        this.eventSubscriber = this.eventManager.subscribe('matchModeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
