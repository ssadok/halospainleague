import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TeamList } from './team-list.model';
import { TeamListService } from './team-list.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-team-list',
    templateUrl: './team-list.component.html'
})
export class TeamListComponent implements OnInit, OnDestroy {
teamLists: TeamList[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private teamListService: TeamListService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.teamListService.query().subscribe(
            (res: ResponseWrapper) => {
                this.teamLists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTeamLists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TeamList) {
        return item.id;
    }
    registerChangeInTeamLists() {
        this.eventSubscriber = this.eventManager.subscribe('teamListListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
