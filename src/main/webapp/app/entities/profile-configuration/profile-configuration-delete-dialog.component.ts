import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileConfiguration } from './profile-configuration.model';
import { ProfileConfigurationPopupService } from './profile-configuration-popup.service';
import { ProfileConfigurationService } from './profile-configuration.service';

@Component({
    selector: 'jhi-profile-configuration-delete-dialog',
    templateUrl: './profile-configuration-delete-dialog.component.html'
})
export class ProfileConfigurationDeleteDialogComponent {

    profileConfiguration: ProfileConfiguration;

    constructor(
        private profileConfigurationService: ProfileConfigurationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profileConfigurationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'profileConfigurationListModification',
                content: 'Deleted an profileConfiguration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profile-configuration-delete-popup',
    template: ''
})
export class ProfileConfigurationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileConfigurationPopupService: ProfileConfigurationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.profileConfigurationPopupService
                .open(ProfileConfigurationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
