import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Theme } from './theme.model';
import { ThemePopupService } from './theme-popup.service';
import { ThemeService } from './theme.service';

@Component({
    selector: 'jhi-theme-delete-dialog',
    templateUrl: './theme-delete-dialog.component.html'
})
export class ThemeDeleteDialogComponent {

    theme: Theme;

    constructor(
        private themeService: ThemeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.themeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'themeListModification',
                content: 'Deleted an theme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-theme-delete-popup',
    template: ''
})
export class ThemeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private themePopupService: ThemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.themePopupService
                .open(ThemeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
