import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ResultMatch } from './result-match.model';
import { ResultMatchService } from './result-match.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-result-match',
    templateUrl: './result-match.component.html'
})
export class ResultMatchComponent implements OnInit, OnDestroy {
resultMatches: ResultMatch[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private resultMatchService: ResultMatchService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.resultMatchService.query().subscribe(
            (res: ResponseWrapper) => {
                this.resultMatches = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInResultMatches();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ResultMatch) {
        return item.id;
    }
    registerChangeInResultMatches() {
        this.eventSubscriber = this.eventManager.subscribe('resultMatchListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
