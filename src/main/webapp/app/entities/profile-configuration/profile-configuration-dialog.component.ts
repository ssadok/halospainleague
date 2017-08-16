import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProfileConfiguration } from './profile-configuration.model';
import { ProfileConfigurationPopupService } from './profile-configuration-popup.service';
import { ProfileConfigurationService } from './profile-configuration.service';
import { Player, PlayerService } from '../player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-profile-configuration-dialog',
    templateUrl: './profile-configuration-dialog.component.html'
})
export class ProfileConfigurationDialogComponent implements OnInit {

    profileConfiguration: ProfileConfiguration;
    isSaving: boolean;

    players: Player[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private profileConfigurationService: ProfileConfigurationService,
        private playerService: PlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.playerService
            .query({filter: 'profileconfiguration(id)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.profileConfiguration.player || !this.profileConfiguration.player.id) {
                    this.players = res.json;
                } else {
                    this.playerService
                        .find(this.profileConfiguration.player.id)
                        .subscribe((subRes: Player) => {
                            this.players = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.profileConfiguration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.profileConfigurationService.update(this.profileConfiguration));
        } else {
            this.subscribeToSaveResponse(
                this.profileConfigurationService.create(this.profileConfiguration));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProfileConfiguration>) {
        result.subscribe((res: ProfileConfiguration) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProfileConfiguration) {
        this.eventManager.broadcast({ name: 'profileConfigurationListModification', content: 'OK'});
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

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-profile-configuration-popup',
    template: ''
})
export class ProfileConfigurationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileConfigurationPopupService: ProfileConfigurationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.profileConfigurationPopupService
                    .open(ProfileConfigurationDialogComponent as Component, params['id']);
            } else {
                this.profileConfigurationPopupService
                    .open(ProfileConfigurationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
