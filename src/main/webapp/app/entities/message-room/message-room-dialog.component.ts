import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MessageRoom } from './message-room.model';
import { MessageRoomPopupService } from './message-room-popup.service';
import { MessageRoomService } from './message-room.service';
import { Player, PlayerService } from '../player';
import { Message, MessageService } from '../message';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-message-room-dialog',
    templateUrl: './message-room-dialog.component.html'
})
export class MessageRoomDialogComponent implements OnInit {

    messageRoom: MessageRoom;
    isSaving: boolean;

    players: Player[];

    messages: Message[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private messageRoomService: MessageRoomService,
        private playerService: PlayerService,
        private messageService: MessageService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.playerService.query()
            .subscribe((res: ResponseWrapper) => { this.players = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.messageService.query()
            .subscribe((res: ResponseWrapper) => { this.messages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.messageRoom, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.messageRoom.id !== undefined) {
            this.subscribeToSaveResponse(
                this.messageRoomService.update(this.messageRoom));
        } else {
            this.subscribeToSaveResponse(
                this.messageRoomService.create(this.messageRoom));
        }
    }

    private subscribeToSaveResponse(result: Observable<MessageRoom>) {
        result.subscribe((res: MessageRoom) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MessageRoom) {
        this.eventManager.broadcast({ name: 'messageRoomListModification', content: 'OK'});
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

    trackMessageById(index: number, item: Message) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-message-room-popup',
    template: ''
})
export class MessageRoomPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messageRoomPopupService: MessageRoomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.messageRoomPopupService
                    .open(MessageRoomDialogComponent as Component, params['id']);
            } else {
                this.messageRoomPopupService
                    .open(MessageRoomDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
