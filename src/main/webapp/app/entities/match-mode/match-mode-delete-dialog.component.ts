import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MatchMode } from './match-mode.model';
import { MatchModePopupService } from './match-mode-popup.service';
import { MatchModeService } from './match-mode.service';

@Component({
    selector: 'jhi-match-mode-delete-dialog',
    templateUrl: './match-mode-delete-dialog.component.html'
})
export class MatchModeDeleteDialogComponent {

    matchMode: MatchMode;

    constructor(
        private matchModeService: MatchModeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchModeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'matchModeListModification',
                content: 'Deleted an matchMode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-match-mode-delete-popup',
    template: ''
})
export class MatchModeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchModePopupService: MatchModePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.matchModePopupService
                .open(MatchModeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
