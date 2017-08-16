/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { NotificationDetailComponent } from '../../../../../../main/webapp/app/entities/notification/notification-detail.component';
import { NotificationService } from '../../../../../../main/webapp/app/entities/notification/notification.service';
import { Notification } from '../../../../../../main/webapp/app/entities/notification/notification.model';

describe('Component Tests', () => {

    describe('Notification Management Detail Component', () => {
        let comp: NotificationDetailComponent;
        let fixture: ComponentFixture<NotificationDetailComponent>;
        let service: NotificationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [NotificationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    NotificationService,
                    JhiEventManager
                ]
            }).overrideTemplate(NotificationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Notification(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.notification).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
