import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { MessageRoom } from './message-room.model';
import { MessageRoomService } from './message-room.service';

@Component({
    selector: 'jhi-message-room-detail',
    templateUrl: './message-room-detail.component.html'
})
export class MessageRoomDetailComponent implements OnInit, OnDestroy {

    messageRoom: MessageRoom;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private messageRoomService: MessageRoomService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMessageRooms();
    }

    load(id) {
        this.messageRoomService.find(id).subscribe((messageRoom) => {
            this.messageRoom = messageRoom;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMessageRooms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'messageRoomListModification',
            (response) => this.load(this.messageRoom.id)
        );
    }
}
