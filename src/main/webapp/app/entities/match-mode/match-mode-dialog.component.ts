import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MatchMode } from './match-mode.model';
import { MatchModePopupService } from './match-mode-popup.service';
import { MatchModeService } from './match-mode.service';
import { Match, MatchService } from '../match';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-match-mode-dialog',
    templateUrl: './match-mode-dialog.component.html'
})
export class MatchModeDialogComponent implements OnInit {

    matchMode: MatchMode;
    isSaving: boolean;

    matches: Match[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private matchModeService: MatchModeService,
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
        if (this.matchMode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matchModeService.update(this.matchMode));
        } else {
            this.subscribeToSaveResponse(
                this.matchModeService.create(this.matchMode));
        }
    }

    private subscribeToSaveResponse(result: Observable<MatchMode>) {
        result.subscribe((res: MatchMode) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MatchMode) {
        this.eventManager.broadcast({ name: 'matchModeListModification', content: 'OK'});
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
    selector: 'jhi-match-mode-popup',
    template: ''
})
export class MatchModePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchModePopupService: MatchModePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matchModePopupService
                    .open(MatchModeDialogComponent as Component, params['id']);
            } else {
                this.matchModePopupService
                    .open(MatchModeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
