import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TeamList } from './team-list.model';
import { TeamListPopupService } from './team-list-popup.service';
import { TeamListService } from './team-list.service';

@Component({
    selector: 'jhi-team-list-delete-dialog',
    templateUrl: './team-list-delete-dialog.component.html'
})
export class TeamListDeleteDialogComponent {

    teamList: TeamList;

    constructor(
        private teamListService: TeamListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teamListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teamListListModification',
                content: 'Deleted an teamList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-team-list-delete-popup',
    template: ''
})
export class TeamListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamListPopupService: TeamListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.teamListPopupService
                .open(TeamListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
