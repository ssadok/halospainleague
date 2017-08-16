import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SocialAccount } from './social-account.model';
import { SocialAccountPopupService } from './social-account-popup.service';
import { SocialAccountService } from './social-account.service';

@Component({
    selector: 'jhi-social-account-delete-dialog',
    templateUrl: './social-account-delete-dialog.component.html'
})
export class SocialAccountDeleteDialogComponent {

    socialAccount: SocialAccount;

    constructor(
        private socialAccountService: SocialAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.socialAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'socialAccountListModification',
                content: 'Deleted an socialAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-social-account-delete-popup',
    template: ''
})
export class SocialAccountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private socialAccountPopupService: SocialAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.socialAccountPopupService
                .open(SocialAccountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
