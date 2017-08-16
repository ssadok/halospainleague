/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MapDetailComponent } from '../../../../../../main/webapp/app/entities/map/map-detail.component';
import { MapService } from '../../../../../../main/webapp/app/entities/map/map.service';
import { Map } from '../../../../../../main/webapp/app/entities/map/map.model';

describe('Component Tests', () => {

    describe('Map Management Detail Component', () => {
        let comp: MapDetailComponent;
        let fixture: ComponentFixture<MapDetailComponent>;
        let service: MapService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [MapDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MapService,
                    JhiEventManager
                ]
            }).overrideTemplate(MapDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MapDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MapService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Map(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.map).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
