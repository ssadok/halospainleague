/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MatchModeDetailComponent } from '../../../../../../main/webapp/app/entities/match-mode/match-mode-detail.component';
import { MatchModeService } from '../../../../../../main/webapp/app/entities/match-mode/match-mode.service';
import { MatchMode } from '../../../../../../main/webapp/app/entities/match-mode/match-mode.model';

describe('Component Tests', () => {

    describe('MatchMode Management Detail Component', () => {
        let comp: MatchModeDetailComponent;
        let fixture: ComponentFixture<MatchModeDetailComponent>;
        let service: MatchModeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [MatchModeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MatchModeService,
                    JhiEventManager
                ]
            }).overrideTemplate(MatchModeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchModeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchModeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MatchMode(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.matchMode).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
