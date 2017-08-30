import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MatchMode } from './match-mode.model';
import { MatchModeService } from './match-mode.service';

@Component({
    selector: 'jhi-match-mode-detail',
    templateUrl: './match-mode-detail.component.html'
})
export class MatchModeDetailComponent implements OnInit, OnDestroy {

    matchMode: MatchMode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private matchModeService: MatchModeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMatchModes();
    }

    load(id) {
        this.matchModeService.find(id).subscribe((matchMode) => {
            this.matchMode = matchMode;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMatchModes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'matchModeListModification',
            (response) => this.load(this.matchMode.id)
        );
    }
}
