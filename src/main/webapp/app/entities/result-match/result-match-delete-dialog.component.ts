import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ResultMatch } from './result-match.model';
import { ResultMatchPopupService } from './result-match-popup.service';
import { ResultMatchService } from './result-match.service';

@Component({
    selector: 'jhi-result-match-delete-dialog',
    templateUrl: './result-match-delete-dialog.component.html'
})
export class ResultMatchDeleteDialogComponent {

    resultMatch: ResultMatch;

    constructor(
        private resultMatchService: ResultMatchService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resultMatchService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'resultMatchListModification',
                content: 'Deleted an resultMatch'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-result-match-delete-popup',
    template: ''
})
export class ResultMatchDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resultMatchPopupService: ResultMatchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.resultMatchPopupService
                .open(ResultMatchDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
