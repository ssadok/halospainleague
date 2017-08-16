import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Theme } from './theme.model';
import { ThemePopupService } from './theme-popup.service';
import { ThemeService } from './theme.service';
import { ProfileConfiguration, ProfileConfigurationService } from '../profile-configuration';
import { MessageRoom, MessageRoomService } from '../message-room';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-theme-dialog',
    templateUrl: './theme-dialog.component.html'
})
export class ThemeDialogComponent implements OnInit {

    theme: Theme;
    isSaving: boolean;

    profileconfigurations: ProfileConfiguration[];

    messagerooms: MessageRoom[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private themeService: ThemeService,
        private profileConfigurationService: ProfileConfigurationService,
        private messageRoomService: MessageRoomService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileConfigurationService.query()
            .subscribe((res: ResponseWrapper) => { this.profileconfigurations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.messageRoomService.query()
            .subscribe((res: ResponseWrapper) => { this.messagerooms = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.theme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.themeService.update(this.theme));
        } else {
            this.subscribeToSaveResponse(
                this.themeService.create(this.theme));
        }
    }

    private subscribeToSaveResponse(result: Observable<Theme>) {
        result.subscribe((res: Theme) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Theme) {
        this.eventManager.broadcast({ name: 'themeListModification', content: 'OK'});
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

    trackProfileConfigurationById(index: number, item: ProfileConfiguration) {
        return item.id;
    }

    trackMessageRoomById(index: number, item: MessageRoom) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-theme-popup',
    template: ''
})
export class ThemePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private themePopupService: ThemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.themePopupService
                    .open(ThemeDialogComponent as Component, params['id']);
            } else {
                this.themePopupService
                    .open(ThemeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
