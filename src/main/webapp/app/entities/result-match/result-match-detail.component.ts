import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ResultMatch } from './result-match.model';
import { ResultMatchService } from './result-match.service';

@Component({
    selector: 'jhi-result-match-detail',
    templateUrl: './result-match-detail.component.html'
})
export class ResultMatchDetailComponent implements OnInit, OnDestroy {

    resultMatch: ResultMatch;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private resultMatchService: ResultMatchService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInResultMatches();
    }

    load(id) {
        this.resultMatchService.find(id).subscribe((resultMatch) => {
            this.resultMatch = resultMatch;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInResultMatches() {
        this.eventSubscriber = this.eventManager.subscribe(
            'resultMatchListModification',
            (response) => this.load(this.resultMatch.id)
        );
    }
}
