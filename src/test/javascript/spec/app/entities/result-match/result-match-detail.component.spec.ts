/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ResultMatchDetailComponent } from '../../../../../../main/webapp/app/entities/result-match/result-match-detail.component';
import { ResultMatchService } from '../../../../../../main/webapp/app/entities/result-match/result-match.service';
import { ResultMatch } from '../../../../../../main/webapp/app/entities/result-match/result-match.model';

describe('Component Tests', () => {

    describe('ResultMatch Management Detail Component', () => {
        let comp: ResultMatchDetailComponent;
        let fixture: ComponentFixture<ResultMatchDetailComponent>;
        let service: ResultMatchService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [ResultMatchDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ResultMatchService,
                    JhiEventManager
                ]
            }).overrideTemplate(ResultMatchDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResultMatchDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultMatchService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ResultMatch(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.resultMatch).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
