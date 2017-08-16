import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SocialAccount } from './social-account.model';
import { SocialAccountPopupService } from './social-account-popup.service';
import { SocialAccountService } from './social-account.service';
import { Player, PlayerService } from '../player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-social-account-dialog',
    templateUrl: './social-account-dialog.component.html'
})
export class SocialAccountDialogComponent implements OnInit {

    socialAccount: SocialAccount;
    isSaving: boolean;

    players: Player[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private socialAccountService: SocialAccountService,
        private playerService: PlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.playerService.query()
            .subscribe((res: ResponseWrapper) => { this.players = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.socialAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.socialAccountService.update(this.socialAccount));
        } else {
            this.subscribeToSaveResponse(
                this.socialAccountService.create(this.socialAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<SocialAccount>) {
        result.subscribe((res: SocialAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SocialAccount) {
        this.eventManager.broadcast({ name: 'socialAccountListModification', content: 'OK'});
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
    selector: 'jhi-social-account-popup',
    template: ''
})
export class SocialAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private socialAccountPopupService: SocialAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.socialAccountPopupService
                    .open(SocialAccountDialogComponent as Component, params['id']);
            } else {
                this.socialAccountPopupService
                    .open(SocialAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
