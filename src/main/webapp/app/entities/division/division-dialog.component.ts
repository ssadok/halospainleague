import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Division } from './division.model';
import { DivisionPopupService } from './division-popup.service';
import { DivisionService } from './division.service';

@Component({
    selector: 'jhi-division-dialog',
    templateUrl: './division-dialog.component.html'
})
export class DivisionDialogComponent implements OnInit {

    division: Division;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private divisionService: DivisionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.division.id !== undefined) {
            this.subscribeToSaveResponse(
                this.divisionService.update(this.division));
        } else {
            this.subscribeToSaveResponse(
                this.divisionService.create(this.division));
        }
    }

    private subscribeToSaveResponse(result: Observable<Division>) {
        result.subscribe((res: Division) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Division) {
        this.eventManager.broadcast({ name: 'divisionListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-division-popup',
    template: ''
})
export class DivisionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private divisionPopupService: DivisionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.divisionPopupService
                    .open(DivisionDialogComponent as Component, params['id']);
            } else {
                this.divisionPopupService
                    .open(DivisionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
