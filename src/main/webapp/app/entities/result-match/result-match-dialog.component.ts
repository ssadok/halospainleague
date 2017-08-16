import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ResultMatch } from './result-match.model';
import { ResultMatchPopupService } from './result-match-popup.service';
import { ResultMatchService } from './result-match.service';
import { Match, MatchService } from '../match';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-result-match-dialog',
    templateUrl: './result-match-dialog.component.html'
})
export class ResultMatchDialogComponent implements OnInit {

    resultMatch: ResultMatch;
    isSaving: boolean;

    matches: Match[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private resultMatchService: ResultMatchService,
        private matchService: MatchService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.resultMatch.id !== undefined) {
            this.subscribeToSaveResponse(
                this.resultMatchService.update(this.resultMatch));
        } else {
            this.subscribeToSaveResponse(
                this.resultMatchService.create(this.resultMatch));
        }
    }

    private subscribeToSaveResponse(result: Observable<ResultMatch>) {
        result.subscribe((res: ResultMatch) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ResultMatch) {
        this.eventManager.broadcast({ name: 'resultMatchListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-result-match-popup',
    template: ''
})
export class ResultMatchPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resultMatchPopupService: ResultMatchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.resultMatchPopupService
                    .open(ResultMatchDialogComponent as Component, params['id']);
            } else {
                this.resultMatchPopupService
                    .open(ResultMatchDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
