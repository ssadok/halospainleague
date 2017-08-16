/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ThemeDetailComponent } from '../../../../../../main/webapp/app/entities/theme/theme-detail.component';
import { ThemeService } from '../../../../../../main/webapp/app/entities/theme/theme.service';
import { Theme } from '../../../../../../main/webapp/app/entities/theme/theme.model';

describe('Component Tests', () => {

    describe('Theme Management Detail Component', () => {
        let comp: ThemeDetailComponent;
        let fixture: ComponentFixture<ThemeDetailComponent>;
        let service: ThemeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [ThemeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ThemeService,
                    JhiEventManager
                ]
            }).overrideTemplate(ThemeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThemeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThemeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Theme(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.theme).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
