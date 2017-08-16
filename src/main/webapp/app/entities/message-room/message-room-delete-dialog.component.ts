import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MessageRoom } from './message-room.model';
import { MessageRoomPopupService } from './message-room-popup.service';
import { MessageRoomService } from './message-room.service';

@Component({
    selector: 'jhi-message-room-delete-dialog',
    templateUrl: './message-room-delete-dialog.component.html'
})
export class MessageRoomDeleteDialogComponent {

    messageRoom: MessageRoom;

    constructor(
        private messageRoomService: MessageRoomService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.messageRoomService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'messageRoomListModification',
                content: 'Deleted an messageRoom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-message-room-delete-popup',
    template: ''
})
export class MessageRoomDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messageRoomPopupService: MessageRoomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.messageRoomPopupService
                .open(MessageRoomDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
