/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MessageRoomDetailComponent } from '../../../../../../main/webapp/app/entities/message-room/message-room-detail.component';
import { MessageRoomService } from '../../../../../../main/webapp/app/entities/message-room/message-room.service';
import { MessageRoom } from '../../../../../../main/webapp/app/entities/message-room/message-room.model';

describe('Component Tests', () => {

    describe('MessageRoom Management Detail Component', () => {
        let comp: MessageRoomDetailComponent;
        let fixture: ComponentFixture<MessageRoomDetailComponent>;
        let service: MessageRoomService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [MessageRoomDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MessageRoomService,
                    JhiEventManager
                ]
            }).overrideTemplate(MessageRoomDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageRoomDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageRoomService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MessageRoom(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.messageRoom).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
